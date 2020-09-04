
package za.ac.nwu.wsdl.studentacademicregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validateStudentModulePrerequisiteResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validateStudentModulePrerequisiteResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://nwu.ac.za/wsdl/StudentAcademicRegistration}studentModulePrerequisteResultInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateStudentModulePrerequisiteResponse", propOrder = {
    "_return"
})
public class ValidateStudentModulePrerequisiteResponse {

    @XmlElement(name = "return")
    protected StudentModulePrerequisteResultInfo _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link StudentModulePrerequisteResultInfo }
     *     
     */
    public StudentModulePrerequisteResultInfo getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudentModulePrerequisteResultInfo }
     *     
     */
    public void setReturn(StudentModulePrerequisteResultInfo value) {
        this._return = value;
    }

}
