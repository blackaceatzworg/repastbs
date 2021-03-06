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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DefaultAgentProp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DefaultAgentProp">
 *   &lt;complexContent>
 *     &lt;extension base="{http://repastbs/components}AgentProp">
 *       &lt;sequence>
 *         &lt;element name="actions" type="{http://repastbs/components}ActionsProp"/>
 *         &lt;element name="schedule" type="{http://repastbs/components}ScheduleProp"/>
 *         &lt;element name="variables" type="{http://repastbs/components}VariablesProp"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DefaultAgentProp", propOrder = {
    "actions",
    "schedule",
    "variables"
})
@XmlSeeAlso({
    NetworkAgentProp.class,
    GridAgentProp.class,
    GisAgentProp.class
})
public abstract class DefaultAgentProp
    extends AgentProp
{

    @XmlElement(required = true)
    protected ActionsProp actions;
    @XmlElement(required = true)
    protected ScheduleProp schedule;
    @XmlElement(required = true)
    protected VariablesProp variables;

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
     * Gets the value of the variables property.
     * 
     * @return
     *     possible object is
     *     {@link VariablesProp }
     *     
     */
    public VariablesProp getVariables() {
        return variables;
    }

    /**
     * Sets the value of the variables property.
     * 
     * @param value
     *     allowed object is
     *     {@link VariablesProp }
     *     
     */
    public void setVariables(VariablesProp value) {
        this.variables = value;
    }

}
