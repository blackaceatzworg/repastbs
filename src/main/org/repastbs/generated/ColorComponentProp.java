//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.03.31 at 01:25:07 AM CEST 
//


package org.repastbs.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ColorComponentProp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ColorComponentProp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="red" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="green" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="blue" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ColorComponentProp", propOrder = {
    "red",
    "green",
    "blue"
})
public class ColorComponentProp {

    @XmlElement(required = true)
    protected BigInteger red;
    @XmlElement(required = true)
    protected BigInteger green;
    @XmlElement(required = true)
    protected BigInteger blue;

    /**
     * Gets the value of the red property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRed() {
        return red;
    }

    /**
     * Sets the value of the red property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRed(BigInteger value) {
        this.red = value;
    }

    /**
     * Gets the value of the green property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGreen() {
        return green;
    }

    /**
     * Sets the value of the green property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGreen(BigInteger value) {
        this.green = value;
    }

    /**
     * Gets the value of the blue property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBlue() {
        return blue;
    }

    /**
     * Sets the value of the blue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBlue(BigInteger value) {
        this.blue = value;
    }

}
