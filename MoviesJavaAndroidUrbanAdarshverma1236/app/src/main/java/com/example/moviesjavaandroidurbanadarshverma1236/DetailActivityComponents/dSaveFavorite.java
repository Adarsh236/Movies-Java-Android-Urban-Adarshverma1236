package com.example.moviesjavaandroidurbanadarshverma1236.DetailActivityComponents;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.moviesjavaandroidurbanadarshverma1236.model.Movies;

public abstract class dSaveFavorite extends dCreate {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void InnerClassOfsaveFavorite() {

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
