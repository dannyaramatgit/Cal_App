package com.calapp.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

import com.calapp.entities.CalenderEntry;
import com.calapp.manager.EntryManager;

@Path("/calservice")
public class CtoCalService {

	
	
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/add")
    public Response postMessage(CalenderEntry entry) throws Exception{
        
        CalenderEntry saved = EntryManager.getInstance().createEntry(entry);
        JSONObject jsonObject = new JSONObject(saved);
        String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
    }



	  @Path("{id}")
	  @GET
	  @Produces("application/json")
	  public Response getEntry(@PathParam("id") long id) throws JSONException {
		CalenderEntry entry = EntryManager.getInstance().getEntry(id); 
		String entryResult = null;
		if (entry != null) {
			entryResult = entry.toString();
		}
		JSONObject jsonObject = new JSONObject(entryResult);

		String result = "@Produces(\"application/json\") Output: \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	  }
}
