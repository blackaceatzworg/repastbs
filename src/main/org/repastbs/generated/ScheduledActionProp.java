//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1.6-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.03.14 at 12:31:27 AM CET 
//


package org.repastbs.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScheduledActionProp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScheduledActionProp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="execution" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="tick" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="executeLast" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduledActionProp", propOrder = {
    "execution",
    "tick",
    "executeLast",
    "index"
})
public class ScheduledActionProp {

    @XmlElement(required = true)
    protected BigInteger execution;
    @XmlElement(required = true)
    protected BigInteger tick;
    protected boolean executeLast;
    @XmlElement(required = true)
    protected BigInteger index;

    /**
     * Gets the value of the execution property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getExecution() {
        return execution;
    }

    /**
     * Sets the value of the execution property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setExecution(BigInteger value) {
        this.execution = value;
    }

    /**
     * Gets the value of the tick property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTick() {
        return tick;
    }

    /**
     * Sets the value of the tick property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTick(BigInteger value) {
        this.tick = value;
    }

    /**
     * Gets the value of the executeLast property.
     * 
     */
    public boolean isExecuteLast() {
        return executeLast;
    }

    /**
     * Sets the value of the executeLast property.
     * 
     */
    public void setExecuteLast(boolean value) {
        this.executeLast = value;
    }

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIndex(BigInteger value) {
        this.index = value;
    }

}