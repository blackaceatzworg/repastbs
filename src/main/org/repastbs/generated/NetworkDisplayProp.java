//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.21 at 04:25:42 PM CEST 
//


package org.repastbs.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NetworkDisplayProp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NetworkDisplayProp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="width" type="{http://repastbs/components}IntegerComponentProp"/>
 *         &lt;element name="height" type="{http://repastbs/components}IntegerComponentProp"/>
 *         &lt;element name="actions" type="{http://repastbs/components}ActionsProp"/>
 *         &lt;element name="schedule" type="{http://repastbs/components}ScheduleProp"/>
 *         &lt;element name="layout" type="{http://repastbs/components}NetworkLayoutProp"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NetworkDisplayProp", propOrder = {
    "width",
    "height",
    "actions",
    "schedule",
    "layout"
})
public class NetworkDisplayProp {

    @XmlElement(required = true)
    protected IntegerComponentProp width;
    @XmlElement(required = true)
    protected IntegerComponentProp height;
    @XmlElement(required = true)
    protected ActionsProp actions;
    @XmlElement(required = true)
    protected ScheduleProp schedule;
    @XmlElement(required = true)
    protected NetworkLayoutProp layout;

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link IntegerComponentProp }
     *     
     */
    public IntegerComponentProp getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegerComponentProp }
     *     
     */
    public void setWidth(IntegerComponentProp value) {
        this.width = value;
    }

    /**
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link IntegerComponentProp }
     *     
     */
    public IntegerComponentProp getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegerComponentProp }
     *     
     */
    public void setHeight(IntegerComponentProp value) {
        this.height = value;
    }

    /**
     * Gets the value of the actions property.
     * 
     * @return
     *     possible object is
     *     {@link ActionsProp }
     *     
     */
    public ActionsProp getActions() {
        return actions;
    }

    /**
     * Sets the value of the actions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionsProp }
     *     
     */
    public void setActions(ActionsProp value) {
        this.actions = value;
    }

    /**
     * Gets the value of the schedule property.
     * 
     * @return
     *     possible object is
     *     {@link ScheduleProp }
     *     
     */
    public ScheduleProp getSchedule() {
        return schedule;
    }

    /**
     * Sets the value of the schedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleProp }
     *     
     */
    public void setSchedule(ScheduleProp value) {
        this.schedule = value;
    }

    /**
     * Gets the value of the layout property.
     * 
     * @return
     *     possible object is
     *     {@link NetworkLayoutProp }
     *     
     */
    public NetworkLayoutProp getLayout() {
        return layout;
    }

    /**
     * Sets the value of the layout property.
     * 
     * @param value
     *     allowed object is
     *     {@link NetworkLayoutProp }
     *     
     */
    public void setLayout(NetworkLayoutProp value) {
        this.layout = value;
    }

}
