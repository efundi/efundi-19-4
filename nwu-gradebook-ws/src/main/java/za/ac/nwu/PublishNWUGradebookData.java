package za.ac.nwu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import ac.za.nwu.academic.dates.dto.AcademicPeriodInfo;
import ac.za.nwu.common.dto.MetaInfo;
import ac.za.nwu.courseoffering.service.CourseOfferingService;
import ac.za.nwu.courseoffering.service.factory.CourseOfferingServiceClientFactory;
import ac.za.nwu.moduleoffering.dto.ModuleOfferingInfo;
import ac.za.nwu.moduleoffering.dto.ModuleOfferingSearchCriteriaInfo;
import ac.za.nwu.registry.utility.GenericServiceClientFactory;
import ac.za.nwu.utility.ServiceRegistryLookupUtility;
import assemble.edu.common.dto.ContextInfo;
import assemble.edu.exceptions.DoesNotExistException;
import assemble.edu.exceptions.InvalidParameterException;
import assemble.edu.exceptions.MissingParameterException;
import assemble.edu.exceptions.OperationFailedException;
import assemble.edu.exceptions.PermissionDeniedException;
import lombok.extern.slf4j.Slf4j;
import nwu.student.assesment.service.crud.StudentAssessmentServiceCRUD;
import nwu.student.assesment.service.crud.factory.StudentAssessmentCRUDServiceClientFactory;

@Slf4j
public class PublishNWUGradebookData {
	
	private static Connection connection = null;
	private static PropertiesHolder properties = null;

