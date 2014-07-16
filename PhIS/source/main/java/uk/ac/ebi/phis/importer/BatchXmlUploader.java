package uk.ac.ebi.phis.importer;

import j.Channel;
import j.Doc;
import j.Image;
import j.ImageDescription;
import j.OntologyTerm;
import j.Roi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.xml.sax.SAXException;

import uk.ac.ebi.phis.solrj.pojo.ImagePojo;
import uk.ac.ebi.phis.utils.ValidationUtils;

public class BatchXmlUploader {

	HashMap<String, Image> imageIdMap = new HashMap<>();
	HashMap<String, Channel> channelIdMap = new HashMap<>();
	HashMap<String, Roi> roiIdMap = new HashMap<>();

	ClassLoader classloader;
	HttpSolrServer solrImage;

	String solrImageBaseUrl = "http://localhost:8983/solr/collection1";

	ValidationUtils vu = new ValidationUtils();


	public BatchXmlUploader() {

		classloader = Thread.currentThread().getContextClassLoader();
		solrImage = new HttpSolrServer(solrImageBaseUrl);
	}


	public boolean validateAndUpload(String xmlLocation) {

		Doc doc;
		// Unmarshal XML
		doc = convertXmlToObjects(xmlLocation);
		boolean isValid = validate(xmlLocation, doc);
		upload(doc);
		return isValid;
	}


