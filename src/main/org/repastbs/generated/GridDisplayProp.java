//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.04 at 12:34:09 AM CEST 
//


package org.repastbs.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GridDisplayProp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GridDisplayProp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="color" type="{http://repastbs/components}ColorComponentProp"/>
 *         &lt;element name="actions" type="{http://repastbs/components}ActionsProp"/>
 *         &lt;element name="schedule" type="{http://repastbs/components}ScheduleProp"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GridDisplayProp", propOrder = {
    "color",
    "actions",
    "schedule"
})
public class GridDisplayProp {

    @XmlElement(required = true)
    protected ColorComponentProp color;
    @XmlElement(required = true)
    protected ActionsProp actions;
    @XmlElement(required = true)
    protected ScheduleProp schedule;

    /**
     * Gets the value of the color property.
     * 
     * @return
     *     possible object is
     *     {@link ColorComponentProp }
     *     
     */
    public ColorComponentProp getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     * @param value
     *     allowed object is
     *     {@link ColorComponentProp }
     *     
     */
    public void setColor(ColorComponentProp value) {
        this.color = value;
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

}
