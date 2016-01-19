package com.aholafamily.assignment.rest;

import com.aholafamily.assignment.persistence.Favorites;
import com.aholafamily.assignment.persistence.Venue;
import com.sun.jersey.api.core.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;


import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.ArrayList;

/**
 * FavoriteAPI provides methods to manage venue favorites in a persistent file storage.
 * There are methods to add a favorite venue, update the keywords of a selected favorite venue,
 * delete a selected venue and get all stored favorite venues.
 *
 * For additional security, the method to get all stored favorite venues automatically
 * logs the requesting User-Agent on the server logs for analysis.
 *
 * Created by jukkaahola on 1/15/16.
 */

@Path("favoriteapi")
public class FavoriteAPI {

    @Context
    HttpContext context;

    @GET
    @Path("/favorites/")
    @Produces("application/json")
    public String getFavorites() {
        System.out.println(context.getRequest().getHeaderValue("User-Agent"));
        ArrayList<Venue> favorites = Favorites.getInstance().getAll();
        JSONArray result = new JSONArray();
        for(Venue venue: favorites){
            result.put(venue.toJSONObject());
        }
        return result.toString();
    }


    @POST
    @Path("/favorites/")
    @Produces("application/json")
    public String storeVenueToFavorites(@FormParam("id") String id, @FormParam("name") String name,
                                        @FormParam("address") String address, @FormParam("url") String url,
                                        @FormParam("phone") String phone, @FormParam("keywords") String keywords) {
        boolean result = Favorites.getInstance().addFavorite(id, name, address, url, phone, keywords);
        return new JSONObject().put("result", result).toString();
    }

    @PUT
    @Path("/favorites/{venueid}")
    @Produces("application/json")
    public String updateFavoriteVenue(@PathParam("venueid") String venueid, @FormParam("keywords") String keywords) {
        boolean result = Favorites.getInstance().updateFavorite(venueid, keywords);
        return new JSONObject().put("result", result).toString();
    }

    @DELETE
    @Path("/favorites/{venueid}")
    @Produces("application/json")
    public String deleteFavoriteVenue(@PathParam("venueid") String venueid) {
        boolean result = Favorites.getInstance().deleteFavorite(venueid);
        return new JSONObject().put("result", result).toString();
    }


}
