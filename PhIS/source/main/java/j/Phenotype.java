//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.29 at 02:53:57 PM BST 
//


package j;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Phenotype complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Phenotype">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="phenotype_ontology_id" type="{http://www.example.org/phisSchema}StringArray" minOccurs="0"/>
 *         &lt;element name="phenotype_freetext" type="{http://www.example.org/phisSchema}StringArray" minOccurs="0"/>
 *         &lt;element name="phenotype_ontology_term" type="{http://www.example.org/phisSchema}StringArray" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Phenotype", propOrder = {

})
public class Phenotype {

    @XmlElement(name = "phenotype_ontology_id")
    protected StringArray phenotypeOntologyId;
    @XmlElement(name = "phenotype_freetext")
    protected StringArray phenotypeFreetext;
    @XmlElement(name = "phenotype_ontology_term")
    protected StringArray phenotypeOntologyTerm;

    /**
     * Gets the value of the phenotypeOntologyId property.
     * 
     * @return
     *     possible object is
     *     {@link StringArray }
     *     
     */
    public StringArray getPhenotypeOntologyId() {
        return phenotypeOntologyId;
    }

    /**
     * Sets the value of the phenotypeOntologyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringArray }
     *     
     */
    public void setPhenotypeOntologyId(StringArray value) {
        this.phenotypeOntologyId = value;
    }

    /**
     * Gets the value of the phenotypeFreetext property.
     * 
     * @return
     *     possible object is
     *     {@link StringArray }
     *     
     */
    public StringArray getPhenotypeFreetext() {
        return phenotypeFreetext;
    }

    /**
     * Sets the value of the phenotypeFreetext property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringArray }
     *     
     */
    public void setPhenotypeFreetext(StringArray value) {
        this.phenotypeFreetext = value;
    }

    /**
     * Gets the value of the phenotypeOntologyTerm property.
     * 
     * @return
     *     possible object is
     *     {@link StringArray }
     *     
     */
    public StringArray getPhenotypeOntologyTerm() {
        return phenotypeOntologyTerm;
    }

    /**
     * Sets the value of the phenotypeOntologyTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringArray }
     *     
     */
    public void setPhenotypeOntologyTerm(StringArray value) {
        this.phenotypeOntologyTerm = value;
    }

}
