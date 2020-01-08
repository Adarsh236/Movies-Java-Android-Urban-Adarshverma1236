package com.example.moviesjavaandroidurbanadarshverma1236.MainActivityComponents;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moviesjavaandroidurbanadarshverma1236.adapter.MoviesAdapter;
import com.example.moviesjavaandroidurbanadarshverma1236.data.FavoriteDbHelper;
import com.example.moviesjavaandroidurbanadarshverma1236.model.Movies;

import java.util.List;

public abstract class maVariables extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, SearchView.OnQueryTextListener {

    protected RecyclerView recyclerView;
    protected MoviesAdapter mMoviesAdapter;
    protected List<Movies> mMoviesList;
    protected ProgressDialog pd;
    protected SwipeRefreshLayout swipeContainer;
    protected FavoriteDbHelper favoriteDbHelper;
    public static final String LOG_TAG = MoviesAdapter.class.getName();
    protected int FirstPageNumber = 1;
    protected int LoadMorePageNumber = 1;
    protected int LoadLessPageNumber = 1;
    protected int LoadMorePageNumber2 = 1;
    protected int LoadLessPageNumber2 = 1;

    public Activity getActivity() {
        return this;
    }
}