    public static void main(String args[]) {
    	
        try {

            log.info("Start Publishing NWU Gradebook Data...");
            
        	LocalDateTime startLocalDateTime = LocalDateTime.parse(args[0]);
        	LocalDateTime endLocalDateTime = LocalDateTime.parse(args[1]);
            
        	properties = new PropertiesHolder();

            try {
            	connection = DriverManager.getConnection(properties.getUrl(),
            			properties.getUsername(),
            			properties.getPassword());
            	
                log.info("Database connection successfully made.");
        		
                connection.setAutoCommit(false);
                //???
                
                publishGradebookDataToVSS(startLocalDateTime, endLocalDateTime);

                log.info("Migration complete!");
                
            } finally  {
                if (connection != null) {
                	connection.close();
                }
            }
        } catch (DateTimeParseException  e) {
        	log.error("DateTimeParseException: Please make sure both start & end date paramters has this format: yyyy-MM-ddTHH:mm:ss ie (2021-01-01T12:00:00) ");
        	
        } catch (Exception e) {
        	log.error("Could not publish Gradebook Data to VSS. ");
        	
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * @param startLocalDateTime
     * @param endLocalDateTime
     * @throws SQLException
     */
	private static void publishGradebookDataToVSS(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime) throws SQLException {
		PreparedStatement currentYearSitesPrepStmt = null;
		PreparedStatement studentGradebookMarksPrepStmt = null;
		PreparedStatement nwuGradebookRecordsSelectPrepStmt = null;
		PreparedStatement nwuGradebookRecordsInsertPrepStmt = null;
		PreparedStatement nwuGradebookRecordsUpdatePrepStmt = null;
		ResultSet currentYearSitesResultSet = null;
		ResultSet studentGradebookMarksResultSet = null;
		ResultSet nwuGradebookRecordsSelectResultSet = null;

		try {
			// #1 Get all current year sites to process 
			String currentYearSitesSelectSQL = "SELECT SITE_ID, group_concat(VALUE,'/') as module FROM sakai.sakai_site_group_property WHERE VALUE LIKE ? GROUP BY SITE_ID";
			currentYearSitesPrepStmt = connection.prepareStatement(currentYearSitesSelectSQL);
			currentYearSitesPrepStmt.setString(1, "%" + LocalDateTime.now().getYear());
			currentYearSitesResultSet = currentYearSitesPrepStmt.executeQuery();

			String siteId, siteTitle, studentNumber, assessmentName = "";
			Map<String, String> studentGradeMap = null;
			double grade, total = 0.0;
			LocalDateTime dueDate = null;
			LocalDateTime recordedDate = null;
			List<String> moduleList = null;
			List<String> moduleValues = null;
			Calendar calendar = Calendar.getInstance();
		      
			int gradableObjectId;

			while (currentYearSitesResultSet.next()) {
				
				siteId = currentYearSitesResultSet.getString("SITE_ID");
				siteTitle = getSiteTitle(siteId);
				
				moduleList = Collections.list(new StringTokenizer(currentYearSitesResultSet.getString("module").replaceAll("/", ""), ",")).stream()
					      .map(token -> (String) token)
					      .collect(Collectors.toList());
				
				for (String module : moduleList) {
					moduleValues = Collections.list(new StringTokenizer(module, " ")).stream()
						      .map(token -> (String) token)
						      .collect(Collectors.toList());
					
					studentGradeMap = new HashMap<>();

					// #2 Get all student numbers and their grades for siteId and the date recorded between start and end date
					StringBuilder studentGradebookMarksSelectSQL = new StringBuilder("SELECT gr.STUDENT_ID, gr.POINTS_EARNED, gr.ID, gr.DATE_RECORDED, go.NAME, go.POINTS_POSSIBLE, go.DUE_DATE");
					studentGradebookMarksSelectSQL.append(" FROM gb_grade_record_t gr ");
					studentGradebookMarksSelectSQL.append("   JOIN gb_gradable_object_t go ON go.ID = gr.GRADABLE_OBJECT_ID");
					studentGradebookMarksSelectSQL.append("   JOIN gb_grade_map_t gm ON gm.GRADEBOOK_ID = go.GRADEBOOK_ID");
					studentGradebookMarksSelectSQL.append("   JOIN gb_gradebook_t g ON g.SELECTED_GRADE_MAPPING_ID = gm.ID");
					studentGradebookMarksSelectSQL.append(" WHERE g.NAME = ?");
					studentGradebookMarksSelectSQL.append(" AND gr.DATE_RECORDED BETWEEN ? AND ?");
					studentGradebookMarksPrepStmt = connection.prepareStatement(studentGradebookMarksSelectSQL.toString());
					studentGradebookMarksPrepStmt.setString(1, siteId);
					studentGradebookMarksPrepStmt.setTimestamp(2, Timestamp.valueOf(startLocalDateTime));
					studentGradebookMarksPrepStmt.setTimestamp(3, Timestamp.valueOf(endLocalDateTime));
					studentGradebookMarksResultSet = studentGradebookMarksPrepStmt.executeQuery();
					while (studentGradebookMarksResultSet.next()) {
						
						studentNumber = studentGradebookMarksResultSet.getString("STUDENT_ID");
						grade = studentGradebookMarksResultSet.getDouble("POINTS_EARNED");
						
						recordedDate = studentGradebookMarksResultSet.getTimestamp("DATE_RECORDED").toLocalDateTime();	
						assessmentName = studentGradebookMarksResultSet.getString("NAME");
						total = studentGradebookMarksResultSet.getDouble("POINTS_POSSIBLE");
						dueDate = studentGradebookMarksResultSet.getTimestamp("DUE_DATE").toLocalDateTime();
						
						gradableObjectId = studentGradebookMarksResultSet.getInt("ID");
						
						// #3 Get matching data for students from NWU_GRADEBOOK_DATA
						String nwuGradebookRecordsSelectSQL = "SELECT * FROM NWU_GRADEBOOK_DATA WHERE SITE_ID = ? AND STUDENT_NUMBER = ? AND GRADABLE_OBJECT_ID = ?";
						nwuGradebookRecordsSelectPrepStmt = connection.prepareStatement(nwuGradebookRecordsSelectSQL);
						nwuGradebookRecordsSelectPrepStmt.setString(1, siteId);
						nwuGradebookRecordsSelectPrepStmt.setString(2, studentNumber);
						nwuGradebookRecordsSelectPrepStmt.setInt(3, gradableObjectId);
						nwuGradebookRecordsSelectResultSet = nwuGradebookRecordsSelectPrepStmt.executeQuery();				
						if(nwuGradebookRecordsSelectResultSet.getMetaData().getColumnCount() == 0) {

							// #4 If the record does not exist in NWU_GRADEBOOK_DATA, insert new with status STATUS_NEW
							String nwuGradebookRecordsInsertSQL = "INSERT INTO NWU_GRADEBOOK_DATA VALUES (default,?,?,?,?,?,?,?)";
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
							if(count > 0) {
								// If inserted successfully, add student and grade data to Map
								studentGradeMap.put(studentNumber, String.valueOf(grade));
							}

						} else { 
							
							//If it does exist by grade and recordedDate differ, update with new values and Status = UPDATE
							
//							if (studentGradebookMarksResultSet.next()) {
//								studentNumber = currentYearSitesResultSet.getString("STUDENT_ID");
//								grade = currentYearSitesResultSet.getDouble("POINTS_EARNED");
//								gradableObjectId = currentYearSitesResultSet.getInt("ID");
//								dateRecorde
							
//							}

							//Send via http://workflow7tst.nwu.ac.za/student-assessment-v4-v_test/StudentAssessmentServiceCRUD/StudentAssessmentServiceCRUD?wsdl
							
							//IF WS update was successful, update status in new table to DONE, else update status to FAIL / RETRY
						}
						
						//if not exist in new table, insert
						
						//else, if it does exist in table, 
						
						//Read new table, if status is != DONE, and between START and END DATE, and siteId, studentNumber, 
						
						
						
						//new table ID, SITE_ID, STUDENT_NUMBER, GRADE, GRADABLE_OBJECT_ID, CREATED_DATE, MODIFIED_DATE, STATUS, RETRY_COUNT, DESCRIPTION
						
					}
					

					// #5 If the INSERT was successful and studentGradeMap not empty, send student grades/data via Webservice StudentAssessmentServiceCRUD
					publishGrades(moduleValues, studentGradeMap, siteTitle, assessmentName, total, dueDate, recordedDate);
					
				}				
			}
		} finally {

			if (currentYearSitesResultSet != null) { currentYearSitesResultSet.close(); }
			if (studentGradebookMarksResultSet != null) { studentGradebookMarksResultSet.close(); }
			if (nwuGradebookRecordsSelectResultSet != null) { nwuGradebookRecordsSelectResultSet.close(); }
			
            if (currentYearSitesPrepStmt != null) { currentYearSitesPrepStmt.close(); }
            if (studentGradebookMarksPrepStmt != null) { studentGradebookMarksPrepStmt.close(); }
            if (nwuGradebookRecordsSelectPrepStmt != null) { nwuGradebookRecordsSelectPrepStmt.close(); }
            if (nwuGradebookRecordsInsertPrepStmt != null) { nwuGradebookRecordsInsertPrepStmt.close(); }
            if (nwuGradebookRecordsUpdatePrepStmt != null) { nwuGradebookRecordsUpdatePrepStmt.close(); }
		}
	}

	private static String getSiteTitle(String siteId) throws SQLException {

		String siteTitleSelectSQL = "SELECT TITLE FROM sakai.sakai_site where SITE_ID = ?";
		PreparedStatement siteTitlePrepStmt = connection.prepareStatement(siteTitleSelectSQL);
		siteTitlePrepStmt.setString(1, siteId);
		ResultSet siteTitleResultSet = siteTitlePrepStmt.executeQuery();
		if (siteTitleResultSet.next()) {			
			return siteTitleResultSet.getString("TITLE");
		}			
		return null;
	}

	private static void publishGrades(List<String> moduleValues, Map<String, String> studentGradeMap, String siteTitle, String assessmentName, double total, LocalDateTime dueDate, LocalDateTime recordedDate) {

		log.info("publishGrades start: ");
		
		Calendar calendar = Calendar.getInstance();	
		AcademicPeriodInfo academicPeriodInfo = new AcademicPeriodInfo();
		academicPeriodInfo.setAcadPeriodtTypeKey("vss.code.AcademicPeriod.YEAR");
		academicPeriodInfo.setAcadPeriodValue(Integer.toString(calendar.get(Calendar.YEAR)));
		ContextInfo contextInfo = new ContextInfo("SOAPUI");//EFUNDI
		
		MetaInfo metaInfo = new MetaInfo();
		metaInfo.setCreateId(properties.getWSMetaInfoCreateId());
		metaInfo.setAuditFunction(properties.getWSMetaInfoAuditFunction());
		
//		String wsMajorVersion = ServiceRegistryLookupUtility.getMajorVersion(version);???????????
		String wsMajorVersion = properties.getWSMajorVersion();
		String database = properties.getWSDatabase();
		String runtimeEnvironment = properties.getWSRuntimeEnvironment();
		String username = properties.getWSUsername();
		String password = properties.getWSPassword();
		
		String studentServiceLookupKey = ServiceRegistryLookupUtility.getServiceRegistryLookupKey(
                StudentAssessmentCRUDServiceClientFactory.STUDENTASSESSMENTCRUDSERVICE, wsMajorVersion, database,
                runtimeEnvironment);

        StudentAssessmentServiceCRUD service = (StudentAssessmentServiceCRUD) GenericServiceClientFactory.getService(
                studentServiceLookupKey, username, password, StudentAssessmentServiceCRUD.class);

        String strValue = moduleValues.get(2);
        int indexOf = strValue.indexOf("-");
		String enrolmentCategoryTypeKey = "vss.code.ENROLCAT." + strValue.substring(0, indexOf);
		String modeOfDeliveryTypeKey = "vss.code.PRESENTCAT." + strValue.substring(indexOf + 1);
		
		String moduleSite = Campus.getNumber(moduleValues.get(3));
		ModuleOfferingInfo moduleOfferingInfo = getModuleOfferingInfo(academicPeriodInfo, moduleValues.get(0), moduleValues.get(1), moduleSite, enrolmentCategoryTypeKey, modeOfDeliveryTypeKey, contextInfo);
        
		HashMap<String, String> result = service.maintainStudentMark(moduleValues.get(0), moduleValues.get(1), academicPeriodInfo, enrolmentCategoryTypeKey, 
        		modeOfDeliveryTypeKey, moduleOfferingInfo.getTermTypeKey(), moduleOfferingInfo.getModuleOrgEnt(), moduleSite, siteTitle, "vss.code.LANGUAGE.3", assessmentName, evaluationShortDesc, 
        		dueDate, total, 1, 0, recordedDate, 1, studentGradeMap, metaInfo, contextInfo);
		
		updateNWUGradebookRecords();

		log.info("publishGrades end: ");
	}

	private static void updateNWUGradebookRecords() {

		// #6 IF WS update was successful, update status in new table to DONE, else update status to FAIL / RETRY
		String nwuGradebookRecordsUpdateSQL = "UPDATE NWU_GRADEBOOK_DATA SET MODIFIED_DATE = ?, STATUS = ? WHERE SITE_ID = ? AND STUDENT_NUMBER = ? AND GRADABLE_OBJECT_ID = ?";
//		nwuGradebookRecordsUpdatePrepStmt = connection.prepareStatement(nwuGradebookRecordsUpdateSQL);
//	    java.sql.Date modifiedDate = new java.sql.Date(calendar.getTime().getTime());
//	    nwuGradebookRecordsUpdatePrepStmt.setDate(1, modifiedDate);
//		
//		//if WS success / else status = FAIL
//		nwuGradebookRecordsUpdatePrepStmt.setString(2, NWUGradebookRecord.STATUS_SUCCESS);
//		nwuGradebookRecordsUpdatePrepStmt.setString(3, siteId);
//		nwuGradebookRecordsUpdatePrepStmt.setString(4, studentNumber);
//		nwuGradebookRecordsUpdatePrepStmt.setInt(5, gradableObjectId);
//		
//	    // execute the preparedstatement
//		nwuGradebookRecordsUpdatePrepStmt.executeUpdate();
	}

	private static ModuleOfferingInfo getModuleOfferingInfo(AcademicPeriodInfo academicPeriodInfo, String subjectCode, String moduleNumber, String moduleSite, String enrolmentCategoryTypeKey, String modeOfDeliveryTypeKey, ContextInfo contextInfo) {
		
		String envTypeKey = properties.getWSModuleEnvTypeKey();
		String contextInfoUsername = properties.getNWUContextInfoUsername();
		String contextInfoPassword = properties.getNWUContextInfoPassword();
		
		ModuleOfferingSearchCriteriaInfo searchCriteria = new ModuleOfferingSearchCriteriaInfo();
		searchCriteria.setAcademicPeriod(academicPeriodInfo);
		searchCriteria.setModuleSubjectCode(subjectCode);
		searchCriteria.setModuleNumber(moduleNumber);
		searchCriteria.setModuleSite(moduleSite);
		searchCriteria.setMethodOfDeliveryTypeKey(enrolmentCategoryTypeKey);
		searchCriteria.setModeOfDeliveryTypeKey(modeOfDeliveryTypeKey);		
		
		CourseOfferingService courseOfferingService;
		try {
			courseOfferingService = (CourseOfferingService) CourseOfferingServiceClientFactory
					.getCourseOfferingService(envTypeKey, contextInfoUsername, contextInfoPassword);

			List<ModuleOfferingInfo> moduleOfferingList = courseOfferingService
					.getModuleOfferingBySearchCriteria(searchCriteria, "vss.code.LANGUAGE.3", contextInfo);

			if(moduleOfferingList != null && !moduleOfferingList.isEmpty()) {
				return moduleOfferingList.get(0);
			}
			
		}  catch (InvalidParameterException e) {
			log.error("OfferingTracsService - InvalidParameter: ", e);
		} catch (DoesNotExistException e) {
			log.error("OfferingTracsService - DoesNotExist: ", e);
		} catch (OperationFailedException e) {
			log.error("OfferingTracsService - OperationFailed: ", e);
		} catch (MissingParameterException e) {
			log.error("OfferingTracsService - MissingParameter: ", e);
		} catch (PermissionDeniedException e) {
			log.error("OfferingTracsService - PermissionDenied: ", e);
		} 

		return null;
	}

	private static void showUsage() {
		log.info("Usage:\n");
//        info("  cd /path/to/my/tomcat/directory");
//
//        info("\nThen, for Unix:\n");
//        info("  java -cp \"lib/*\" -Dtomcat.dir=\"$PWD\" org.sakaiproject.user.util.ConvertUserFavoriteSitesSakai11");
//
//        info("\nOr Windows:\n");
//        info("  java -cp \"lib\\*\" -Dtomcat.dir=%cd% org.sakaiproject.user.util.ConvertUserFavoriteSitesSakai11\n");

        log.info("\nIf the properties file containing your database connection details is stored in a non-standard location, you can explicitly select it with:\n");
        log.info("  java -cp \"lib\\*\" -Ddb.properties=nwu-gradebook.properties za.ac.nwu.PublishNWUGradebookData\n");

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
                log.info("\nUp to record number " + recordCount + " of " + estimatedTotalRecordCount);

                long elapsed = (timeOfLastReport - startTime);

                if (elapsed > 0) {
                    float recordsPerSecond = (recordCount / (float)elapsed) * 1000;

                    log.info(String.format("Average processing rate (records/second): %.2f", + recordsPerSecond));
                    long recordsRemaining = (estimatedTotalRecordCount - recordCount);
                    long msRemaining = (long)((recordsRemaining / recordsPerSecond) * 1000);
                    log.info("Estimated finish time: " + new Date(System.currentTimeMillis() + msRemaining));
                }
            }
        }
    }
}
