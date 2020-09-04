
package za.ac.nwu.wsdl.StudentAcademicRegistration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for studentAcademicRegistrationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="studentAcademicRegistrationInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://nwu.ac.za/wsdl/StudentAcademicRegistration}studentAcademicQualificationInfo">
 *       &lt;sequence>
 *         &lt;element name="enrolledModules" type="{http://nwu.ac.za/wsdl/StudentAcademicRegistration}studentModuleAcademicInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="registrationStatusTypeKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numberOfModulesEnrolled" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberOfRepeatModulesEnrolled" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberOfModulesDiscontinued" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberOfModulesExcempted" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberOfModulesRecognition" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="entryLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="studyType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studentAcademicRegistrationInfo", propOrder = {
    "enrolledModules",
    "registrationStatusTypeKey",
    "numberOfModulesEnrolled",
    "numberOfRepeatModulesEnrolled",
    "numberOfModulesDiscontinued",
    "numberOfModulesExcempted",
    "numberOfModulesRecognition",
    "entryLevel",
    "studyType"
})
public class StudentAcademicRegistrationInfo
    extends StudentAcademicQualificationInfo
{

    @XmlElement(nillable = true)
    protected List<StudentModuleAcademicInfo> enrolledModules;
    @XmlElement(required = true)
    protected String registrationStatusTypeKey;
    protected int numberOfModulesEnrolled;
    protected int numberOfRepeatModulesEnrolled;
    protected int numberOfModulesDiscontinued;
    protected int numberOfModulesExcempted;
    protected int numberOfModulesRecognition;
    protected String entryLevel;
    protected String studyType;

    /**
     * Gets the value of the enrolledModules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the enrolledModules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEnrolledModules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StudentModuleAcademicInfo }
     * 
     * 
     */
    public List<StudentModuleAcademicInfo> getEnrolledModules() {
        if (enrolledModules == null) {
            enrolledModules = new ArrayList<StudentModuleAcademicInfo>();
        }
        return this.enrolledModules;
    }

    /**
     * Gets the value of the registrationStatusTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrationStatusTypeKey() {
        return registrationStatusTypeKey;
    }

    /**
     * Sets the value of the registrationStatusTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrationStatusTypeKey(String value) {
        this.registrationStatusTypeKey = value;
    }

    /**
     * Gets the value of the numberOfModulesEnrolled property.
     * 
     */
    public int getNumberOfModulesEnrolled() {
        return numberOfModulesEnrolled;
    }

    /**
     * Sets the value of the numberOfModulesEnrolled property.
     * 
     */
    public void setNumberOfModulesEnrolled(int value) {
        this.numberOfModulesEnrolled = value;
    }

    /**
     * Gets the value of the numberOfRepeatModulesEnrolled property.
     * 
     */
    public int getNumberOfRepeatModulesEnrolled() {
        return numberOfRepeatModulesEnrolled;
    }

    /**
     * Sets the value of the numberOfRepeatModulesEnrolled property.
     * 
     */
    public void setNumberOfRepeatModulesEnrolled(int value) {
        this.numberOfRepeatModulesEnrolled = value;
    }

    /**
     * Gets the value of the numberOfModulesDiscontinued property.
     * 
     */
    public int getNumberOfModulesDiscontinued() {
        return numberOfModulesDiscontinued;
    }

    /**
     * Sets the value of the numberOfModulesDiscontinued property.
     * 
     */
    public void setNumberOfModulesDiscontinued(int value) {
        this.numberOfModulesDiscontinued = value;
    }

    /**
     * Gets the value of the numberOfModulesExcempted property.
     * 
     */
    public int getNumberOfModulesExcempted() {
        return numberOfModulesExcempted;
    }

    /**
     * Sets the value of the numberOfModulesExcempted property.
     * 
     */
    public void setNumberOfModulesExcempted(int value) {
        this.numberOfModulesExcempted = value;
    }

    /**
     * Gets the value of the numberOfModulesRecognition property.
     * 
     */
    public int getNumberOfModulesRecognition() {
        return numberOfModulesRecognition;
    }

    /**
     * Sets the value of the numberOfModulesRecognition property.
     * 
     */
    public void setNumberOfModulesRecognition(int value) {
        this.numberOfModulesRecognition = value;
    }

    /**
     * Gets the value of the entryLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntryLevel() {
        return entryLevel;
    }

    /**
     * Sets the value of the entryLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntryLevel(String value) {
        this.entryLevel = value;
    }

    /**
     * Gets the value of the studyType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudyType() {
        return studyType;
    }

    /**
     * Sets the value of the studyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudyType(String value) {
        this.studyType = value;
    }

}
