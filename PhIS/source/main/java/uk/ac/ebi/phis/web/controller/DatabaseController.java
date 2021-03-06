/*******************************************************************************
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 *******************************************************************************/
package uk.ac.ebi.phis.web.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.cypher.ParameterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.ac.ebi.neo4jUtils.Neo4jAccessUtils;
import uk.ac.ebi.phis.exception.BasicPhisException;
import uk.ac.ebi.phis.service.GenericUpdateService;
import uk.ac.ebi.phis.service.ImageService;
import uk.ac.ebi.phis.solrj.dto.RoiDTO;

	@Controller
	@RequestMapping("/rest/submission")
	public class DatabaseController {

		@Autowired
		ImageService is;
		
		@Autowired
		Neo4jAccessUtils neo;
		
		@Autowired 
		GenericUpdateService gus;
		
		/**
		 * 
		 * @param model
		 * @return
		 * @throws SolrServerException
		 * @throws IOException
		 * @throws URISyntaxException
		 */
		@RequestMapping(value="/", method=RequestMethod.GET)	
	    public String showDocumentation( Model model ) throws SolrServerException, IOException, URISyntaxException {
		
			System.out.println("Try /getImages .");
			return "submission_home";
	    }
		
		
		@RequestMapping(value="/createAnnotation", method=RequestMethod.GET)	
	    public @ResponseBody String createAnnotation(
	    		@RequestParam(value = "userId", required = true) String userId,
	            @RequestParam(value = "annotationId", required = true) String annotationId,
	            @RequestParam(value = "associatedImageId", required = true) String associatedImageId,
	            @RequestParam(value = "xCoordinates", required = true) List<Float> xCoordinates,
	            @RequestParam(value = "yCoordinates", required = true) List<Float> yCoordinates,
	            @RequestParam(value = "zCoordinates", required = false) List<Float> zCoordinates,
	    		@RequestParam(value = "associatedChannelId", required = false) List<String> associatedChannelId,
	            @RequestParam(value = "depictedAnatomyId", required = false) List<String> depictedAnatomyId,
	            @RequestParam(value = "depictedAnatomyTerm", required = false) List<String> depictedAnatomyTerm,
	            @RequestParam(value = "depictedAnatomyFreetext", required = false) List<String> depictedAnatomyFreetext,
	            @RequestParam(value = "expressionInAnatomyId", required = false) List<String> expressionInAnatomyId,
	            @RequestParam(value = "expressionInAnatomyTerm", required = false) List<String> expressionInAnatomyTerm,
	            @RequestParam(value = "expressionInAnatomyFreetext", required = false) List<String> expressionInAnatomyFreetext,
	            @RequestParam(value = "abnInAnatomyId", required = false) List<String> abnInAnatomyId,
	            @RequestParam(value = "abnInAnatomyFreetext", required = false) List<String> abnInAnatomyFreetext,
	            @RequestParam(value = "abnInAnatomyTerm", required = false) List<String> abnInAnatomyTerm,
	            @RequestParam(value = "phenotypeId", required = false) List<String> phenotypeId,
	            @RequestParam(value = "phenotypeFreetext", required = false) List<String> phenotypeFreetext,
	            @RequestParam(value = "phenotypeTerm", required = false) List<String> phenotypeTerm,
	            @RequestParam(value = "observation", required = false) List<String> observations,
	    		Model model  ) {
			
			JSONObject succeded = getSuccessJson();
			
			try {
				if (is.imageIdExists(associatedImageId)){
					
					neo.createAnnotation(userId, annotationId, associatedImageId, xCoordinates, yCoordinates, zCoordinates, associatedChannelId,
					depictedAnatomyId, depictedAnatomyFreetext, depictedAnatomyTerm, abnInAnatomyId, abnInAnatomyFreetext, abnInAnatomyTerm,
					phenotypeId, phenotypeFreetext, phenotypeTerm, observations, expressionInAnatomyId, expressionInAnatomyTerm, expressionInAnatomyFreetext);
					
					ArrayList zCoord = (zCoordinates != null ? new ArrayList<Float>(zCoordinates) : null);
					RoiDTO roi = new RoiDTO(annotationId, associatedChannelId, associatedImageId, depictedAnatomyId, depictedAnatomyTerm, 
							depictedAnatomyFreetext, abnInAnatomyId, abnInAnatomyTerm, abnInAnatomyFreetext, 
							phenotypeId, phenotypeTerm, phenotypeFreetext, observations, new ArrayList<Float>(xCoordinates), 
							new ArrayList<Float>(yCoordinates), zCoord, expressionInAnatomyTerm, expressionInAnatomyFreetext, expressionInAnatomyId);
					gus.addToCores(roi);					
				}				
			} catch (BasicPhisException e) {
				e.printStackTrace();
				succeded = getFailJson();
				succeded.put("message", e.getMessage());
			}
			return succeded.toString();
	    }
		
		@RequestMapping(value="/updateAnnotation", method=RequestMethod.GET)	
	    public @ResponseBody String updateAnnotation(
	    		@RequestParam(value = "userId", required = true) String userId,
	            @RequestParam(value = "annotationId", required = true) String annotationId,
	            @RequestParam(value = "associatedImageId", required = true) String associatedImageId,
	            @RequestParam(value = "xCoordinates", required = true) List<Float> xCoordinates,
	            @RequestParam(value = "yCoordinates", required = true) List<Float> yCoordinates,
	            @RequestParam(value = "zCoordinates", required = false) List<Float> zCoordinates,
	    		@RequestParam(value = "associatedChannelId", required = false) List<String> associatedChannelId,
	            @RequestParam(value = "depictedAnatomyId", required = false) List<String> depictedAnatomyId,
	            @RequestParam(value = "depictedAnatomyTerm", required = false) List<String> depictedAnatomyTerm,
	            @RequestParam(value = "depictedAnatomyFreetext", required = false) List<String> depictedAnatomyFreetext,
	            @RequestParam(value = "abnInAnatomyId", required = false) List<String> abnInAnatomyId,
	            @RequestParam(value = "abnInAnatomyTerm", required = false) List<String> abnInAnatomyTerm,
	            @RequestParam(value = "abnInAnatomyFreetext", required = false) List<String> abnInAnatomyFreetext,
	            @RequestParam(value = "expressionInAnatomyId", required = false) List<String> expressionInAnatomyId,
	            @RequestParam(value = "expressionInAnatomyFreetext", required = false) List<String> expressionInAnatomyFreetext,
	            @RequestParam(value = "expressionInAnatomyTerm", required = false) List<String> expressionInAnatomyTerm,
	            @RequestParam(value = "phenotypeId", required = false) List<String> phenotypeId,
	            @RequestParam(value = "phenotypeTerm", required = false) List<String> phenotypeTerm,
	            @RequestParam(value = "phenotypeFreetext", required = false) List<String> phenotypeFreetext,
	            @RequestParam(value = "observation", required = false) List<String> observations,
	    		Model model ) {
			
			JSONObject succeded = getSuccessJson();
			
			try {
				
				if (is.imageIdExists(associatedImageId)){
				
					neo.updateAnnotation(userId, annotationId, associatedImageId, xCoordinates, yCoordinates, zCoordinates, associatedChannelId,
						depictedAnatomyId, depictedAnatomyFreetext, depictedAnatomyTerm, abnInAnatomyId, abnInAnatomyFreetext, abnInAnatomyTerm, phenotypeId, 
						phenotypeFreetext, phenotypeTerm, observations, expressionInAnatomyId, expressionInAnatomyTerm, expressionInAnatomyFreetext);
					
					
						RoiDTO roi = new RoiDTO(annotationId, associatedChannelId, associatedImageId, depictedAnatomyId, depictedAnatomyTerm, 
							depictedAnatomyFreetext, abnInAnatomyId, abnInAnatomyTerm, abnInAnatomyFreetext, 
							phenotypeId, phenotypeTerm, phenotypeFreetext, observations, new ArrayList<Float>(xCoordinates), 
							new ArrayList<Float>(yCoordinates), zCoordinates != null ? new ArrayList<Float>(zCoordinates) : null,
							expressionInAnatomyTerm, expressionInAnatomyFreetext, expressionInAnatomyId);
						gus.addToCores(roi);
						
				}
				
			} catch (BasicPhisException e) {
				e.printStackTrace();
				succeded = getFailJson();
				succeded.put("message", e.getMessage());
			}
			
			return succeded.toString();
	    }
		
		
		
		@RequestMapping(value="/deleteAnnotation", method=RequestMethod.GET)	
	    public @ResponseBody String createAnnotation(
	    		@RequestParam(value = "userId", required = true) String userId,
	            @RequestParam(value = "annotationId", required = true) String anntoationId,
	    		Model model) {
			
			JSONObject obj = getSuccessJson();
			
			try {
				if (neo.hasSameUser(userId, anntoationId)){
					neo.deleteNodeWithRelations(anntoationId);
					gus.deleteFromCores(anntoationId);
				}else{
					throw new BasicPhisException("User provided does not match the user of the annotation. Annotation was not deleted.");
				}
			} catch (BasicPhisException e){
				e.printStackTrace();
				obj = getFailJson();
				obj.put("message", e.getMessage());				
			}
			
			return obj.toString();
	    }
		
		
		
		private JSONObject getSuccessJson(){
			
			JSONObject obj = new JSONObject();
			obj.put("outcome", "SUCCESS");
			
			return obj;
			
		}
		
		
		
		private JSONObject getFailJson(){
			
			JSONObject obj = new JSONObject();
			obj.put("outcome", "FAIL");
			
			return obj;
			
		}
	}



