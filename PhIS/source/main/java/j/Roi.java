//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.13 at 02:30:38 PM BST 
//


package j;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Roi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Roi">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="associated_immage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="associated_channel" type="{http://www.example.org/phisSchema}StringArray" minOccurs="0"/>
 *         &lt;element name="coordinates" type="{http://www.example.org/phisSchema}Coordinates"/>
 *         &lt;element name="anatomy_expression_annotations" type="{http://www.example.org/phisSchema}AnnotationArray" minOccurs="0"/>
 *         &lt;element name="anatomical_part_with_phenotype" type="{http://www.example.org/phisSchema}AnnotationArray" minOccurs="0"/>
 *         &lt;element name="anatomical_part_of_interest" type="{http://www.example.org/phisSchema}AnnotationArray" minOccurs="0"/>
 *         &lt;element name="phenotype_annotations" type="{http://www.example.org/phisSchema}AnnotationArray" minOccurs="0"/>
 *         &lt;element name="observations" type="{http://www.example.org/phisSchema}StringArray" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Roi", propOrder = {

})
public class Roi {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(name = "associated_immage", required = true)
    protected String associatedImmage;
    @XmlElement(name = "associated_channel")
    protected StringArray associatedChannel;
    @XmlElement(required = true)
    protected Coordinates coordinates;
    @XmlElement(name = "anatomy_expression_annotations")
    protected AnnotationArray anatomyExpressionAnnotations;
    @XmlElement(name = "anatomical_part_with_phenotype")
    protected AnnotationArray anatomicalPartWithPhenotype;
    @XmlElement(name = "anatomical_part_of_interest")
    protected AnnotationArray anatomicalPartOfInterest;
    @XmlElement(name = "phenotype_annotations")
    protected AnnotationArray phenotypeAnnotations;
    protected StringArray observations;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the associatedImmage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssociatedImmage() {
        return associatedImmage;
    }

    /**
     * Sets the value of the associatedImmage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssociatedImmage(String value) {
        this.associatedImmage = value;
    }

    /**
     * Gets the value of the associatedChannel property.
     * 
     * @return
     *     possible object is
     *     {@link StringArray }
     *     
     */
    public StringArray getAssociatedChannel() {
        return associatedChannel;
    }

    /**
     * Sets the value of the associatedChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringArray }
     *     
     */
    public void setAssociatedChannel(StringArray value) {
        this.associatedChannel = value;
    }

    /**
     * Gets the value of the coordinates property.
     * 
     * @return
     *     possible object is
     *     {@link Coordinates }
     *     
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the value of the coordinates property.
     * 
     * @param value
     *     allowed object is
     *     {@link Coordinates }
     *     
     */
    public void setCoordinates(Coordinates value) {
        this.coordinates = value;
    }

    /**
     * Gets the value of the anatomyExpressionAnnotations property.
     * 
     * @return
     *     possible object is
     *     {@link AnnotationArray }
     *     
     */
    public AnnotationArray getAnatomyExpressionAnnotations() {
        return anatomyExpressionAnnotations;
    }

    /**
     * Sets the value of the anatomyExpressionAnnotations property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnotationArray }
     *     
     */
    public void setAnatomyExpressionAnnotations(AnnotationArray value) {
        this.anatomyExpressionAnnotations = value;
    }

    /**
     * Gets the value of the anatomicalPartWithPhenotype property.
     * 
     * @return
     *     possible object is
     *     {@link AnnotationArray }
     *     
     */
    public AnnotationArray getAnatomicalPartWithPhenotype() {
        return anatomicalPartWithPhenotype;
    }

    /**
     * Sets the value of the anatomicalPartWithPhenotype property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnotationArray }
     *     
     */
    public void setAnatomicalPartWithPhenotype(AnnotationArray value) {
        this.anatomicalPartWithPhenotype = value;
    }

    /**
     * Gets the value of the anatomicalPartOfInterest property.
     * 
     * @return
     *     possible object is
     *     {@link AnnotationArray }
     *     
     */
    public AnnotationArray getAnatomicalPartOfInterest() {
        return anatomicalPartOfInterest;
    }

    /**
     * Sets the value of the anatomicalPartOfInterest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnotationArray }
     *     
     */
    public void setAnatomicalPartOfInterest(AnnotationArray value) {
        this.anatomicalPartOfInterest = value;
    }

    /**
     * Gets the value of the phenotypeAnnotations property.
     * 
     * @return
     *     possible object is
     *     {@link AnnotationArray }
     *     
     */
    public AnnotationArray getPhenotypeAnnotations() {
        return phenotypeAnnotations;
    }

    /**
     * Sets the value of the phenotypeAnnotations property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnnotationArray }
     *     
     */
    public void setPhenotypeAnnotations(AnnotationArray value) {
        this.phenotypeAnnotations = value;
    }

    /**
     * Gets the value of the observations property.
     * 
     * @return
     *     possible object is
     *     {@link StringArray }
     *     
     */
    public StringArray getObservations() {
        return observations;
    }

    /**
     * Sets the value of the observations property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringArray }
     *     
     */
    public void setObservations(StringArray value) {
        this.observations = value;
    }

}
