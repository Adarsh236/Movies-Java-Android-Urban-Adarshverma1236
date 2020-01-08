package com.example.moviesjavaandroidurbanadarshverma1236.MainActivityComponents;

import android.util.Log;

import com.example.moviesjavaandroidurbanadarshverma1236.model.Movies;

import java.util.ArrayList;

public abstract class maFilter extends maGetAllFavorite {

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(LOG_TAG, "NEWTEXT: " + newText);
        newText = newText.toLowerCase();
        ArrayList<Movies> newList = new ArrayList<>();
        int i = 0;
        Log.d(LOG_TAG, "SIZE: " + mMoviesList.size());
        for (Movies item : mMoviesList) {
            String name = item.getOriginalTitle().toLowerCase();
            Log.d(LOG_TAG, "NAME: " + name);
            if (name.startsWith(newText)) {
                newList.add(item);
                Log.d(LOG_TAG, "Sorting by favorite" + newList.get(i).getOriginalTitle());
                i++;
            }
        }
        Log.d(LOG_TAG, "ITEM sz: " + newList.size());
        Log.d(LOG_TAG, "ITEM sz: " + newList);
        // Toast.makeText(getApplicationContext(), newText + "show : " + newList, Toast.LENGTH_SHORT).show();
        //newList.forEach(T -> System.out.print(T + " ") );
        mMoviesAdapter.setFilter(newList);
        recyclerView.setAdapter(mMoviesAdapter);
        recyclerView.smoothScrollToPosition(0);
        Log.d(LOG_TAG, "Changed " + mMoviesAdapter);
        return true;
    }
}
