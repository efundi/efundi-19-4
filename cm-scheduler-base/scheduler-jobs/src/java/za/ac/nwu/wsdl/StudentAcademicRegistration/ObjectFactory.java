
package za.ac.nwu.wsdl.StudentAcademicRegistration;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the za.ac.nwu.wsdl.studentacademicregistration package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetStudentAcademicRegistrationByLang_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "getStudentAcademicRegistrationByLang");
    private final static QName _MissingParameter_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "MissingParameter");
    private final static QName _GetStudentAcademicRegistrationResponse_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "getStudentAcademicRegistrationResponse");
    private final static QName _ResetService_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "resetService");
    private final static QName _GetStudentAcademicRegistrationByModuleOfferingResponse_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "getStudentAcademicRegistrationByModuleOfferingResponse");
    private final static QName _GetStudentAcademicRegistrationByModuleOffering_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "getStudentAcademicRegistrationByModuleOffering");
    private final static QName _ValidateStudentModulePrerequisite_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "validateStudentModulePrerequisite");
    private final static QName _InvalidParameter_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "InvalidParameter");
    private final static QName _PermissionDenied_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "PermissionDenied");
    private final static QName _ResetServiceResponse_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "resetServiceResponse");
    private final static QName _OperationFailed_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "OperationFailed");
    private final static QName _DoesNotExist_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "DoesNotExist");
    private final static QName _GetStudentAcademicRegistrationByLangResponse_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "getStudentAcademicRegistrationByLangResponse");
    private final static QName _ValidateStudentModulePrerequisiteResponse_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "validateStudentModulePrerequisiteResponse");
    private final static QName _GetStudentAcademicRegistration_QNAME = new QName("http://nwu.ac.za/wsdl/StudentAcademicRegistration", "getStudentAcademicRegistration");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: za.ac.nwu.wsdl.studentacademicregistration
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StudentModuleAcademicInfo }
     * 
     */
    public StudentModuleAcademicInfo createStudentModuleAcademicInfo() {
        return new StudentModuleAcademicInfo();
    }

    /**
     * Create an instance of {@link StudentModuleAcademicInfo.LegacyKeys }
     * 
     */
    public StudentModuleAcademicInfo.LegacyKeys createStudentModuleAcademicInfoLegacyKeys() {
        return new StudentModuleAcademicInfo.LegacyKeys();
    }

    /**
     * Create an instance of {@link StudentAcademicQualificationInfo }
     * 
     */
    public StudentAcademicQualificationInfo createStudentAcademicQualificationInfo() {
        return new StudentAcademicQualificationInfo();
    }

    /**
     * Create an instance of {@link StudentAcademicQualificationInfo.LegacyKeys }
     * 
     */
    public StudentAcademicQualificationInfo.LegacyKeys createStudentAcademicQualificationInfoLegacyKeys() {
        return new StudentAcademicQualificationInfo.LegacyKeys();
    }

    /**
     * Create an instance of {@link GetStudentAcademicRegistration }
     * 
     */
    public GetStudentAcademicRegistration createGetStudentAcademicRegistration() {
        return new GetStudentAcademicRegistration();
    }

    /**
     * Create an instance of {@link GetStudentAcademicRegistrationByLangResponse }
     * 
     */
    public GetStudentAcademicRegistrationByLangResponse createGetStudentAcademicRegistrationByLangResponse() {
        return new GetStudentAcademicRegistrationByLangResponse();
    }

    /**
     * Create an instance of {@link DoesNotExist }
     * 
     */
    public DoesNotExist createDoesNotExist() {
        return new DoesNotExist();
    }

    /**
     * Create an instance of {@link ValidateStudentModulePrerequisiteResponse }
     * 
     */
    public ValidateStudentModulePrerequisiteResponse createValidateStudentModulePrerequisiteResponse() {
        return new ValidateStudentModulePrerequisiteResponse();
    }

    /**
     * Create an instance of {@link ValidateStudentModulePrerequisite }
     * 
     */
    public ValidateStudentModulePrerequisite createValidateStudentModulePrerequisite() {
        return new ValidateStudentModulePrerequisite();
    }

    /**
     * Create an instance of {@link InvalidParameter }
     * 
     */
    public InvalidParameter createInvalidParameter() {
        return new InvalidParameter();
    }

    /**
     * Create an instance of {@link PermissionDenied }
     * 
     */
    public PermissionDenied createPermissionDenied() {
        return new PermissionDenied();
    }

    /**
     * Create an instance of {@link ResetServiceResponse }
     * 
     */
    public ResetServiceResponse createResetServiceResponse() {
        return new ResetServiceResponse();
    }

    /**
     * Create an instance of {@link OperationFailed }
     * 
     */
    public OperationFailed createOperationFailed() {
        return new OperationFailed();
    }

    /**
     * Create an instance of {@link MissingParameter }
     * 
     */
    public MissingParameter createMissingParameter() {
        return new MissingParameter();
    }

    /**
     * Create an instance of {@link GetStudentAcademicRegistrationByLang }
     * 
     */
    public GetStudentAcademicRegistrationByLang createGetStudentAcademicRegistrationByLang() {
        return new GetStudentAcademicRegistrationByLang();
    }

    /**
     * Create an instance of {@link GetStudentAcademicRegistrationByModuleOfferingResponse }
     * 
     */
    public GetStudentAcademicRegistrationByModuleOfferingResponse createGetStudentAcademicRegistrationByModuleOfferingResponse() {
        return new GetStudentAcademicRegistrationByModuleOfferingResponse();
    }

    /**
     * Create an instance of {@link GetStudentAcademicRegistrationByModuleOffering }
     * 
     */
    public GetStudentAcademicRegistrationByModuleOffering createGetStudentAcademicRegistrationByModuleOffering() {
        return new GetStudentAcademicRegistrationByModuleOffering();
    }

    /**
     * Create an instance of {@link GetStudentAcademicRegistrationResponse }
     * 
     */
    public GetStudentAcademicRegistrationResponse createGetStudentAcademicRegistrationResponse() {
        return new GetStudentAcademicRegistrationResponse();
    }

    /**
     * Create an instance of {@link ResetService }
     * 
     */
    public ResetService createResetService() {
        return new ResetService();
    }

    /**
     * Create an instance of {@link StudentAcademicRegistrationInfo }
     * 
     */
    public StudentAcademicRegistrationInfo createStudentAcademicRegistrationInfo() {
        return new StudentAcademicRegistrationInfo();
    }

    /**
     * Create an instance of {@link AttributeInfo }
     * 
     */
    public AttributeInfo createAttributeInfo() {
        return new AttributeInfo();
    }

    /**
     * Create an instance of {@link AcademicPeriodInfo }
     * 
     */
    public AcademicPeriodInfo createAcademicPeriodInfo() {
        return new AcademicPeriodInfo();
    }

    /**
     * Create an instance of {@link StudentModulePrerequisteResultInfo }
     * 
     */
    public StudentModulePrerequisteResultInfo createStudentModulePrerequisteResultInfo() {
        return new StudentModulePrerequisteResultInfo();
    }

    /**
     * Create an instance of {@link ModuleOfferingSearchCriteriaInfo }
     * 
     */
    public ModuleOfferingSearchCriteriaInfo createModuleOfferingSearchCriteriaInfo() {
        return new ModuleOfferingSearchCriteriaInfo();
    }

    /**
     * Create an instance of {@link ContextInfo }
     * 
     */
    public ContextInfo createContextInfo() {
        return new ContextInfo();
    }

    /**
     * Create an instance of {@link StudentModuleAcademicInfo.LegacyKeys.Entry }
     * 
     */
    public StudentModuleAcademicInfo.LegacyKeys.Entry createStudentModuleAcademicInfoLegacyKeysEntry() {
        return new StudentModuleAcademicInfo.LegacyKeys.Entry();
    }

    /**
     * Create an instance of {@link StudentAcademicQualificationInfo.LegacyKeys.Entry }
     * 
     */
    public StudentAcademicQualificationInfo.LegacyKeys.Entry createStudentAcademicQualificationInfoLegacyKeysEntry() {
        return new StudentAcademicQualificationInfo.LegacyKeys.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStudentAcademicRegistrationByLang }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "getStudentAcademicRegistrationByLang")
    public JAXBElement<GetStudentAcademicRegistrationByLang> createGetStudentAcademicRegistrationByLang(GetStudentAcademicRegistrationByLang value) {
        return new JAXBElement<GetStudentAcademicRegistrationByLang>(_GetStudentAcademicRegistrationByLang_QNAME, GetStudentAcademicRegistrationByLang.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MissingParameter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "MissingParameter")
    public JAXBElement<MissingParameter> createMissingParameter(MissingParameter value) {
        return new JAXBElement<MissingParameter>(_MissingParameter_QNAME, MissingParameter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStudentAcademicRegistrationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "getStudentAcademicRegistrationResponse")
    public JAXBElement<GetStudentAcademicRegistrationResponse> createGetStudentAcademicRegistrationResponse(GetStudentAcademicRegistrationResponse value) {
        return new JAXBElement<GetStudentAcademicRegistrationResponse>(_GetStudentAcademicRegistrationResponse_QNAME, GetStudentAcademicRegistrationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "resetService")
    public JAXBElement<ResetService> createResetService(ResetService value) {
        return new JAXBElement<ResetService>(_ResetService_QNAME, ResetService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStudentAcademicRegistrationByModuleOfferingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "getStudentAcademicRegistrationByModuleOfferingResponse")
    public JAXBElement<GetStudentAcademicRegistrationByModuleOfferingResponse> createGetStudentAcademicRegistrationByModuleOfferingResponse(GetStudentAcademicRegistrationByModuleOfferingResponse value) {
        return new JAXBElement<GetStudentAcademicRegistrationByModuleOfferingResponse>(_GetStudentAcademicRegistrationByModuleOfferingResponse_QNAME, GetStudentAcademicRegistrationByModuleOfferingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStudentAcademicRegistrationByModuleOffering }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "getStudentAcademicRegistrationByModuleOffering")
    public JAXBElement<GetStudentAcademicRegistrationByModuleOffering> createGetStudentAcademicRegistrationByModuleOffering(GetStudentAcademicRegistrationByModuleOffering value) {
        return new JAXBElement<GetStudentAcademicRegistrationByModuleOffering>(_GetStudentAcademicRegistrationByModuleOffering_QNAME, GetStudentAcademicRegistrationByModuleOffering.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateStudentModulePrerequisite }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "validateStudentModulePrerequisite")
    public JAXBElement<ValidateStudentModulePrerequisite> createValidateStudentModulePrerequisite(ValidateStudentModulePrerequisite value) {
        return new JAXBElement<ValidateStudentModulePrerequisite>(_ValidateStudentModulePrerequisite_QNAME, ValidateStudentModulePrerequisite.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidParameter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "InvalidParameter")
    public JAXBElement<InvalidParameter> createInvalidParameter(InvalidParameter value) {
        return new JAXBElement<InvalidParameter>(_InvalidParameter_QNAME, InvalidParameter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PermissionDenied }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "PermissionDenied")
    public JAXBElement<PermissionDenied> createPermissionDenied(PermissionDenied value) {
        return new JAXBElement<PermissionDenied>(_PermissionDenied_QNAME, PermissionDenied.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "resetServiceResponse")
    public JAXBElement<ResetServiceResponse> createResetServiceResponse(ResetServiceResponse value) {
        return new JAXBElement<ResetServiceResponse>(_ResetServiceResponse_QNAME, ResetServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationFailed }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "OperationFailed")
    public JAXBElement<OperationFailed> createOperationFailed(OperationFailed value) {
        return new JAXBElement<OperationFailed>(_OperationFailed_QNAME, OperationFailed.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoesNotExist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "DoesNotExist")
    public JAXBElement<DoesNotExist> createDoesNotExist(DoesNotExist value) {
        return new JAXBElement<DoesNotExist>(_DoesNotExist_QNAME, DoesNotExist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStudentAcademicRegistrationByLangResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "getStudentAcademicRegistrationByLangResponse")
    public JAXBElement<GetStudentAcademicRegistrationByLangResponse> createGetStudentAcademicRegistrationByLangResponse(GetStudentAcademicRegistrationByLangResponse value) {
        return new JAXBElement<GetStudentAcademicRegistrationByLangResponse>(_GetStudentAcademicRegistrationByLangResponse_QNAME, GetStudentAcademicRegistrationByLangResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateStudentModulePrerequisiteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "validateStudentModulePrerequisiteResponse")
    public JAXBElement<ValidateStudentModulePrerequisiteResponse> createValidateStudentModulePrerequisiteResponse(ValidateStudentModulePrerequisiteResponse value) {
        return new JAXBElement<ValidateStudentModulePrerequisiteResponse>(_ValidateStudentModulePrerequisiteResponse_QNAME, ValidateStudentModulePrerequisiteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStudentAcademicRegistration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nwu.ac.za/wsdl/StudentAcademicRegistration", name = "getStudentAcademicRegistration")
    public JAXBElement<GetStudentAcademicRegistration> createGetStudentAcademicRegistration(GetStudentAcademicRegistration value) {
        return new JAXBElement<GetStudentAcademicRegistration>(_GetStudentAcademicRegistration_QNAME, GetStudentAcademicRegistration.class, null, value);
    }

}
