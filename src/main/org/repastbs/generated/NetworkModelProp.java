//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.04 at 12:34:07 AM CEST 
//


package org.repastbs.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://repastbs/networkModel}ModelProp">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://repastbs/components}StringComponentProp"/>
 *         &lt;element name="displayName" type="{http://repastbs/components}StringComponentProp"/>
 *         &lt;element name="description" type="{http://repastbs/components}StringComponentProp"/>
 *         &lt;element name="variables" type="{http://repastbs/components}VariablesProp"/>
 *         &lt;element name="actions" type="{http://repastbs/components}ActionsProp"/>
 *         &lt;element name="schedule" type="{http://repastbs/components}ScheduleProp"/>
 *         &lt;element name="agents" type="{http://repastbs/components}AgentsProp"/>
 *         &lt;element name="networkDisplay" type="{http://repastbs/components}NetworkDisplayProp"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name",
    "displayName",
    "description",
    "variables",
    "actions",
    "schedule",
    "agents",
    "networkDisplay"
})
@XmlRootElement(name = "NetworkModelProp", namespace = "http://repastbs/networkModel")
public class NetworkModelProp
    extends ModelProp
{

    @XmlElement(required = true)
    protected StringComponentProp name;
    @XmlElement(required = true)
    protected StringComponentProp displayName;
    @XmlElement(required = true)
    protected StringComponentProp description;
    @XmlElement(required = true)
    protected VariablesProp variables;
    @XmlElement(required = true)
    protected ActionsProp actions;
    @XmlElement(required = true)
    protected ScheduleProp schedule;
    @XmlElement(required = true)
    protected AgentsProp agents;
    @XmlElement(required = true)
    protected NetworkDisplayProp networkDisplay;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link StringComponentProp }
     *     
     */
    public StringComponentProp getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringComponentProp }
     *     
     */
    public void setName(StringComponentProp value) {
        this.name = value;
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
     * Gets the value of the agents property.
     * 
     * @return
     *     possible object is
     *     {@link AgentsProp }
     *     
     */
    public AgentsProp getAgents() {
        return agents;
    }

    /**
     * Sets the value of the agents property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentsProp }
     *     
     */
    public void setAgents(AgentsProp value) {
        this.agents = value;
    }

    /**
     * Gets the value of the networkDisplay property.
     * 
     * @return
     *     possible object is
     *     {@link NetworkDisplayProp }
     *     
     */
    public NetworkDisplayProp getNetworkDisplay() {
        return networkDisplay;
    }

    /**
     * Sets the value of the networkDisplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link NetworkDisplayProp }
     *     
     */
    public void setNetworkDisplay(NetworkDisplayProp value) {
        this.networkDisplay = value;
    }

}
