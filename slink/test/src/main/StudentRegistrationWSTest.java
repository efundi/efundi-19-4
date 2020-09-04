import java.net.URL;
import java.util.Calendar;
import java.util.List;

import javax.xml.ws.BindingProvider;

import za.ac.nwu.wsdl.studentacademicregistration.AcademicPeriodInfo;
import za.ac.nwu.wsdl.studentacademicregistration.ModuleOfferingSearchCriteriaInfo;
import za.ac.nwu.wsdl.studentacademicregistration.StudentAcademicRegistrationService;
import za.ac.nwu.wsdl.studentacademicregistration.StudentAcademicRegistrationService_Service;

public class StudentRegistrationWSTest {

	public static void main(String[] args) {

		StringBuilder students = new StringBuilder();
		Calendar calendar = Calendar.getInstance();

		ModuleOfferingSearchCriteriaInfo searchCriteria = new ModuleOfferingSearchCriteriaInfo();

		AcademicPeriodInfo academicPeriodInfo = new AcademicPeriodInfo();
		academicPeriodInfo.setAcadPeriodtTypeKey("vss.code.AcademicPeriod.Year");
		academicPeriodInfo.setAcadPeriodValue(Integer.toString(calendar.get(Calendar.YEAR)));

		searchCriteria.setAcademicPeriod(academicPeriodInfo);
		searchCriteria.setModuleSubjectCode("ALDA");
		// searchCriteria.setModuleNumber(moduleDetail.getModuleNumber());
		searchCriteria.setModuleSite("1");
		// searchCriteria.setMethodOfDeliveryTypeKey(moduleDetail.getMethodOfDeliveryCodeParam());
		// searchCriteria.setModeOfDeliveryTypeKey(moduleDetail.getModeOfDeliveryCodeParam());

		try {
			URL wsdlURL = new URL("http://workflowprd.nwu.ac.za/sapi-vss-v5/StudentAcademicRegistrationService/StudentAcademicRegistrationService?wsdl");
	        StudentAcademicRegistrationService_Service service = new StudentAcademicRegistrationService_Service(wsdlURL);
	        StudentAcademicRegistrationService port = service.getStudentAcademicRegistrationServicePort();        

			((BindingProvider) port).getRequestContext().put( BindingProvider.USERNAME_PROPERTY, "sapiappreadprod");
			((BindingProvider) port).getRequestContext().put( BindingProvider.PASSWORD_PROPERTY, "5p@ssw0rd4pr0dr");

			List<String> studentUserNames = port.getStudentAcademicRegistrationByModuleOffering(searchCriteria, null);
	        
	        for (int j = 0; j < studentUserNames.size(); j++) {
	        	String studentUserName = studentUserNames.get(j);
	        	students.append(studentUserName);
	            if (j != studentUserNames.size() - 1) {
	                students.append(",");
	            }
			} 

			System.out.println(students);

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
