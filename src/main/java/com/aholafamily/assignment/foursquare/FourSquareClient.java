package com.aholafamily.assignment.foursquare;

import com.aholafamily.assignment.persistence.Venue;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * FourSquareClient is a REST-JSON client for public FourSquare venue services.
 * It can handle venue searches based on location and coordinates and photo searches for a given
 * venue Id. The FourSquare clientId and clientSecret are stored in a separate properties file
 * and loaded into a resource bundle during the runtime for security and configuration management.
 * This client also maps the FourSquare JSON-data structures to support POJO-data model in this application.
 *
 * Created by jukkaahola on 1/17/16.
 */
public class FourSquareClient {

    private String clientId = "[GET_YOUR_OWN_CLIENT_ID]";
    private String clientSecret = "[GET_YOUR_OWN_CLIENT_SECRET]";

    public FourSquareClient() throws Exception {
        super();
        ResourceBundle rb = new PropertyResourceBundle(new FileInputStream("foursquare.properties"));
        clientId = rb.getString("client_id");
        clientSecret = rb.getString("client_secret");

    }

    public ArrayList<Venue> searchVenuesByName(String name){
        ArrayList<Venue> result = new ArrayList<Venue>();
        Client client = new Client();
        WebResource webResource = client.resource(getVenueUriByLocation(name));
        ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
        if (clientResponse.getStatus() == 200){
            JSONObject jo = new JSONObject(clientResponse.getEntity(String.class));
            JSONObject response = jo.getJSONObject("response");
            JSONArray venues = response.getJSONArray("venues");
            for(int i=0;i<venues.length();i++){
                JSONObject jsonVenue = venues.getJSONObject(i);
                Venue venue = new Venue();
                venue.setVenueId(getJsonAttribute(jsonVenue, "id"));
                venue.setName(getJsonAttribute(jsonVenue, "name"));
                venue.setAddress(getJsonAttribute(jsonVenue, "location","address"));
                venue.setPhone(getJsonAttribute(jsonVenue, "contact", "phone"));
                venue.setUrl(getJsonAttribute(jsonVenue, "url"));
                venue.setKeywords("");
                result.add(venue);

            }

        }
        return result;
    }

    public ArrayList<Venue> searchVenuesByCoordinates(String coordinates){
        ArrayList<Venue> result = new ArrayList<Venue>();

        Client client = new Client();
        WebResource webResource = client.resource(getVenueUriByCoordinates(coordinates));
        ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
        if (clientResponse.getStatus() == 200){
            JSONObject jo = new JSONObject(clientResponse.getEntity(String.class));
            JSONObject response = jo.getJSONObject("response");
            JSONArray venues = response.getJSONArray("venues");
            for(int i=0;i<venues.length();i++){
                JSONObject jsonVenue = venues.getJSONObject(i);
                Venue venue = new Venue();
                venue.setVenueId(getJsonAttribute(jsonVenue, "id"));
                venue.setName(getJsonAttribute(jsonVenue, "name"));
                venue.setAddress(getJsonAttribute(jsonVenue, "location","address"));
                venue.setPhone(getJsonAttribute(jsonVenue, "contact", "phone"));
                venue.setUrl(getJsonAttribute(jsonVenue, "url"));
                venue.setKeywords("");
                result.add(venue);

            }

        }
        return result;
    }


    public ArrayList<String> getVenuePhotoUrls(String venueId){
        ArrayList<String> result = new ArrayList<String>();

        Client client = new Client();
        WebResource webResource = client.resource(getPhotoUri(venueId));
        ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
        if (clientResponse.getStatus() == 200){
            JSONObject jo = new JSONObject(clientResponse.getEntity(String.class));
            JSONObject response = jo.getJSONObject("response");
            JSONObject photos = response.getJSONObject("photos");
            JSONArray arrPhotos = photos.getJSONArray("items");
            for(int i=0;i<arrPhotos.length();i++){
                JSONObject jsonPhoto = arrPhotos.getJSONObject(i);
                String photoUri = jsonPhoto.getString("prefix") +"original"+jsonPhoto.getString("suffix");
                result.add(photoUri);

            }

        }
        return result;

    }

    private String getJsonAttribute(JSONObject jsonObj, String key){

        String result = "";
        if(jsonObj.has(key)){
            result = jsonObj.getString(key);
        }
        if(result == null){
            result ="";
        }
        return result;
    }

    private String getJsonAttribute(JSONObject jsonObj, String key1, String key2){
        JSONObject jo = jsonObj.getJSONObject(key1);
        if(jo!=null){
            return getJsonAttribute(jo, key2);
        } else {
            return "";
        }
    }

    private String getPhotoUri(String venueId){
        return "https://api.foursquare.com/v2/venues/"+venueId+"/photos?client_id="+clientId+"&client_secret="+clientSecret+"&v=20160101";
    }

    private String getVenueUriByLocation(String location){
        try {
            location = URLEncoder.encode(location, "UTF-8");
        } catch (Exception exc){
            ;
        }
        return "https://api.foursquare.com/v2/venues/search?client_id="+clientId+"&client_secret="+clientSecret+"&v=20160101&near="+location;
    }

    private String getVenueUriByCoordinates(String coordinates){
        try {
            coordinates = URLEncoder.encode(coordinates, "UTF-8");
        } catch (Exception exc){
            ;
        }
        return "https://api.foursquare.com/v2/venues/search?client_id="+clientId+"&client_secret="+clientSecret+"&v=20160101&ll="+coordinates;
    }

}
