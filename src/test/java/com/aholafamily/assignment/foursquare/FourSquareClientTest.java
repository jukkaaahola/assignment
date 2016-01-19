package com.aholafamily.assignment.foursquare;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jukkaahola on 1/19/16.
 */
public class FourSquareClientTest {
    FourSquareClient client = null;

    @Before
    public void setUp() throws Exception {
        client = new FourSquareClient();
    }

    @After
    public void tearDown() throws Exception {
        client = null;
    }

    @Test
    public void testSearchVenuesByName() throws Exception {
        assertTrue("Finds venues from Oulu", client.searchVenuesByName("Oulu, Finland").size()>0);
    }

    @Test
    public void testSearchVenuesByCoordinates() throws Exception {
        assertTrue("Finds venues from GPS coordinates", client.searchVenuesByCoordinates("65.08,25.45").size()>0);
    }

    @Test
    public void testGetVenuePhotoUrls() throws Exception {
        assertTrue("Finds photos from a venue that is known to have photos", client.getVenuePhotoUrls("5328695b498e19b60161ddf5").size()>0);
    }
}