//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.21 at 04:25:42 PM CEST 
//


package org.repastbs.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="action" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="execution" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="tick" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="executeLast" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduledActionProp")
public class ScheduledActionProp {

    @XmlAttribute
    protected String action;
    @XmlAttribute(required = true)
    protected int execution;
    @XmlAttribute
    protected Integer tick;
    @XmlAttribute
    protected Boolean executeLast;
    @XmlAttribute(required = true)
    protected int index;

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * Gets the value of the execution property.
     * 
     */
    public int getExecution() {
        return execution;
    }

    /**
     * Sets the value of the execution property.
     * 
     */
    public void setExecution(int value) {
        this.execution = value;
    }

    /**
     * Gets the value of the tick property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTick() {
        return tick;
    }

    /**
     * Sets the value of the tick property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTick(Integer value) {
        this.tick = value;
    }

    /**
     * Gets the value of the executeLast property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExecuteLast() {
        return executeLast;
    }

    /**
     * Sets the value of the executeLast property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExecuteLast(Boolean value) {
        this.executeLast = value;
    }

    /**
     * Gets the value of the index property.
     * 
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     */
    public void setIndex(int value) {
        this.index = value;
    }

}
