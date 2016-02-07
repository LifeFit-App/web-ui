package com.lifefit.rest.pcs.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;

import org.glassfish.jersey.client.ClientConfig;

import com.lifefit.model.Goal;
import com.lifefit.model.LifeStatus;
import com.lifefit.model.Measure;
import com.lifefit.model.Person;
import com.lifefit.util.Transformer;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;

public class LifeFitClient {

	static InputStream stream = null;
	
	static Response response;
	static String results = null;
	//RESTFul Web Service URL for LifeFit process centric services
	final String SERVER_URL = "https://lifefit-pcs-181499.herokuapp.com/lifefit-pcs";
	WebTarget service;
	
	public LifeFitClient(){
		init();
	}
	
	private void init(){
		final ClientConfig clientConfig = new ClientConfig().register(GensonJsonConverter.class);			
		Client client = ClientBuilder.newClient(clientConfig);
		service = client.target(getBaseURI(SERVER_URL));		
	}
	
	private static URI getBaseURI(String SERVER_URL){
		return UriBuilder.fromUri(SERVER_URL).build();
	}
	
	public Person readPerson(int personId){
		Person person = new Person();
		
		try{
			response = service.path("person/"+personId).request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);			
			//Convert string into inputStream
			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
			
			Transformer transform = new Transformer();
			person = transform.unmarshallJSONPerson(stream);
								
		}
		catch(Exception e){e.printStackTrace();}
		return person;
	}
	
	public List<Measure> getMeasureTypes(){
		List<Measure> measureTypes = new ArrayList<Measure>();
		
		try{
			response = service.path("measureTypes").request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);			
			//Convert string into inputStream
			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
			
			Transformer transform = new Transformer();
			Measure[] measureArray = transform.unmarshallJSONMeasureTypes(stream);	
			for(int i=0; i<measureArray.length; i++){
				measureTypes.add(measureArray[i]);
			}
		}
		catch(Exception e){e.printStackTrace();}
		return measureTypes;
	}
	
	public List<Measure> getMeasureTypesGoal(){
		List<Measure> measureTypes = new ArrayList<Measure>();
		
		try{
			response = service.path("measureTypes/goal/list").request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);			
			//Convert string into inputStream
			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
			
			Transformer transform = new Transformer();
			Measure[] measureArray = transform.unmarshallJSONMeasureTypes(stream);	
			for(int i=0; i<measureArray.length; i++){
				measureTypes.add(measureArray[i]);
			}
		}
		catch(Exception e){e.printStackTrace();}
		return measureTypes;
	}
	
	public Person authenticateUser(String email, String pass){		
		Person person = null;
		
		try{
			//Authenticate user by invoking process centric services
			response = service.path("person/"+email+"/"+pass).request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);
			if(results != null){
				//Convert string into inputStream
				stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
				
				Transformer transform = new Transformer();
				person = transform.unmarshallJSONPerson(stream);
			}			
		}
		catch(Exception e){e.printStackTrace();}
		return person;
	}
	
	public LifeStatus savePersonHealthMeasure(LifeStatus lifeStatus, int personId, String measureType){				
		
		try{
			response = service.path("person/"+personId+"/hp/"+measureType).request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(lifeStatus, MediaType.APPLICATION_JSON), Response.class);
			results = response.readEntity(String.class);
			if(results != null){
				//Convert string into inputStream
				stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
				
				Transformer transform = new Transformer();
				lifeStatus = transform.unmarshallJSONLifeStatus(stream);
			}			
		}
		catch(Exception e){e.printStackTrace();}
		return lifeStatus;
	}
	
	public Goal getGoal(int personId){
		Goal goal = new Goal();
		
		try{
			response = service.path("person/"+personId+"/goal").request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);
			
			if(results != null && !results.equalsIgnoreCase("")){
				//Convert string into inputStream
				stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
				
				Transformer transform = new Transformer();
				goal = transform.unmarshallJSONGoal(stream);
			}			
		}
		catch(Exception e){e.printStackTrace();}
		return goal;
	}
	
	public boolean savePersonGoal(Goal goal, int personId, String measureType){
		int httpStatus = 0;
		boolean result = false;
		
		try{
			response = service.path("person/"+personId+"/goal/"+measureType).request(MediaType.APPLICATION_JSON)
					.put(Entity.entity(goal, MediaType.APPLICATION_JSON), Response.class);
			httpStatus = response.getStatus();
			
			if(httpStatus == 200 || httpStatus == 201)
				result = true;
			else
				result = false;	
		}
		catch(Exception e){e.printStackTrace();}
		return result;
	}
	
	public String checkDailyGoalStatus(int personId){
		String dailyGoalStatus = null;
		
		try{
			response = service.path("person/"+personId+"/goal/status").request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);
			dailyGoalStatus = results.toString();		
		}
		catch(Exception e){}
		return dailyGoalStatus;
	}
}