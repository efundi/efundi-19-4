package edu.nwu.sakaistudentlink.services;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import za.ac.nwu.wsdl.CourseOfferingTracs.AcademicPeriodInfo;
import za.ac.nwu.wsdl.CourseOfferingTracs.ContextInfo;
import za.ac.nwu.wsdl.CourseOfferingTracs.CourseOfferingTracsServiceProxy;
import za.ac.nwu.wsdl.CourseOfferingTracs.DoesNotExist;
import za.ac.nwu.wsdl.CourseOfferingTracs.InvalidParameter;
import za.ac.nwu.wsdl.CourseOfferingTracs.MissingParameter;
import za.ac.nwu.wsdl.CourseOfferingTracs.ModuleOfferingTracsInfo;
import za.ac.nwu.wsdl.CourseOfferingTracs.ModuleOfferingTracsSearchCriteriaInfo;
import za.ac.nwu.wsdl.CourseOfferingTracs.OperationFailed;
import za.ac.nwu.wsdl.CourseOfferingTracs.PermissionDenied;

/**
 * author: Jaco Gillman
 * 
 */
public class ModuleSearch {

    private static final Logger log = LoggerFactory.getLogger(ModuleSearch.class);

	private SettingsProperties settingsProperties;

	private ConnectionManager connectionManager;
	
    private ModuleSearch() {
    }

