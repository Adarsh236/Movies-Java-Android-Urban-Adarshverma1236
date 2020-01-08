package com.example.moviesjavaandroidurbanadarshverma1236.DetailActivityComponents;

import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviesjavaandroidurbanadarshverma1236.data.FavoriteDbHelper;
import com.example.moviesjavaandroidurbanadarshverma1236.model.Movies;

public abstract class dVariables extends AppCompatActivity {
    TextView Aname, Apopularity, Avote, Aadult, Aoriginallanguage, Avoteaverage, Aoverview, AreleaseDate;
    ImageView imageView;

    protected FavoriteDbHelper favoriteDbHelper;
    protected SQLiteDatabase mDb;

    Movies mMovies;
    String thumbnail;
    protected String Bname;
    String Bpopularity;
    String Bvote;
    String Badult;
    String Boriginallanguage;
    String Bvoteaverage;
    String Boverview;
    String BreleaseDate;
    protected int movie_id;
}
