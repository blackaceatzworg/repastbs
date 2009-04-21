//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.21 at 12:14:41 AM CEST 
//


package org.repastbs.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ModelProp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ModelProp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://repastbs/components}StringComponentProp"/>
 *         &lt;element name="modelName" type="{http://repastbs/components}StringComponentProp"/>
 *         &lt;element name="displayName" type="{http://repastbs/components}StringComponentProp"/>
 *       &lt;/sequence>
 *       &lt;attribute name="modelClass" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModelProp", propOrder = {
    "description",
    "modelName",
    "displayName"
})
@XmlSeeAlso({
    GisModelProp.class
})
public class ModelProp {

    @XmlElement(required = true, nillable = true)
    protected StringComponentProp description;
    @XmlElement(required = true, nillable = true)
    protected StringComponentProp modelName;
    @XmlElement(required = true, nillable = true)
    protected StringComponentProp displayName;
    @XmlAttribute(required = true)
    protected String modelClass;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link StringComponentProp }
     *     
     */
    public StringComponentProp getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringComponentProp }
     *     
     */
    public void setDescription(StringComponentProp value) {
        this.description = value;
    }

    /**
     * Gets the value of the modelName property.
     * 
     * @return
     *     possible object is
     *     {@link StringComponentProp }
     *     
     */
    public StringComponentProp getModelName() {
        return modelName;
    }

    /**
     * Sets the value of the modelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringComponentProp }
     *     
     */
    public void setModelName(StringComponentProp value) {
        this.modelName = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link StringComponentProp }
     *     
     */
    public StringComponentProp getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringComponentProp }
     *     
     */
    public void setDisplayName(StringComponentProp value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the modelClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelClass() {
        return modelClass;
    }

    /**
     * Sets the value of the modelClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelClass(String value) {
        this.modelClass = value;
    }

}
