package com.example.moviesjavaandroidurbanadarshverma1236;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviesjavaandroidurbanadarshverma1236.DetailActivityComponents.dGetAllInnerClasses;
import com.example.moviesjavaandroidurbanadarshverma1236.data.FavoriteDbHelper;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


public class DetailActivity extends dGetAllInnerClasses {
    private final AppCompatActivity activity = DetailActivity.this;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InnerClassOfonCreate();

        MaterialFavoriteButton materialFavoriteButton = findViewById(R.id.favorite_button);
        try {
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
        } catch (Exception e) {
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void saveFavorite() {
        favoriteDbHelper = new FavoriteDbHelper(activity);
        InnerClassOfsaveFavorite();
    }
}