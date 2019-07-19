package com.premar.muvi.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MovieTest {

    @Test
    public void getTitle() {
        final String mTitle = "Alita: Battle Angel";

        Movie newMovie = new Movie();
        newMovie.setTitle(mTitle);

        assertEquals(newMovie.getTitle(), mTitle);
    }
}