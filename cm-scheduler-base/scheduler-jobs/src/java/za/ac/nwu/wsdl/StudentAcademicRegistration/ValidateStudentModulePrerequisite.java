
package za.ac.nwu.wsdl.StudentAcademicRegistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validateStudentModulePrerequisite complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validateStudentModulePrerequisite">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="studentModuleAcadInfo" type="{http://nwu.ac.za/wsdl/StudentAcademicRegistration}studentModuleAcademicInfo"/>
 *         &lt;element name="systemLanguageTypeKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contextInfo" type="{http://nwu.ac.za/wsdl/StudentAcademicRegistration}ContextInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateStudentModulePrerequisite", propOrder = {
    "studentModuleAcadInfo",
    "systemLanguageTypeKey",
    "contextInfo"
})
public class ValidateStudentModulePrerequisite {

    @XmlElement(required = true)
    protected StudentModuleAcademicInfo studentModuleAcadInfo;
    @XmlElement(required = true)
    protected String systemLanguageTypeKey;
    protected ContextInfo contextInfo;

    /**
     * Gets the value of the studentModuleAcadInfo property.
     * 
     * @return
     *     possible object is
     *     {@link StudentModuleAcademicInfo }
     *     
     */
    public StudentModuleAcademicInfo getStudentModuleAcadInfo() {
        return studentModuleAcadInfo;
    }

    /**
     * Sets the value of the studentModuleAcadInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudentModuleAcademicInfo }
     *     
     */
    public void setStudentModuleAcadInfo(StudentModuleAcademicInfo value) {
        this.studentModuleAcadInfo = value;
    }

    /**
     * Gets the value of the systemLanguageTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemLanguageTypeKey() {
        return systemLanguageTypeKey;
    }

    /**
     * Sets the value of the systemLanguageTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemLanguageTypeKey(String value) {
        this.systemLanguageTypeKey = value;
    }

    /**
     * Gets the value of the contextInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ContextInfo }
     *     
     */
    public ContextInfo getContextInfo() {
        return contextInfo;
    }

    /**
     * Sets the value of the contextInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContextInfo }
     *     
     */
    public void setContextInfo(ContextInfo value) {
        this.contextInfo = value;
    }

}
