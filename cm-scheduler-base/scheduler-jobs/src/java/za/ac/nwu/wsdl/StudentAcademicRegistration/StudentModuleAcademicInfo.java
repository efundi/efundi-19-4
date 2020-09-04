
package za.ac.nwu.wsdl.StudentAcademicRegistration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for studentModuleAcademicInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="studentModuleAcademicInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="academicPeriod" type="{http://nwu.ac.za/wsdl/StudentAcademicRegistration}AcademicPeriodInfo" minOccurs="0"/>
 *         &lt;element name="conditionalRegistration" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="exemption" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="exemptionTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legacyKeys">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="module" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moduleCreditTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moduleDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moduleDiscontinueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="moduleMethodOfDeliveryTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modulePeriodTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modulePresentationCategoryTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moduleRegistrationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="moduleResultTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moduleSite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="moduleTypeTypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="program" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="qualification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="repeatingModule" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="univNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studentModuleAcademicInfo", propOrder = {
    "academicPeriod",
    "conditionalRegistration",
    "exemption",
    "exemptionTypeKey",
    "legacyKeys",
    "module",
    "moduleCreditTypeKey",
    "moduleDescription",
    "moduleDiscontinueDate",
    "moduleMethodOfDeliveryTypeKey",
    "modulePeriodTypeKey",
    "modulePresentationCategoryTypeKey",
    "moduleRegistrationDate",
    "moduleResultTypeKey",
    "moduleSite",
    "moduleTypeTypeKey",
    "program",
    "qualification",
    "repeatingModule",
    "univNumber"
})
public class StudentModuleAcademicInfo {

    protected AcademicPeriodInfo academicPeriod;
    protected boolean conditionalRegistration;
    protected boolean exemption;
    protected String exemptionTypeKey;
    @XmlElement(required = true)
    protected StudentModuleAcademicInfo.LegacyKeys legacyKeys;
    protected String module;
    protected String moduleCreditTypeKey;
    protected String moduleDescription;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar moduleDiscontinueDate;
    protected String moduleMethodOfDeliveryTypeKey;
    protected String modulePeriodTypeKey;
    protected String modulePresentationCategoryTypeKey;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar moduleRegistrationDate;
    protected String moduleResultTypeKey;
    protected int moduleSite;
    protected String moduleTypeTypeKey;
    protected String program;
    protected String qualification;
    protected boolean repeatingModule;
    protected String univNumber;

    /**
     * Gets the value of the academicPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link AcademicPeriodInfo }
     *     
     */
    public AcademicPeriodInfo getAcademicPeriod() {
        return academicPeriod;
    }

    /**
     * Sets the value of the academicPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcademicPeriodInfo }
     *     
     */
    public void setAcademicPeriod(AcademicPeriodInfo value) {
        this.academicPeriod = value;
    }

    /**
     * Gets the value of the conditionalRegistration property.
     * 
     */
    public boolean isConditionalRegistration() {
        return conditionalRegistration;
    }

    /**
     * Sets the value of the conditionalRegistration property.
     * 
     */
    public void setConditionalRegistration(boolean value) {
        this.conditionalRegistration = value;
    }

    /**
     * Gets the value of the exemption property.
     * 
     */
    public boolean isExemption() {
        return exemption;
    }

    /**
     * Sets the value of the exemption property.
     * 
     */
    public void setExemption(boolean value) {
        this.exemption = value;
    }

    /**
     * Gets the value of the exemptionTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExemptionTypeKey() {
        return exemptionTypeKey;
    }

    /**
     * Sets the value of the exemptionTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExemptionTypeKey(String value) {
        this.exemptionTypeKey = value;
    }

    /**
     * Gets the value of the legacyKeys property.
     * 
     * @return
     *     possible object is
     *     {@link StudentModuleAcademicInfo.LegacyKeys }
     *     
     */
    public StudentModuleAcademicInfo.LegacyKeys getLegacyKeys() {
        return legacyKeys;
    }

    /**
     * Sets the value of the legacyKeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudentModuleAcademicInfo.LegacyKeys }
     *     
     */
    public void setLegacyKeys(StudentModuleAcademicInfo.LegacyKeys value) {
        this.legacyKeys = value;
    }