    public List<ModuleOffering> findSearchModule(Map<SearchCriteria, String> criteria)
            throws IntegrationException {

        List<ModuleOffering> modules = new ArrayList<ModuleOffering>();
        TreeSet<ModuleOffering> moduleSet = new TreeSet<ModuleOffering>();
        Calendar calendar = Calendar.getInstance();
        
        ModuleOfferingTracsSearchCriteriaInfo searchCriteria = new ModuleOfferingTracsSearchCriteriaInfo();
        searchCriteria.setAcademicPeriod(new AcademicPeriodInfo(Integer.toString(calendar.get(Calendar.YEAR)), Integer.toString(calendar.get(Calendar.YEAR))));
        String courseCode = criteria.get(SearchCriteria.MODULE_SUBJECT_CODE);
        if(courseCode != null){
            searchCriteria.setModuleSubjectCode(criteria.get(SearchCriteria.MODULE_SUBJECT_CODE)); 
        }       
        String moduleNumber = criteria.get(SearchCriteria.MODULE_NUMBER);
        if(moduleNumber != null){
            searchCriteria.setModuleNumber(moduleNumber);  
        }     
        searchCriteria.setModuleSite(criteria.get(SearchCriteria.CAMPUS));        
        String methodOfDel = criteria.get(SearchCriteria.METHOD_OF_DEL) == null ? null : criteria.get(SearchCriteria.METHOD_OF_DEL);
        if(methodOfDel != null){
            searchCriteria.setMethodOfDeliveryTypeKey("vss.code.ENROLCAT." + methodOfDel);  
        }         
        String modeOfDel = criteria.get(SearchCriteria.PRESENT_CAT) == null ? null : criteria.get(SearchCriteria.PRESENT_CAT);
        if(modeOfDel != null){
            searchCriteria.setModeOfDeliveryTypeKey("vss.code.PRESENTCAT." + modeOfDel);  
        } 
        
        String webserviceUrl = settingsProperties
                .getProperty("ws.module.url", "http://workflowprd.nwu.ac.za/student-tracs-v3/CourseOfferingTracsService/OfferingTracsService?wsdl");
        String contextInfoUsername = settingsProperties.getProperty("nwu.context.info.username", "sapiappreadprod");
        String contextInfoPassword = settingsProperties.getProperty("nwu.context.info.password", "5p@ssw0rd4pr0dr");
                
		try {
	        CourseOfferingTracsServiceProxy proxy = new CourseOfferingTracsServiceProxy(webserviceUrl);   
			ModuleOfferingTracsInfo[] result = proxy.getModuleOfferingBySearchCriteria(searchCriteria, new ContextInfo(contextInfoUsername, contextInfoPassword, calendar, null));

			int moduleIdCnt = 1;
			for (ModuleOfferingTracsInfo moduleOfferingTracsInfo : result) {
				
				ModuleOffering moduleOffering = new ModuleOffering();
				moduleOffering.setKsapimotracsid(Integer.toString(moduleIdCnt)); //This has a null value returned, must have a value
				moduleOffering.setModuleSubjectCode(moduleOfferingTracsInfo.getModuleSubjectCode());
				moduleOffering.setModuleNumber(moduleOfferingTracsInfo.getModuleNumber());
				moduleOffering.setModuleSite(moduleOfferingTracsInfo.getModuleSite());
				moduleOffering.setMethodOfDeliveryTypeKey(moduleOfferingTracsInfo.getMethodOfDeliveryTypeKey());
				moduleOffering.setModeOfDeliveryTypeKey(moduleOfferingTracsInfo.getModeOfDeliveryTypeKey());
				moduleOffering.setLanguageTypeKey(moduleOfferingTracsInfo.getLanguageTypeKey());			
				moduleOffering.setTermTypeKey(moduleOfferingTracsInfo.getTermTypeKey());
				moduleOffering.setStartDate(moduleOfferingTracsInfo.getModuleSubjectCode());
				moduleOffering.setEndDate(moduleOfferingTracsInfo.getModuleSubjectCode());
				
				String methodOfDelResult = moduleOfferingTracsInfo.getMethodOfDeliveryTypeKey();
	            if(methodOfDelResult != null && methodOfDelResult.length() != 0){
	            	methodOfDelResult = methodOfDelResult.replace("vss.code.ENROLCAT.", "");
	            	methodOfDelResult = "vss.code.ENROLCAT." +  methodOfDelResult.substring(0, methodOfDelResult.indexOf("."));
	            }
	            String modeOfDelResult = moduleOfferingTracsInfo.getModeOfDeliveryTypeKey();
	            if(modeOfDelResult != null && modeOfDelResult.length() != 0){
	            	modeOfDelResult = modeOfDelResult.replace("vss.code.PRESENTCAT.", "");
	            	modeOfDelResult = "vss.code.PRESENTCAT." +  modeOfDelResult.substring(0, modeOfDelResult.indexOf("."));
	            }
				
				moduleOffering.setLinkedByLecturer(getLinkedByLecturer(calendar.get(Calendar.YEAR), 
						moduleOfferingTracsInfo.getModuleSubjectCode(), moduleOfferingTracsInfo.getModuleNumber().substring(0, 1), moduleOfferingTracsInfo.getModuleNumber().substring(1, 3),
						Campus.getCampus(moduleOfferingTracsInfo.getModuleSite()).getNumber(), methodOfDelResult, modeOfDelResult));
				moduleSet.add(moduleOffering);
    			moduleIdCnt += 1;
			}

		} catch (InvalidParameter e) {
			log.error("OfferingTracsService - InvalidParameter: ", e);

			IntegrationException ie = new IntegrationException("Could not retrieve the modules ", e);
			IntegrationError error = new IntegrationError();
			error.setErrorMessage(e.getFaultString());
			ie.addError(error);
			throw ie;
		} catch (DoesNotExist e) {
			log.error("OfferingTracsService - DoesNotExist: ", e);

			IntegrationException ie = new IntegrationException("Could not retrieve the modules ", e);
			IntegrationError error = new IntegrationError();
			error.setErrorMessage(e.getFaultString());
			ie.addError(error);
			throw ie;
		} catch (OperationFailed e) {
			log.error("OfferingTracsService - OperationFailed: ", e);

			IntegrationException ie = new IntegrationException("Could not retrieve the modules ", e);
			IntegrationError error = new IntegrationError();
			error.setErrorMessage(e.getFaultString());
			ie.addError(error);
			throw ie;
		} catch (MissingParameter e) {
			log.error("OfferingTracsService - MissingParameter: ", e);

			IntegrationException ie = new IntegrationException("Could not retrieve the modules ", e);
			IntegrationError error = new IntegrationError();
			error.setErrorMessage(e.getFaultString());
			ie.addError(error);
			throw ie;
		} catch (PermissionDenied e) {
			log.error("OfferingTracsService - PermissionDenied: ", e);

			IntegrationException ie = new IntegrationException("Could not retrieve the modules ", e);
			IntegrationError error = new IntegrationError();
			error.setErrorMessage(e.getFaultString());
			ie.addError(error);
			throw ie;
		} catch (RemoteException e) {
			log.error("OfferingTracsService - RemoteException: ", e);

			IntegrationException ie = new IntegrationException("Could not retrieve the modules ", e);
			IntegrationError error = new IntegrationError();
			error.setErrorMessage(e.getMessage());
			ie.addError(error);
			throw ie;
		} catch (ConnectionNotEstablishedException e) {
			log.error("OfferingTracsService - ConnectionNotEstablishedException: ", e);

			IntegrationException ie = new IntegrationException("Could not retrieve the linked lecturers ", e);
			IntegrationError error = new IntegrationError();
			error.setErrorMessage(e.getMessage());
			ie.addError(error);
			throw ie;
		}
       
        modules.addAll(moduleSet);
        return modules;
    }

