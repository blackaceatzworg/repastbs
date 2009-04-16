//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-792 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.15 at 01:07:41 AM CEST 
//


package org.repastbs.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SquareLatticeNetworkProp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SquareLatticeNetworkProp">
 *   &lt;complexContent>
 *     &lt;extension base="{http://repastbs/components}NetworkTypeProp">
 *       &lt;sequence>
 *         &lt;element name="colsVar" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="connectRadiusVar" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rowsVar" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="wrapAroundVar" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SquareLatticeNetworkProp", propOrder = {
    "colsVar",
    "connectRadiusVar",
    "rowsVar",
    "wrapAroundVar"
})
public class SquareLatticeNetworkProp
    extends NetworkTypeProp
{

    @XmlElement(required = true)
    protected String colsVar;
    @XmlElement(required = true)
    protected String connectRadiusVar;
    @XmlElement(required = true)
    protected String rowsVar;
    @XmlElement(required = true)
    protected String wrapAroundVar;

    /**
     * Gets the value of the colsVar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColsVar() {
        return colsVar;
    }

    /**
     * Sets the value of the colsVar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColsVar(String value) {
        this.colsVar = value;
    }

    /**
     * Gets the value of the connectRadiusVar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectRadiusVar() {
        return connectRadiusVar;
    }

    /**
     * Sets the value of the connectRadiusVar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectRadiusVar(String value) {
        this.connectRadiusVar = value;
    }

    /**
     * Gets the value of the rowsVar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRowsVar() {
        return rowsVar;
    }

    /**
     * Sets the value of the rowsVar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRowsVar(String value) {
        this.rowsVar = value;
    }

    /**
     * Gets the value of the wrapAroundVar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWrapAroundVar() {
        return wrapAroundVar;
    }

    /**
     * Sets the value of the wrapAroundVar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWrapAroundVar(String value) {
        this.wrapAroundVar = value;
    }

}