    /**
     * Gets the value of the module property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModule() {
        return module;
    }

    /**
     * Sets the value of the module property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModule(String value) {
        this.module = value;
    }

    /**
     * Gets the value of the moduleCreditTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleCreditTypeKey() {
        return moduleCreditTypeKey;
    }

    /**
     * Sets the value of the moduleCreditTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleCreditTypeKey(String value) {
        this.moduleCreditTypeKey = value;
    }

    /**
     * Gets the value of the moduleDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleDescription() {
        return moduleDescription;
    }

    /**
     * Sets the value of the moduleDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleDescription(String value) {
        this.moduleDescription = value;
    }

    /**
     * Gets the value of the moduleDiscontinueDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModuleDiscontinueDate() {
        return moduleDiscontinueDate;
    }

    /**
     * Sets the value of the moduleDiscontinueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModuleDiscontinueDate(XMLGregorianCalendar value) {
        this.moduleDiscontinueDate = value;
    }

    /**
     * Gets the value of the moduleMethodOfDeliveryTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleMethodOfDeliveryTypeKey() {
        return moduleMethodOfDeliveryTypeKey;
    }

    /**
     * Sets the value of the moduleMethodOfDeliveryTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleMethodOfDeliveryTypeKey(String value) {
        this.moduleMethodOfDeliveryTypeKey = value;
    }

    /**
     * Gets the value of the modulePeriodTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModulePeriodTypeKey() {
        return modulePeriodTypeKey;
    }

    /**
     * Sets the value of the modulePeriodTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModulePeriodTypeKey(String value) {
        this.modulePeriodTypeKey = value;
    }

    /**
     * Gets the value of the modulePresentationCategoryTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModulePresentationCategoryTypeKey() {
        return modulePresentationCategoryTypeKey;
    }

    /**
     * Sets the value of the modulePresentationCategoryTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModulePresentationCategoryTypeKey(String value) {
        this.modulePresentationCategoryTypeKey = value;
    }

    /**
     * Gets the value of the moduleRegistrationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModuleRegistrationDate() {
        return moduleRegistrationDate;
    }

    /**
     * Sets the value of the moduleRegistrationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModuleRegistrationDate(XMLGregorianCalendar value) {
        this.moduleRegistrationDate = value;
    }

    /**
     * Gets the value of the moduleResultTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleResultTypeKey() {
        return moduleResultTypeKey;
    }

    /**
     * Sets the value of the moduleResultTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleResultTypeKey(String value) {
        this.moduleResultTypeKey = value;
    }

    /**
     * Gets the value of the moduleSite property.
     * 
     */
    public int getModuleSite() {
        return moduleSite;
    }

    /**
     * Sets the value of the moduleSite property.
     * 
     */
    public void setModuleSite(int value) {
        this.moduleSite = value;
    }

    /**
     * Gets the value of the moduleTypeTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleTypeTypeKey() {
        return moduleTypeTypeKey;
    }

    /**
     * Sets the value of the moduleTypeTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleTypeTypeKey(String value) {
        this.moduleTypeTypeKey = value;
    }

    /**
     * Gets the value of the program property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgram() {
        return program;
    }

    /**
     * Sets the value of the program property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgram(String value) {
        this.program = value;
    }

    /**
     * Gets the value of the qualification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * Sets the value of the qualification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualification(String value) {
        this.qualification = value;
    }

    /**
     * Gets the value of the repeatingModule property.
     * 
     */
    public boolean isRepeatingModule() {
        return repeatingModule;
    }

    /**
     * Sets the value of the repeatingModule property.
     * 
     */
    public void setRepeatingModule(boolean value) {
        this.repeatingModule = value;
    }

    /**
     * Gets the value of the univNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnivNumber() {
        return univNumber;
    }

    /**
     * Sets the value of the univNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnivNumber(String value) {
        this.univNumber = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entry"
    })
    public static class LegacyKeys {

        protected List<StudentModuleAcademicInfo.LegacyKeys.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link StudentModuleAcademicInfo.LegacyKeys.Entry }
         * 
         * 
         */
        public List<StudentModuleAcademicInfo.LegacyKeys.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<StudentModuleAcademicInfo.LegacyKeys.Entry>();
            }
            return this.entry;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entry {

            protected String key;
            protected Integer value;

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKey(String value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setValue(Integer value) {
                this.value = value;
            }

        }

    }

}
