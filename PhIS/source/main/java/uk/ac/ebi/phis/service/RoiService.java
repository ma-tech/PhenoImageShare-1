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
package uk.ac.ebi.phis.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import uk.ac.ebi.phis.solrj.dto.ChannelDTO;
import uk.ac.ebi.phis.solrj.dto.RoiDTO;
import uk.ac.ebi.phis.utils.web.JSONRestUtil;


public class RoiService extends BasicService{

	
	public RoiService(String solrUrl) {
		super(solrUrl);
	}
	
	public String getRoiAsJsonString(String roiId){
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*");
		roiId = handleSpecialCharacters(roiId);
		solrQuery.setFilterQueries(RoiDTO.ID + ":\""+ roiId + "\"");
		solrQuery.set("wt", "json");
		
		System.out.println("------ ROI" + getQueryUrl(solrQuery));

		try {
			return JSONRestUtil.getResults(getQueryUrl(solrQuery)).toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return "Couldn't get anything back from solr.";
	}	


	public RoiDTO getRoiById(String id){
		
		SolrQuery solrQuery = new SolrQuery();
		id = handleSpecialCharacters(id);
		solrQuery.setQuery(RoiDTO.ID + ":\""+ id + "\"");
		try {
			List<RoiDTO> results = solr.query(solrQuery).getBeans(RoiDTO.class);
			if (results.size() > 0){
				return results.get(0);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getRois(String imageId){
		
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*");
		imageId = handleSpecialCharacters(imageId);
		solrQuery.setFilterQueries(RoiDTO.ASSOCIATED_IMAGE_ID + ":\""+ imageId + "\"");
		solrQuery.set("wt", "json");
		
		try {
			return JSONRestUtil.getResults(getQueryUrl(solrQuery)).toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return "Couldn't get anything back from solr.";
	}	
	
	public void updateRoi(RoiDTO roi){
		List<RoiDTO> list = new ArrayList<>();
		list.add(roi);
		addBeans(list);
	}
	
	public void addRoi(RoiDTO roi){
		List<RoiDTO> list = new ArrayList<>();
		list.add(roi);
		addBeans(list);
	}
	
	public void addBeans(List<RoiDTO> docs){
		try {
			solr.addBeans(docs);
			solr.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRoi(String roiId){
		
		roiId = handleSpecialCharacters(roiId);
		try {
			solr.deleteByQuery(RoiDTO.ID + ":" + roiId);
			solr.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes all documents from the core
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public void clear() throws SolrServerException, IOException{
		solr.deleteByQuery("*:*");
		solr.commit();
	}
	

	public String getQueryUrl(SolrQuery q){
		return solr.getBaseURL() + "/select?" + q.toString();
	}
	
}
