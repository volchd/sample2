package com.emc.iig.analytics.utility;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

@Service
public class JSONUtility {
	private ObjectMapper mapper = new ObjectMapper();
	
	public Map<String, String> getMap(String json) throws JsonParseException, JsonMappingException, IOException
	{
		Map<String, String> result =mapper.readValue(json, new TypeReference<Map<String, String>>() { });

		return result;
	}
	public String getJSON(Map<String, String> map) throws JsonGenerationException, JsonMappingException, IOException
	{

		return mapper.writeValueAsString(map);
	}
	public ObjectMapper getMapper() {
		return mapper;
	}
	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}
}
