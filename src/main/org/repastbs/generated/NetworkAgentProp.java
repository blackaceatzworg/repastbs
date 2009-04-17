//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.16 at 11:15:33 PM CEST 
//


package org.repastbs.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NetworkAgentProp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NetworkAgentProp">
 *   &lt;complexContent>
 *     &lt;extension base="{http://repastbs/components}DefaultAgentProp">
 *       &lt;sequence>
 *         &lt;element name="networkType" type="{http://repastbs/components}NetworkTypeProp"/>
 *         &lt;element name="interface" type="{http://repastbs/components}GameAgentInterfaceProp"/>
 *       &lt;/sequence>
 *       &lt;attribute name="groupName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NetworkAgentProp", propOrder = {
    "networkType",
    "_interface"
})
public class NetworkAgentProp
    extends DefaultAgentProp
{

    @XmlElement(required = true)
    protected NetworkTypeProp networkType;
    @XmlElement(name = "interface", required = true)
    protected GameAgentInterfaceProp _interface;
    @XmlAttribute(required = true)
    protected String groupName;

    /**
     * Gets the value of the networkType property.
     * 
     * @return
     *     possible object is
     *     {@link NetworkTypeProp }
     *     
     */
    public NetworkTypeProp getNetworkType() {
        return networkType;
    }

    /**
     * Sets the value of the networkType property.
     * 
     * @param value
     *     allowed object is
     *     {@link NetworkTypeProp }
     *     
     */
    public void setNetworkType(NetworkTypeProp value) {
        this.networkType = value;
    }

    /**
     * Gets the value of the interface property.
     * 
     * @return
     *     possible object is
     *     {@link GameAgentInterfaceProp }
     *     
     */
    public GameAgentInterfaceProp getInterface() {
        return _interface;
    }

    /**
     * Sets the value of the interface property.
     * 
     * @param value
     *     allowed object is
     *     {@link GameAgentInterfaceProp }
     *     
     */
    public void setInterface(GameAgentInterfaceProp value) {
        this._interface = value;
    }

    /**
     * Gets the value of the groupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the value of the groupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupName(String value) {
        this.groupName = value;
    }

}
