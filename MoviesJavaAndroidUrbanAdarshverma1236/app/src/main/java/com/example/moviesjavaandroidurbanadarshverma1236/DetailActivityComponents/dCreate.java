package com.example.moviesjavaandroidurbanadarshverma1236.DetailActivityComponents;

import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.moviesjavaandroidurbanadarshverma1236.R;
import com.example.moviesjavaandroidurbanadarshverma1236.data.FavoriteDbHelper;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;

public abstract class dCreate extends dVariables {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void InnerClassOfonCreate() {
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //TODO
        FavoriteDbHelper dbHelper = new FavoriteDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        imageView = findViewById(R.id.thumbnail_image_header);
        Aname = findViewById(R.id.title);
        Apopularity = findViewById(R.id.popularity);
        Avote = findViewById(R.id.vote);
        Aadult = findViewById(R.id.adult);
        Aoriginallanguage = findViewById(R.id.originallanguage);
        Avoteaverage = findViewById(R.id.voteaverage);
        Aoverview = findViewById(R.id.overview);
        AreleaseDate = findViewById(R.id.releasedate);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("movies")) {

            mMovies = getIntent().getParcelableExtra("movies");

            assert mMovies != null;
            thumbnail = mMovies.getPosterPath();
            Bname = mMovies.getOriginalTitle();
            Bpopularity = String.valueOf(mMovies.getPopularity());
            Bvote = Double.toString(mMovies.getVoteCount());
            Badult = String.valueOf(mMovies.isAdult());
            Boriginallanguage = mMovies.getOriginalLanguage();
            Bvoteaverage = Double.toString(mMovies.getVoteAverage());
            Boverview = mMovies.getOverview();
            BreleaseDate = mMovies.getReleaseDate();
            movie_id = mMovies.getId();

            String poster = "https://image.tmdb.org/t/p/w500" + thumbnail;
            Glide.with(this)
                    .load(poster)
                    .placeholder(R.drawable.load)
                    .into(imageView);

            Aname.setText(Bname);
            Apopularity.setText(Bpopularity);
            Avote.setText(Bvote);
            Aadult.setText(Badult);
            Aoriginallanguage.setText(Boriginallanguage);
            Avoteaverage.setText(Bvoteaverage);
            Aoverview.setText(Boverview);
            AreleaseDate.setText(BreleaseDate);
            //TODO
            ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar)).setTitle(Bname);

        } else {
            Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();
        }

    }
}
