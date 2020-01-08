package com.example.moviesjavaandroidurbanadarshverma1236.MainActivityComponents;

import android.content.res.Configuration;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.moviesjavaandroidurbanadarshverma1236.R;
import com.example.moviesjavaandroidurbanadarshverma1236.adapter.MoviesAdapter;

import java.util.ArrayList;

public abstract class maIntitialView extends maFilter {

    protected void InnerClassOfinitialViews() {
        recyclerView = findViewById(R.id.recycler_view);
        mMoviesList = new ArrayList<>();
        mMoviesAdapter = new MoviesAdapter(this, mMoviesList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mMoviesAdapter);
        mMoviesAdapter.notifyDataSetChanged();
    }
}
