
package za.ac.nwu.wsdl.studentacademicregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for studentModulePrerequisteResultInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="studentModulePrerequisteResultInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="prereqPassed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="prereqResultMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="studentModuleAcademic" type="{http://nwu.ac.za/wsdl/StudentAcademicRegistration}studentModuleAcademicInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studentModulePrerequisteResultInfo", propOrder = {
    "prereqPassed",
    "prereqResultMessage",
    "studentModuleAcademic"
})
public class StudentModulePrerequisteResultInfo {

    protected boolean prereqPassed;
    protected String prereqResultMessage;
    protected StudentModuleAcademicInfo studentModuleAcademic;

    /**
     * Gets the value of the prereqPassed property.
     * 
     */
    public boolean isPrereqPassed() {
        return prereqPassed;
    }

    /**
     * Sets the value of the prereqPassed property.
     * 
     */
    public void setPrereqPassed(boolean value) {
        this.prereqPassed = value;
    }

    /**
     * Gets the value of the prereqResultMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrereqResultMessage() {
        return prereqResultMessage;
    }

    /**
     * Sets the value of the prereqResultMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrereqResultMessage(String value) {
        this.prereqResultMessage = value;
    }

    /**
     * Gets the value of the studentModuleAcademic property.
     * 
     * @return
     *     possible object is
     *     {@link StudentModuleAcademicInfo }
     *     
     */
    public StudentModuleAcademicInfo getStudentModuleAcademic() {
        return studentModuleAcademic;
    }

    /**
     * Sets the value of the studentModuleAcademic property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudentModuleAcademicInfo }
     *     
     */
    public void setStudentModuleAcademic(StudentModuleAcademicInfo value) {
        this.studentModuleAcademic = value;
    }

}
