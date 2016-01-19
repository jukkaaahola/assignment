package com.aholafamily.assignment.persistence;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Favorites is a singleton class to handle persistent storage.
 * It utilizes XStream for object serialization and deserialization to XML.
 * The public methods are strongly synchronized in order to avoid conflicts with
 * parallel transactions.
 *
 * Created by jukkaahola on 1/17/16.
 */
public class Favorites {
    private static Favorites favorites = null;
    private static File persistenceFile = null;

    private Favorites(){
        super();
        persistenceFile = new File("favorites.xml");
    }

    public static Favorites getInstance(){
        if(favorites == null){
            favorites = new Favorites();
        }
        if(!favorites.persistenceFile.exists()){
            favorites.store(new ArrayList<Venue>());
        }

        return favorites;
    }

    private ArrayList<Venue> load(){
        ArrayList<Venue> favoriteList = null;
        try {
            XStream xstream = new XStream(new DomDriver());
            favoriteList = (ArrayList<Venue>)xstream.fromXML(new FileInputStream(persistenceFile));
        } catch (Exception exc){
            System.out.println(exc.getMessage());
            favoriteList = null;
        }
        return favoriteList;
    }

    private boolean store(ArrayList<Venue> favs){
        boolean isStoreSuccessful = false;
        try {
            XStream xstream = new XStream(new DomDriver());
            xstream.toXML(favs,new FileWriter(persistenceFile));
            isStoreSuccessful = true;
        } catch (Exception exc){
            System.out.println(exc.getMessage());
            isStoreSuccessful = false;
        }

        return isStoreSuccessful;
    }

    public synchronized void eraseAllData(){
        store(new ArrayList<Venue>());
    }

    public synchronized ArrayList<Venue> getAll(){
        return load();
    }

    public synchronized boolean deleteFavorite(String venueId){
        boolean result = false;
        ArrayList<Venue> favoriteList = load();
        if(favoriteList!= null) {
            for(Venue venue : favoriteList){
                if(venue.venueId.equals(venueId)){
                    favoriteList.remove(venue);
                    result = true;
                    break;
                }
            }
            store(favoriteList);
        }
        return result;
    }

    public synchronized boolean updateFavorite(String venueId, String keywords){
        boolean result = false;
        ArrayList<Venue> favoriteList = load();
        if(favoriteList!= null) {
            for(Venue venue : favoriteList){
                if(venue.venueId.equals(venueId)){
                    venue.keywords = keywords;
                    result = true;
                    break;
                }
            }
            store(favoriteList);
        }
        return result;
    }

    public synchronized boolean addFavorite(String venueId, String name,
                                            String address, String url,
                                            String phone, String keywords){

        boolean result = false;
        ArrayList<Venue> favoriteList = load();
        if(favoriteList!= null) {
            for(Venue venue : favoriteList){
                if(venue.venueId.equals(venueId)){
                    favoriteList.remove(venue);
                    break;
                }
            }

            Venue fav = new Venue();
            fav.venueId = venueId;
            fav.name = name;
            fav.address = address;
            fav.url = url;
            fav.phone = phone;
            fav.keywords = keywords;
            favoriteList.add(fav);
            store(favoriteList);
            result = true;
        }
        return result;
    }

}
