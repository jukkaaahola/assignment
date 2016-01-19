package com.aholafamily.assignment.persistence;

import org.json.JSONObject;

/**
 * Venue is a POJO-object that is used for FourSquare queries and favorite venue persistence.
 *
 * Created by jukkaahola on 1/17/16.
 */
public class Venue {
    protected String venueId = null;
    protected String name = null;
    protected String address = null;
    protected String url = null;
    protected String phone = null;
    protected String keywords = null;

    public Venue(){
        super();
    }

    public String toString(){
        return toJSONObject().toString();
    }

    public JSONObject toJSONObject(){
        JSONObject result = new JSONObject();
        result.put("venueId", venueId);
        result.put("name", name);
        result.put("address", address);
        result.put("url", url);
        result.put("phone", phone);
        result.put("keywords", keywords);
        return result;

    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
