package com.example.moviesjavaandroidurbanadarshverma1236;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.moviesjavaandroidurbanadarshverma1236.data.FavoriteContract;
import com.example.moviesjavaandroidurbanadarshverma1236.data.FavoriteDbHelper;
import com.example.moviesjavaandroidurbanadarshverma1236.model.Movies;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


public class DetailActivity extends AppCompatActivity {
    TextView Aname, Apopularity, Avote, Aadult, Aoriginallanguage, Avoteaverage, Aoverview, AreleaseDate;
    ImageView imageView;

    private FavoriteDbHelper favoriteDbHelper;
    private final AppCompatActivity activity = DetailActivity.this;
    private SQLiteDatabase mDb;

    Movies mMovies;
    String thumbnail, Bname, Bpopularity, Bvote, Badult, Boriginallanguage, Bvoteaverage, Boverview, BreleaseDate;
    int movie_id;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        MaterialFavoriteButton materialFavoriteButton = findViewById(R.id.favorite_button);

        if (Exists(Bname)) {
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                favoriteDbHelper = new FavoriteDbHelper(DetailActivity.this);
                                favoriteDbHelper.deleteFavorite(movie_id);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                int movie_id = Objects.requireNonNull(getIntent().getExtras()).getInt("id");
                                favoriteDbHelper = new FavoriteDbHelper(DetailActivity.this);
                                favoriteDbHelper.deleteFavorite(movie_id);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }
    }

    public boolean Exists(String searchItem) {

        String[] projection = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_Popularity,
                FavoriteContract.FavoriteEntry.COLUMN_Vote_Count,
                FavoriteContract.FavoriteEntry.COLUMN_Adult,
                FavoriteContract.FavoriteEntry.COLUMN_Original_Language,
                FavoriteContract.FavoriteEntry.COLUMN_Vote_Average,
                FavoriteContract.FavoriteEntry.COLUMN_Overview,
                FavoriteContract.FavoriteEntry.COLUMN_Release_Date,
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH

        };
        String selection = FavoriteContract.FavoriteEntry.COLUMN_TITLE + " =?";
        String[] selectionArgs = {searchItem};
        String limit = "1";

        Cursor cursor = mDb.query(FavoriteContract.FavoriteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public void saveFavorite() {
        favoriteDbHelper = new FavoriteDbHelper(activity);
        Movies favorite = new Movies();

        Double rate = mMovies.getVoteAverage();

        favorite.setId(movie_id);
        favorite.setOriginalTitle(Bname);
        favorite.setPosterPath(thumbnail);
        favorite.setPopularity(Double.valueOf(Bpopularity));
        favorite.setVoteCount(Double.valueOf(Bvote));
        favorite.setAdult(Boolean.parseBoolean(Badult));
        favorite.setOriginalLanguage(Boriginallanguage);
        favorite.setVoteAverage(rate);
        favorite.setOverview(Boverview);
        favorite.setReleaseDate(BreleaseDate);

        favoriteDbHelper.addFavorite(favorite);
    }
}