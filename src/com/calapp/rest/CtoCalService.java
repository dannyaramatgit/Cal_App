package com.calapp.rest;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.calapp.entities.CalenderEntry;
import com.calapp.manager.EntryManager;
import com.calapp.manager.Utilities;

@Path("/calservice")
public class CtoCalService {

	
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/add")
    public Response putMessage(CalenderEntry entry) throws Exception{
        
        try {
			CalenderEntry saved = EntryManager.getInstance().createEntry(entry);
			JSONObject jsonObject = new JSONObject(saved);
			String result = "@Produces(\"application/json\") Output: \n\n" + jsonObject;
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			return Response.status(400).entity("Failed to create new entry.").build();
		}
    }
    
    @POST
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public Response addEntry(@MatrixParam("from") String from,
            @MatrixParam("to") String to,
            @MatrixParam("subject") String subject,
            @MatrixParam("comments") String comments){
    	
    	
		if(!Utilities.validateDate(from) || !Utilities.validateDate(to)) {
			return Response.status(400).entity("Failed to process entry times. \nPlease check format yyyy-MM-dd'T'HH:mm").build();
		}
    	
    	try {
			CalenderEntry saved = EntryManager.getInstance().createEntry(from, to, subject, comments);
			JSONObject jsonObject = new JSONObject(saved);
			String result = jsonObject.toString();
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			return Response.status(400).entity("Failed to create new entry.").build();
		}
     
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/edit")
    public Response editEntry(CalenderEntry entry) throws Exception{
        
        try {
			CalenderEntry saved = EntryManager.getInstance().editEntry(entry);
			JSONObject jsonObject = new JSONObject(saved);
			String result = jsonObject.toString();
			return Response.status(200).entity("Entry edited. " + result).build();
		} catch (Exception e) {
			return Response.status(400).entity("Failed to edit entry " + entry.getEntryId()).build();
		}    
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_HTML)
    @Path("/editparam")
    public Response editParametersMessage(@MatrixParam("entryId") long entryId,
    		@MatrixParam("from") String from,
                @MatrixParam("to") String to,
                @MatrixParam("subject") String subject,
                @MatrixParam("comments") String comments)  throws Exception {
        
    	
    	if(!Utilities.validateDate(from) || !Utilities.validateDate(to) || !Utilities.validatedates(from, to)) {
			return Response.status(400).entity("Failed to process entry times. \nPlease check format yyyy-MM-dd'T'HH:mm").build();
		}
    	
        try {
			CalenderEntry entry = EntryManager.getInstance().editEntry(entryId, from, to, subject, comments);
			JSONObject jsonObject = new JSONObject(entry);
			String result = "@Produces(\"application/json\") Output: \n\n" + jsonObject;
			return Response.status(200).entity("Entry edited. " + result).build();
		} catch (Exception e) {
			return Response.status(400).entity("Failed to edit entry " + entryId).build();
		}
    }



	  @Path("/list")
	  @POST
	  @Produces("application/json")
	  public Response getEntriesByDates(@MatrixParam("from") String from,
              @MatrixParam("to") String to) throws JSONException {
		try {
			List<CalenderEntry> entries = EntryManager.getInstance().getEntriesByDates(from,to); 

			JSONArray jsonArray = new JSONArray();
			for(CalenderEntry entry: entries){
				jsonArray.put(new JSONObject(entry));
			}
			JSONObject responseDetailsJson = new JSONObject(); 
			responseDetailsJson.put("entries", jsonArray);
			String result = "results: \n\n" + responseDetailsJson;
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			return Response.status(400).entity("Failed to load entry list.").build();
		}
	  }
	  
	  @Path("/search")
	  @POST
	  @Produces("application/json")
	  public Response getEntriesBySubject(@MatrixParam("searchString") String searchString,
              @MatrixParam("to") String to) throws JSONException {
		try {
			List<CalenderEntry> entries = EntryManager.getInstance().getEntriesByText(searchString); 

			JSONArray jsonArray = new JSONArray();
			for(CalenderEntry entry: entries){
				jsonArray.put(new JSONObject(entry));
			}
			JSONObject responseDetailsJson = new JSONObject(); 
			responseDetailsJson.put("entries", jsonArray);
			String result = "results: \n\n" + responseDetailsJson;
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			return Response.status(400).entity("Failed to load entry list for string:" + searchString).build();	   
		}
	  }
	  
	  
	  @Path("{id}")
	  @GET
	  @Produces("application/json")
	  public Response getEntry(@PathParam("id") long id) throws JSONException {
		CalenderEntry entry = EntryManager.getInstance().getEntry(id); 
		String entryResult = null;
		if (entry != null) {
			entryResult = entry.toString();
			JSONObject jsonObject = new JSONObject(entryResult);

			String result = jsonObject.toString();
			return Response.status(200).entity(result).build();
		}
		return Response.status(200).entity("No entry found.").build();
	  }
	  
	  
	  
	  @DELETE
	  @Path("/delete/{entryId}")
	  @Produces(MediaType.TEXT_HTML)
	   public Response deleteEntry(@PathParam("entryId") int entryId){
		  boolean result = EntryManager.getInstance().deleteEntry(entryId);
	      
	      if(result){
	    	  return Response.status(200).entity("Entry deleted. " + result).build();
	      }
	      
	      return Response.status(400).entity("Failed to delete entry. " + entryId).build();	   }
}
