//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.06 at 10:42:09 PM CEST 
//


package org.repastbs.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnlinkedNetworkProp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnlinkedNetworkProp">
 *   &lt;complexContent>
 *     &lt;extension base="{http://repastbs/components}NetworkTypeProp">
 *       &lt;sequence>
 *         &lt;element name="countVar" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnlinkedNetworkProp", propOrder = {
    "countVar"
})
public class UnlinkedNetworkProp
    extends NetworkTypeProp
{

    @XmlElement(required = true)
    protected String countVar;

    /**
     * Gets the value of the countVar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountVar() {
        return countVar;
    }

    /**
     * Sets the value of the countVar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountVar(String value) {
        this.countVar = value;
    }

}