    private String getLinkedByLecturer(int year, String courseCode, String courseLevel, 
            String courseModule, Integer campus, String methodOfDeliveryCode, String presentationCategoryCode) 
            		throws ConnectionNotEstablishedException {
        log.info("Performing ModuleSearch.getLinkedByLecturer ");
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rset = null;
        String lecturerName = "";
        try {
            conn = connectionManager.getCourseManagementConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT l.username ");
            sql.append("FROM CM_MODULES m, ");
            sql.append("CM_LECTURER l, ");
            sql.append("CM_YEAR_CAMPUS c ");
            sql.append("WHERE  ");
            sql.append("m.lecturer_f_id = l.lecturer_id ");
            sql.append("AND l.year_campus_f_id = c.year_campus_id ");
            sql.append("AND c.year = ? ");
            sql.append("AND m.course_code = ? ");
            sql.append("AND m.course_level = ? ");
            sql.append("AND m.course_module = ? ");
            if (campus.intValue() != 0) {
                sql.append("AND c.campus_code = ? ");
            }
            sql.append("and m.method_of_del = ? ");
            sql.append("and m.present_cat = ?");
            pstmt = conn.prepareStatement(sql.toString());
            int cnt = 1;
            pstmt.setInt(cnt++, year);
            pstmt.setString(cnt++, courseCode);
            pstmt.setString(cnt++, courseLevel);
            pstmt.setString(cnt++, courseModule);
            if (campus.intValue() != 0) {
                pstmt.setInt(cnt++, campus.intValue());
            }
            pstmt.setString(cnt++, methodOfDeliveryCode);
            pstmt.setString(cnt++, presentationCategoryCode);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                lecturerName = rset.getString(1);
                break;
            }
        }
        catch (ConnectionNotEstablishedException e) {
            log.error(
                "A SQL Connection could not be established while performing findSearchModuleJDBC",
                e);
            throw new ConnectionNotEstablishedException(
                        "A SQL Connection could not be established to the Sakai database.", e);
        }
        catch (SQLException e) {
            log.error("SQL error occured when performing ModuleSearch.getLinkedByLecturer", e);
            throw new ConnectionNotEstablishedException(
                    "SQL error occured while retrieving Linked Lecturer data from the Sakai database.", e);
        }
        finally {
            try {
                ConnectionManager.close(rset, pstmt, conn);
            }
            catch (Exception e) {
                log.error("Error closing connection to the Sakai database", e);
                throw new ConnectionNotEstablishedException(
                        "Error closing connection to the Sakai database", e);
            }
        }
        return lecturerName;
    }

	@Autowired
	public void setSettingsProperties(SettingsProperties settingsProperties) {
		this.settingsProperties = settingsProperties;
	}
	
	@Autowired
	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
}