package com.app.batch.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.app.batch.model.PartnerJson;
import com.app.batch.model.PartnerUrl;
import com.app.batch.model.UberEats;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@StepScope
public class UrlReader implements ItemReader {
	@Value("#{stepExecutionContext[urlList]}")
	List<String> urlList;

	private int nextPartnerUrlIndex;

	private RestTemplate restTemplate;
	private ArrayList<Object> arrayList = new ArrayList<>();
	List<PartnerUrl> apiListFromFile = new ArrayList<>();

	private HashSet<String> hashSet = new HashSet<>();

	public UrlReader(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * This method is used to read the Response from the url
	 */
	@Override
	public Object read() throws JSONException {
		if (arrayList.isEmpty()) {
			arrayList = fetchDataFromApi(urlList);
		}

		Object nextPartnerUrl = null;
		PartnerJson partnerJson = new PartnerJson();

		if (nextPartnerUrlIndex < arrayList.size()) {
			nextPartnerUrl = arrayList.get(nextPartnerUrlIndex);
			ObjectMapper obj = new ObjectMapper();
			try {
				JSONObject myObject = new JSONObject(obj.writeValueAsString(nextPartnerUrl));
				String partnerName = myObject.getString("partnerName");
				partnerJson.setPartnerName(partnerName);

				partnerJson.setResponse(obj.writeValueAsString(nextPartnerUrl));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			partnerJson.setDateTime(new Timestamp(System.currentTimeMillis()));
			nextPartnerUrlIndex++;
		} else {
			arrayList = new ArrayList<>();
			nextPartnerUrlIndex = 0;
			partnerJson = null;
		}
		return partnerJson;
	}

	/**
	 * This method is used to get response from the url
	 * 
	 * @param urlList
	 * @return
	 */
	private ArrayList<Object> fetchDataFromApi(List<String> urlList) {
		for (String url : urlList) {
			ResponseEntity<UberEats> response = restTemplate.getForEntity(url, UberEats.class);
			if (hashSet.contains(url)) {
				response.getBody().setUpdated(true);
			}
			response.getBody().setUrlInvoked(url);
			hashSet.add(url);
			arrayList.add(response.getBody());
		}

		return arrayList;
	}
}
