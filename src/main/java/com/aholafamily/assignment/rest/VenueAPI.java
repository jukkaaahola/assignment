package com.aholafamily.assignment.rest;

import com.aholafamily.assignment.foursquare.FourSquareClient;
import com.aholafamily.assignment.persistence.Venue;
import org.json.JSONArray;

import javax.ws.rs.*;

/**
 * VenueAPI contains REST API methods to retrieve venues and photos for a given venue.
 * The venues can be retrieved by giving a name of the location or by providing the
 * GPS coordinates, such as "65.08,25.45". The response contains JSON-formatted
 * common attributes about the venue, including venueId, name, address, url, and phone.
 * The photo search response is a JSON-formatted array of absolute URLs to the images.
 *
 *
 * Created by jukkaahola on 1/15/16.
 */

@Path("venueapi")
public class VenueAPI {

    FourSquareClient client = null;

    public VenueAPI() throws Exception {
        super();
        client = new FourSquareClient();
    }

    @GET
    @Path("/venuesbyname/{name}")
    @Produces("application/json")
    public String getVenuesByLocationName(@PathParam("name") String name) {
        JSONArray result = new JSONArray();
        for(Venue venue: client.searchVenuesByName(name)){
            result.put(venue.toJSONObject());
        }
        return result.toString();
    }

    @GET
    @Path("/venuesbycoordinates/{coordinates}")
    @Produces("application/json")
    public String getVenuesByLocationCoordinates(@PathParam("coordinates") String coordinates) {
        JSONArray result = new JSONArray();
        for(Venue venue: client.searchVenuesByCoordinates(coordinates)){
            result.put(venue.toJSONObject());
        }
        return result.toString();
    }


    @GET
    @Path("/photos/{venueid}")
    @Produces("application/json")
    public String getPhotosFromVenue(@PathParam("venueid") String venueId) {
        JSONArray result = new JSONArray();
        for(String photoUri: client.getVenuePhotoUrls(venueId)){
            result.put(photoUri);
        }
        return result.toString();
    }


}
