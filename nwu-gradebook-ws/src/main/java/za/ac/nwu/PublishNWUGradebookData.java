package za.ac.nwu;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import ac.za.nwu.academic.dates.dto.AcademicPeriodInfo;
import ac.za.nwu.academic.registration.service.StudentAcademicRegistrationService;
import ac.za.nwu.registry.utility.GenericServiceClientFactory;
import ac.za.nwu.utility.ServiceRegistryLookupUtility;
import assemble.edu.common.dto.ContextInfo;
import lombok.extern.slf4j.Slf4j;
import nwu.student.assesment.service.StudentAssessmentService;
import nwu.student.assesment.service.crud.StudentAssessmentServiceCRUD;
import nwu.student.assesment.service.crud.factory.StudentAssessmentCRUDServiceClientFactory;

@Slf4j
public class PublishNWUGradebookData {

    public static void main(String args[]) {
        String dbPropertiesPath = "nwu-gradebook-db.properties";

//        if (dbPropertiesPath == null) {
//            info("You must set the nwu-gradebook-db.properties system property.\n");
//            showUsage();
//
//            System.exit(1);
//        }

        try {
            DBConfig config = new DBConfig(dbPropertiesPath);

            Connection connection = null;
            try {
            	connection = DriverManager.getConnection(config.getUrl(),
                        config.getUsername(),
                        config.getPassword());
                publishGradebookData(connection);
                
//                info("YES");
                
            } finally  {
                if (connection != null) {
                	connection.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void info(String msg) {
        log.info(msg);
    }

    private static void debug(String msg) {
        if (log.isDebugEnabled()) {
            log.debug("*** DEBUG: " + msg);
        }
    }

    private static void publishGradebookData(Connection connection) throws
        SQLException {

        connection.setAutoCommit(false);
        
        publishGradebookDataToVSS(getGradeBookDataToProcess(connection));

        info("Migration complete!");
    }

    private static void publishGradebookDataToVSS(Object gradeBookDataToProcess) {
		// TODO Auto-generated method stub
		
	}    

	private static Object getGradeBookDataToProcess(Connection connection) throws SQLException {
		PreparedStatement currentYearSitesPrepStmt = null;
		PreparedStatement studentGradebookMarksPrepStmt = null;
		PreparedStatement nwuGradebookRecordsSelectPrepStmt = null;
		PreparedStatement nwuGradebookRecordsInsertPrepStmt = null;
		PreparedStatement nwuGradebookRecordsUpdatePrepStmt = null;

		ResultSet resultSet = null;

		Date startDateTime = new Date();
		Date endDateTime = new Date();

		LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(startDateTime.toInstant(), ZoneId.systemDefault());
		LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(endDateTime.toInstant(), ZoneId.systemDefault());
		
		String currentYearSitesSelectSQL = "SELECT SITE_ID, group_concat(VALUE,'/') as module FROM sakai.sakai_site_group_property WHERE VALUE LIKE ? GROUP BY SITE_ID";
		
		StringBuilder studentGradebookMarksSelectSQL = new StringBuilder("SELECT gr.STUDENT_ID, gr.POINTS_EARNED, gr.ID");
		studentGradebookMarksSelectSQL.append(" FROM gb_grade_record_t gr ");
		studentGradebookMarksSelectSQL.append("   JOIN gb_gradable_object_t go ON go.ID = gr.GRADABLE_OBJECT_ID");
		studentGradebookMarksSelectSQL.append("   JOIN gb_grade_map_t gm ON gm.GRADEBOOK_ID = go.GRADEBOOK_ID");
		studentGradebookMarksSelectSQL.append("   JOIN gb_gradebook_t g ON g.SELECTED_GRADE_MAPPING_ID = gm.ID");
		studentGradebookMarksSelectSQL.append(" WHERE g.NAME = ?");
		studentGradebookMarksSelectSQL.append(" AND gr.DATE_RECORDED BETWEEN ? AND ?");
		
		String nwuGradebookRecordsSelectSQL = "SELECT * FROM NWU_GRADEBOOK_DATA WHERE SITE_ID = ? AND STUDENT_NUMBER = ? AND GRADABLE_OBJECT_ID = ?";

		String nwuGradebookRecordsInsertSQL = "INSERT INTO NWU_GRADEBOOK_DATA VALUES (default,?,?,?,?,?,?,?)";
		String nwuGradebookRecordsUpdateSQL = "UPDATE NWU_GRADEBOOK_DATA SET MODIFIED_DATE = ?, STATUS = ? WHERE SITE_ID = ? AND STUDENT_NUMBER = ? AND GRADABLE_OBJECT_ID = ?";
				
		try {
			currentYearSitesPrepStmt = connection.prepareStatement(currentYearSitesSelectSQL);
			currentYearSitesPrepStmt.setString(1, "%" + LocalDateTime.now().getYear());
			ResultSet currentYearSitesResultSet = currentYearSitesPrepStmt.executeQuery();

			String siteId, module, studentNumber = "";
			double grade;
			Calendar calendar = Calendar.getInstance();
		      
			int gradableObjectId;

			if (currentYearSitesResultSet.next()) {
				siteId = currentYearSitesResultSet.getString("SITE_ID");
				module = currentYearSitesResultSet.getString("module");

				studentGradebookMarksPrepStmt = connection.prepareStatement(studentGradebookMarksSelectSQL.toString());
				studentGradebookMarksPrepStmt.setString(1, siteId);
				studentGradebookMarksPrepStmt.setTimestamp(2, Timestamp.valueOf(startLocalDateTime));
				studentGradebookMarksPrepStmt.setTimestamp(3, Timestamp.valueOf(endLocalDateTime));
				ResultSet studentGradebookMarksResultSet = studentGradebookMarksPrepStmt.executeQuery();
				if (studentGradebookMarksResultSet.next()) {
					studentNumber = currentYearSitesResultSet.getString("STUDENT_ID");
					grade = currentYearSitesResultSet.getDouble("POINTS_EARNED");
					gradableObjectId = currentYearSitesResultSet.getInt("ID");
					
					//Lookup records in NWU_GRADEBOOK_DATA
					nwuGradebookRecordsSelectPrepStmt = connection.prepareStatement(nwuGradebookRecordsSelectSQL);
					nwuGradebookRecordsSelectPrepStmt.setString(1, siteId);
					nwuGradebookRecordsSelectPrepStmt.setString(2, studentNumber);
					nwuGradebookRecordsSelectPrepStmt.setInt(3, gradableObjectId);
					ResultSet nwuGradebookRecordsResultSet = nwuGradebookRecordsSelectPrepStmt.executeQuery();				
					if(nwuGradebookRecordsResultSet.getMetaData().getColumnCount() == 0) { // If the record does not exist in NWU_GRADEBOOK_DATA
						
						nwuGradebookRecordsInsertPrepStmt = connection.prepareStatement(nwuGradebookRecordsInsertSQL, Statement.RETURN_GENERATED_KEYS);
						nwuGradebookRecordsInsertPrepStmt.setString(1, siteId);
						nwuGradebookRecordsInsertPrepStmt.setString(2, studentNumber);
						nwuGradebookRecordsInsertPrepStmt.setDouble(3, grade);
						nwuGradebookRecordsInsertPrepStmt.setInt(4, gradableObjectId);						

					    java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
						nwuGradebookRecordsInsertPrepStmt.setDate(5, startDate);
						nwuGradebookRecordsInsertPrepStmt.setString(6, NWUGradebookRecord.STATUS_NEW);
						nwuGradebookRecordsInsertPrepStmt.setInt(7, 0);							
						
					    // execute the preparedstatement
						int count = nwuGradebookRecordsInsertPrepStmt.executeUpdate();
						if(count > 0) { // If the INSERT was successful, send student grade via Webservice
							
							publishGrades();

							//Send via http://workflow7tst.nwu.ac.za/student-assessment-v4-v_test/StudentAssessmentServiceCRUD/StudentAssessmentServiceCRUD?wsdl
							
							//IF WS update was successful, update status in new table to DONE, else update status to FAIL / RETRY
							nwuGradebookRecordsUpdatePrepStmt = connection.prepareStatement(nwuGradebookRecordsUpdateSQL);
						    java.sql.Date modifiedDate = new java.sql.Date(calendar.getTime().getTime());
						    nwuGradebookRecordsUpdatePrepStmt.setDate(1, modifiedDate);
							
							//if WS success / else status = FAIL
							nwuGradebookRecordsUpdatePrepStmt.setString(2, NWUGradebookRecord.STATUS_SUCCESS);
							nwuGradebookRecordsUpdatePrepStmt.setString(3, siteId);
							nwuGradebookRecordsUpdatePrepStmt.setString(4, studentNumber);
							nwuGradebookRecordsUpdatePrepStmt.setInt(5, gradableObjectId);
							
						    // execute the preparedstatement
							nwuGradebookRecordsUpdatePrepStmt.executeUpdate();
						}

					} else { 
						
						if (studentGradebookMarksResultSet.next()) {
							studentNumber = currentYearSitesResultSet.getString("STUDENT_ID");
							grade = currentYearSitesResultSet.getDouble("POINTS_EARNED");
							gradableObjectId = currentYearSitesResultSet.getInt("ID");
						}

						//Send via http://workflow7tst.nwu.ac.za/student-assessment-v4-v_test/StudentAssessmentServiceCRUD/StudentAssessmentServiceCRUD?wsdl
						
						//IF WS update was successful, update status in new table to DONE, else update status to FAIL / RETRY
					}
					
					//if not exist in new table, insert
					
					//else, if it does exist in table, 
					
					//Read new table, if status is != DONE, and between START and END DATE, and siteId, studentNumber, 
					
					
					
					//new table ID, SITE_ID, STUDENT_NUMBER, GRADE, GRADABLE_OBJECT_ID, CREATED_DATE, MODIFIED_DATE, STATUS, RETRY_COUNT, DESCRIPTION
					
				}
			}
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (currentYearSitesPrepStmt != null) {
				currentYearSitesPrepStmt.close();
			}
			if (studentGradebookMarksPrepStmt != null) {
				studentGradebookMarksPrepStmt.close();
			}
			if (nwuGradebookRecordsSelectPrepStmt != null) {
				nwuGradebookRecordsSelectPrepStmt.close();
			}
			if (nwuGradebookRecordsInsertPrepStmt != null) {
				nwuGradebookRecordsInsertPrepStmt.close();
			}
			if (nwuGradebookRecordsUpdatePrepStmt != null) {
				nwuGradebookRecordsUpdatePrepStmt.close();
			}
		}
		return null;
	}

	private static void publishGrades() {

		Calendar calendar = Calendar.getInstance();		
		String envTypeKey = serverConfigurationService.getString("ws.env.type.key", "/PROD/SAPI-STUDENTACADEMICREGISTRATIONSERVICE/V8");
		String contextInfoUsername = serverConfigurationService.getString("nwu.context.info.username", "sapiappreadprod");
		String contextInfoPassword = serverConfigurationService.getString("nwu.context.info.password", "5p@ssw0rd4pr0dr");

		AcademicPeriodInfo academicPeriodInfo = new AcademicPeriodInfo();
		academicPeriodInfo.setAcadPeriodtTypeKey("vss.code.AcademicPeriod.YEAR");
		academicPeriodInfo.setAcadPeriodValue(Integer.toString(calendar.get(Calendar.YEAR)));
		ContextInfo contextInfo = new ContextInfo("SOAPUI");

		StudentAcademicRegistrationService service = (StudentAcademicRegistrationService) GenericServiceClientFactory
				.getService(envTypeKey, contextInfoUsername, contextInfoPassword, StudentAcademicRegistrationService.class);
		List<String> studentUserNames = service.getStudentAcademicRegistrationByModuleOffering(searchCriteria, contextInfo);
		
		version sal dan die major version van die service wees (in die geval die dependency)
		database sal byvoorbeeld v_test/v_prod ens wees
		runtimeEnvironment sal byvoorbeeld QA/TEST/PROD wees
		
		
		
		
		String studentServiceLookupKey = ServiceRegistryLookupUtility.getServiceRegistryLookupKey(
                StudentAssessmentCRUDServiceClientFactory.STUDENTASSESSMENTCRUDSERVICE, wsMajorVersion, database,
                runtimeEnvironment);

        StudentAssessmentServiceCRUD service = (StudentAssessmentServiceCRUD) GenericServiceClientFactory.getService(
                studentServiceLookupKey, username, password, StudentAssessmentServiceCRUD.class);
		
		
		
		String wsMajorVersion = ServiceRegistryLookupUtility.getMajorVersion(version);
		String studentServiceLookupKey = ServiceRegistryLookupUtility.getServiceRegistryLookupKey(
		        QualificationOfferingServiceClientFactory.QUALIFICATIONOFFERING_SERVICE, wsMajorVersion, database,
		        runtimeEnvironment);
		 
		log.info("LookupKey: " + studentServiceLookupKey + "\n");
		 
		try {
		    //String envTypeKey, String username, String password, Class interfaceClass
			StudentAssessmentService service = (StudentAssessmentService) GenericServiceClientFactory.getService(studentServiceLookupKey, username, password, QualificationOfferingService.class);
		    return service;
		 
		} catch (Exception ex) {
		    log.error("Could not initialize QualificationServiceProxy check if it is deployed: " + ex);
		    throw new VaadinUIException(
		            "Could not initialize QualificationServiceProxy check if it is deployed: " + ex);
		}
	}

	private static void showUsage() {
        info("Usage:\n");
//        info("  cd /path/to/my/tomcat/directory");
//
//        info("\nThen, for Unix:\n");
//        info("  java -cp \"lib/*\" -Dtomcat.dir=\"$PWD\" org.sakaiproject.user.util.ConvertUserFavoriteSitesSakai11");
//
//        info("\nOr Windows:\n");
//        info("  java -cp \"lib\\*\" -Dtomcat.dir=%cd% org.sakaiproject.user.util.ConvertUserFavoriteSitesSakai11\n");

        info("\nIf the properties file containing your database connection details is stored in a non-standard location, you can explicitly select it with:\n");
        info("  java -cp \"lib\\*\" -Ddb.properties=nwu-gradebook-db.properties za.ac.nwu.PublishNWUGradebookData\n");

    }


    //
    // Show a progress counter and some performance numbers
    private static class ProgressCounter {

        // Show a status message every REPORT_FREQUENCY_MS milliseconds
        private static long REPORT_FREQUENCY_MS = 10000;

        private long estimatedTotalRecordCount = 0;
        private long recordCount = 0;
        private long startTime;
        private long timeOfLastReport = 0;

        public ProgressCounter(long estimatedTotalRecordCount) {
            this.estimatedTotalRecordCount = estimatedTotalRecordCount;
            this.startTime = System.currentTimeMillis();
        }

        public void tick() {
            recordCount++;

            if (recordCount > estimatedTotalRecordCount) {
                // lie :)
                recordCount = estimatedTotalRecordCount;
            }

            long now = System.currentTimeMillis();
            long msSinceLastReport = (now - timeOfLastReport);

            if (msSinceLastReport >= REPORT_FREQUENCY_MS || recordCount == estimatedTotalRecordCount) {
                timeOfLastReport = now;
                info("\nUp to record number " + recordCount + " of " + estimatedTotalRecordCount);

                long elapsed = (timeOfLastReport - startTime);

                if (elapsed > 0) {
                    float recordsPerSecond = (recordCount / (float)elapsed) * 1000;

                    info(String.format("Average processing rate (records/second): %.2f", + recordsPerSecond));
                    long recordsRemaining = (estimatedTotalRecordCount - recordCount);
                    long msRemaining = (long)((recordsRemaining / recordsPerSecond) * 1000);
                    info("Estimated finish time: " + new Date(System.currentTimeMillis() + msRemaining));
                }
            }
        }
    }

    //
    // Find the user's database connection settings
    //
    private static class DBConfig {
        private String username;
        private String password;
        private String url;

        public DBConfig(String propertiesFile) {
            if (propertiesFile != null) {
                loadFromProperties(propertiesFile);
            }

            if (username == null || password == null || url == null) {
                throw new RuntimeException("Could not locate your database connection settings!");
            }
        }

        public String getUrl() { return url; }
        public String getUsername() { return username; }
        public String getPassword() { return password; }

        private void loadFromProperties(String filename) {
        	
            Properties properties = new Properties();
        	try (InputStream input = getClass().getClassLoader().getResourceAsStream(filename)) {
                properties.load(input);
//                fh.close();
            } catch (IOException e) {
            	PublishNWUGradebookData.info("Failed to read properties from: " + filename);
            }

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String prop = (String) entry.getKey();
                String value = (String) entry.getValue();

                if ("driverClassName@javax.sql.BaseDataSource".equals(prop)) {
                    try {
                        Class.forName(value);
                    } catch (ClassNotFoundException e) {
                    	PublishNWUGradebookData.info("*** Failed to load database driver!");
                        throw new RuntimeException(e);
                    }
                } else if ("url@javax.sql.BaseDataSource".equals(prop)) {
                    this.url = value;
                } else if ("username@javax.sql.BaseDataSource".equals(prop)) {
                    this.username = value;
                } else if ("password@javax.sql.BaseDataSource".equals(prop)) {
                    this.password = value;
                }
            }
        }
    }
}
