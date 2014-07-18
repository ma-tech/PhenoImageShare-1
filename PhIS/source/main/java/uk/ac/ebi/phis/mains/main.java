package uk.ac.ebi.phis.mains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import uk.ac.ebi.phis.importer.BatchXmlUploader;
import uk.ac.ebi.phis.service.ChannelService;
import uk.ac.ebi.phis.service.ImageService;
import uk.ac.ebi.phis.service.RoiService;
import uk.ac.ebi.phis.xmlDump.TracerXmlGenerator;

@Component
public class main {

	public static void main(String[] args){

		// OntologyMapper mapper = new
		// OntologyMapper(OntologyMapperPredefinedTypes.MA_MP);
		// mapper.getMappings("http://purl.obolibrary.org/obo/MP_0003684",
		// "MA");
		// System.out.println("\t\t " + mapper.getAnatomyLabel("MA_0000003"));

	    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("app-config.xml");
		
		try {
			long time = System.currentTimeMillis();
//			SangerXmlGenerator sg = new SangerXmlGenerator();
//			sg.exportImages();
			System.out.println("Generating xml for Sanger took " + (System.currentTimeMillis() - time));

			time = System.currentTimeMillis();
			TracerXmlGenerator tg = new TracerXmlGenerator();
			tg.exportImages();
			System.out.println("Generating XML for Tracer took " + (System.currentTimeMillis() - time));

			ImageService is = (ImageService) applicationContext.getBean("imageService");
			RoiService rs = (RoiService) applicationContext.getBean("roiService");
			ChannelService cs = (ChannelService) applicationContext.getBean("channelService"); 
			BatchXmlUploader reader = new BatchXmlUploader(is, rs, cs);

			time = System.currentTimeMillis();
			System.out.println(reader.validateAndUpload("tracerExport.xml"));
			System.out.println("Validating Tracer XML took " + (System.currentTimeMillis() - time));

			time = System.currentTimeMillis();
		//	System.out.println("Is valid? " + reader.validateAndUpload("sangerExport.xml"));
			System.out.println("Validating Sanger XML took " + (System.currentTimeMillis() - time));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