	private void upload(Doc doc) {

		try {
			doBatchSubmission(doc);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}


	private boolean validate(String xmlLocation, Doc doc) {

		InputStream xsd;
		InputStream xml;
		boolean isValid = false;
		try {
			xsd = classloader.getResourceAsStream("phisSchema.xsd");
			xml = classloader.getResourceAsStream(xmlLocation);
			isValid = validateAgainstXSD(xml, xsd);
			xsd.close();
			xml.close();
			isValid = (isValid && checkInformation(doc));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isValid;
	}


	private void doBatchSubmission(Doc doc)
	throws IOException, SolrServerException {

		solrImage.deleteByQuery("*:*");
		addImageDocuments(doc.getImage());
		solrImage.commit();
		
	}


	private void addImageDocuments(List<Image> images)
	throws IOException, SolrServerException {
		
		int i = 0;
		for (Image img : images) {
			// add it
			solrImage.addBean(fillPojo(img));
			// flush every 1000 docs
			if (i++ % 1000 == 0) {
				solrImage.commit();
			}
		}

		solrImage.commit();
	}


	private ImagePojo fillPojo(Image img) {

		ImagePojo bean = new ImagePojo();
		bean.setTaxon(img.getOrganism().getTaxon());
		bean.setId(img.getId());

		ImageDescription desc = img.getImageDescription();
		bean.setImageGeneratedBy(desc.getImageGeneratedBy());
		bean.setSampleGeneratedBy(desc.getOrganismGeneratedBy());
		bean.setHostName(desc.getHostName());
		bean.setHostUrl(desc.getHostUrl());
		bean.setImageUrl(desc.getImageUrl());
		bean.setOriginalImageId(desc.getOriginalImageId());
		if (desc.getImageContextUrl() != null){
			bean.setImageContextUrl(desc.getImageContextUrl());
		}

		
		bean.setAssociatedRoi(img.getAssociatedRoi().getEl());;

		bean.setAssociatedChannel(img.getAssociatedChannel().getEl());;

		bean.setDepth(desc.getImageDimensions().getImageDepth());

		bean.setHeight(desc.getImageDimensions().getImageHeight());

		bean.setWidth(desc.getImageDimensions().getImageWidth());

		if (desc.getImagingMethod() != null){
			for (OntologyTerm im: desc.getImagingMethod().getEl()){
				bean.setImagingMethodId(im.getTermId());
				bean.setImagingMethodLabel(im.getTermLabel());
			}
		}
		if (desc.getSamplePreparation() != null){
			for (OntologyTerm sp: desc.getSamplePreparation().getEl()){
				bean.setSamplePreparationId(sp.getTermId());
				bean.setSamplePreparationLabel(sp.getTermLabel());
			}
		}
		if (desc.getVisualisationMethod() != null){
			for (OntologyTerm vm: desc.getVisualisationMethod().getEl()){
				bean.setVisualisationMethodId(vm.getTermId());
				bean.setVisualisationMethodLabel(vm.getTermLabel());
			}
		}
		
		if (desc.getMachine() != null){
			bean.setMachine(desc.getMachine());			
		}

		// TODO bean.setThumbnailPath(thumbnailPath);;

		// Sample
		
		if (img.getOrganism().getAge() != null){
			if (img.getOrganism().getAge().getAgeSinceBirth() != null){
				bean.setAgeSinceBirth(img.getOrganism().getAge().getAgeSinceBirth());
			}
		}

		bean.setNcbiTaxonId(ncbiTaxonId);

		bean.setSex(sex);

		bean.setStage(stage);


		// annotations -->

		bean.setAnatomyId(anatomyId);

		bean.setAnatomyTerm(anatomyTerm);

		bean.setAnatomyFreetext(anatomyFreetext);

		// field name="anatomy_computed_id" /-->
		// field name="anatomy_computed_term" /-->
		// field name="anatomy_ann_bag" /-->
		// field name="other_ann_bag" /-->
		// field name="phenotype_ann_bag" /-->

		bean.setObservations(observations);

		bean.setConditions(conditions);

		// genetic features -->

		bean.setGeneIds(geneIds);

		bean.setGeneSymbols(geneSymbols);

		bean.setGeneticFeatureIds(geneticFeatureIds);

		bean.setGeneticFeatureSymbols(geneticFeatureSymbols);

		bean.setGenetifFeatureEnsemlIds(genetifFeatureEnsemlIds);

		// field name="expressed_gf_bag" /-->
		// field name="expressed_anatomy_bag" /-->
		bean.setChromosome(chromosome);

		bean.setInsertionSite(insertionSite);

		bean.setStartPosition(startPosition);

		bean.setEndPosition(endPosition);

		bean.setStrand(strand);

		bean.setZygosity(zygosity);
		
		return bean;
	}


	boolean validateAgainstXSD(InputStream xml, InputStream xsd) {

		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new StreamSource(xsd));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(xml));
			return true;
		} catch (SAXException e) {
			System.out.println("NOT valid");
			System.out.println("Reason: " + e.getLocalizedMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	boolean checkInformation(Doc doc) {

		imageIdMap = new HashMap<>();
		channelIdMap = new HashMap<>();
		roiIdMap = new HashMap<>();

		// Create maps <id, Object> for quick reference
		for (Image img : doc.getImage()) {
			imageIdMap.put(img.getId(), img);
		}
		for (Roi roi : doc.getRoi()) {
			roiIdMap.put(roi.getId(), roi);
		}
		for (Channel ch : doc.getChannel()) {
			channelIdMap.put(ch.getId(), ch);
		}

		// Check associated image/channel/roi ids are valid a) they exist , b)
		// the link is reflezive
		boolean res = checkIdsReferenceEWxistingObjects();
		if (!res) { return false; }

		for (Image img : imageIdMap.values()) {

			// Check ontoloy fields contain ontology IDs and they are from the
			// right ontology
			// Check label & id match
			if (!vu.hasValidOntologyTerms(img)) {
				System.out.println("there was something wrong with the ontology terms for img id = " + img.getId());

			}

			// positive dimensions
			if (!vu.hasPositieDimensions(img.getImageDescription().getImageDimensions())) {
				System.out.println("Dimensions are not positive! Validation failed.");
				return false;
			}

		}

		for (Roi roi : roiIdMap.values()) {

			// percentages
			if (!vu.arePercentagesOk(roi.getCoordinates())) { return false; }

			// Check ontoloy fields contain ontology IDs and they are from the
			// right ontology
			// Check label & id match
			if (!vu.isValidOntologyTerms(roi)) {
				System.out.println("there was something wrong with the ontology terms for roi id = " + roi.getId());
				return false;
			}
		}
		return true;
	}


	private boolean checkIdsReferenceEWxistingObjects() {

		// Associated roi & channel for image really exist
		for (Image img : imageIdMap.values()) {
			if (img.getAssociatedRoi() != null) {
				for (String roiId : img.getAssociatedRoi().getEl()) {
					if (!roiIdMap.containsKey(roiId)) {
						System.out.println("roi id referenced without existing in image id = " + img.getId());
						return false;
					}
				}
			}
			if (img.getAssociatedChannel() != null) {
				for (String channelId : img.getAssociatedChannel().getEl()) {
					if (!channelIdMap.containsKey(channelId)) {
						System.out.println("channel id referenced without existing in image id = " + img.getId());
						return false;
					}
				}
			}
		}

		// Associated roi & image for channels really exist
		for (Channel channel : channelIdMap.values()) {
			if (channel.getAssociatedRoi() != null) {
				for (String roiId : channel.getAssociatedRoi().getEl()) {
					if (!roiIdMap.containsKey(roiId)) {
						System.out.println("roi id referenced without existing in channel id = " + channel.getId());
						return false;
					}
				}
			}
			if (channel.getAssociatedImage() != null) {
				if (!imageIdMap.containsKey(channel.getAssociatedImage())) {
					System.out.println("image id referenced without existing in channel id = " + channel.getId());
					return false;
				}
			}
		}

		// Associated image & channel for roi really exist
		for (Roi roi : roiIdMap.values()) {
			if (roi.getAssociatedChannel() != null) {
				for (String channelId : roi.getAssociatedChannel().getEl()) {
					if (!channelIdMap.containsKey(channelId)) {
						System.out.println("channel id referenced without existing in roi id = " + roi.getId());
						return false;
					}
				}
			}
			if (roi.getAssociatedImage() != null) {
				if (!imageIdMap.containsKey(roi.getAssociatedImage())) {
					System.out.println("image id referenced without existing in roi id = " + roi.getId());
					return false;
				}
			}
		}
		return true;
	}


	private Doc convertXmlToObjects(String xmlFullPathLocation) {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Doc.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Doc doc = (Doc) jaxbUnmarshaller.unmarshal(classloader.getResourceAsStream(xmlFullPathLocation));
			return doc;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
