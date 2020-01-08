package com.example.moviesjavaandroidurbanadarshverma1236.data;

import android.provider.BaseColumns;

public class FavoriteContract {

    public static final class FavoriteEntry implements BaseColumns {

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_Popularity = "popularity";
        public static final String COLUMN_Vote_Count = "vote";
        public static final String COLUMN_Adult = "adult";
        public static final String COLUMN_Original_Language = "originallanguage";
        public static final String COLUMN_Vote_Average = "voteaverage";
        public static final String COLUMN_Overview = "overview";
        public static final String COLUMN_Release_Date = "releasedate";
        public static final String COLUMN_POSTER_PATH = "posterpath";
    }
}
