package com.example.moviesjavaandroidurbanadarshverma1236.MainActivityComponents;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public abstract class maGetAllFavorite extends maMenu {
    @SuppressLint("StaticFieldLeak")
    protected void getAllFavorite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                mMoviesList.clear();
                mMoviesList.addAll(favoriteDbHelper.getAllFavorite());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mMoviesAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
