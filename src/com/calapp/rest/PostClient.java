package com.calapp.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class PostClient {

	public static void main(String[] args) {

	    try {

	        Client client = Client.create();

	        WebResource webResource = client.resource("http://localhost:8080/Cal_App/calAPI/calservice/add");

	        String input = "{\"entryId\":0,\"fromFullTime\":1484781423720,\"toFullTime\":1484785023720,\"subject\":\"new entry subject\",\"comments\":\"new entry comment\"}";

	        ClientResponse response = webResource.type("application/json")
	           .post(ClientResponse.class, input);

	        if (response.getStatus() != 201) {
	            throw new RuntimeException("Failed : HTTP error code : "
	                 + response.getStatus());
	        }

	        System.out.println("Output from Server .... \n");
	        String output = response.getEntity(String.class);
	        System.out.println(output);

	      } catch (Exception e) {

	        e.printStackTrace();

	      }

	    }
	
}
