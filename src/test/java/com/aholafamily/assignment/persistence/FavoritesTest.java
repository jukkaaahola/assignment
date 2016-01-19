package com.aholafamily.assignment.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jukkaahola on 1/17/16.
 */
public class FavoritesTest {
    Favorites favorites = null;

    @Before
    public void initTests(){
        favorites = Favorites.getInstance();
        favorites.eraseAllData();
        favorites.addFavorite("1", "V1", "Mainstreet 100", "http://url", "+19529471161", "a,b,c");
        favorites.addFavorite("2", "V2", "Mainstreet 200", "http://url2", "+19529471162", "d,e,f");

    }

    @After
    public void cleanup(){
        favorites.eraseAllData();
    }


    @Test
    public void testGetAll() throws Exception {
        assertEquals("Favorites has two elements", 2, favorites.getAll().size());
    }

    @Test
    public void testDeleteFavorite() throws Exception {
        assertTrue("Delete one of the favorites:", favorites.deleteFavorite("2"));
        assertEquals("Favorites has one elements", 1, favorites.getAll().size());

    }

    @Test
    public void testUpdateFavorite() throws Exception {
        assertTrue("Update the remaining favorite:", favorites.updateFavorite("1", "g,h,i"));
    }

    @Test
    public void testAddFavorite() throws Exception {
        assertTrue("Add a new favorite", favorites.addFavorite("3", "V3", "Mainstreet 300", "http://url3", "+19529471161", "j,k,l"));
        assertEquals("Favorites has three elements", 3, favorites.getAll().size());

    }

}