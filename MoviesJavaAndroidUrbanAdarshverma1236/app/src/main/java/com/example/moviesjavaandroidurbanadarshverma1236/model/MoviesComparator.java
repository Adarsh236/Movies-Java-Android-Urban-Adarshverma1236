package com.example.moviesjavaandroidurbanadarshverma1236.model;

import java.util.Comparator;

public class MoviesComparator extends Movies {

    // comparator for name
    public static final Comparator<Movies> BY_NAME_ALPHABETICAL = new Comparator<Movies>() {
        @Override
        public int compare(Movies movies, Movies t1) {

            return movies.getOriginalTitle().compareTo(t1.getOriginalTitle());
        }
    };
}