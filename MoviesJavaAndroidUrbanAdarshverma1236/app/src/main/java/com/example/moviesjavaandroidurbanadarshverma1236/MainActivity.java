package com.example.moviesjavaandroidurbanadarshverma1236;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moviesjavaandroidurbanadarshverma1236.MainActivityComponents.maGetAllInnerClasses;
import com.example.moviesjavaandroidurbanadarshverma1236.data.FavoriteDbHelper;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends maGetAllInnerClasses {
    private AppCompatActivity activity = MainActivity.this;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialViews1();
    }

    public Activity getActivity() {
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initialViews1() {
        InnerClassOfinitialViews();
        favoriteDbHelper = new FavoriteDbHelper(activity);
        swipeContainer = findViewById(R.id.main_content);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initialViews1();
                Toast.makeText(MainActivity.this, "Movies Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
        checkSortOrder();
    }

    // adding favorite
    private void initialViews2() {
        InnerClassOfinitialViews();
        favoriteDbHelper = new FavoriteDbHelper(activity);
        getAllFavorite();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.d(LOG_TAG, "Preferences updated");
        checkSortOrder();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void checkSortOrder() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_most_popular)
        );
        if (sortOrder.equals(this.getString(R.string.pref_most_popular))) {
            Log.d(LOG_TAG, "Sorting by most popular");
            loadJSON();
        } else if (sortOrder.equals(this.getString(R.string.favorite))) {
            Log.d(LOG_TAG, "Sorting by favorite");
            initialViews2();
        } else {
            Log.d(LOG_TAG, "Sorting by vote average");
            loadJSON1();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        if (mMoviesList.isEmpty()) {
            checkSortOrder();
        } else {
            checkSortOrder();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loadMore(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_most_popular));

        if (sortOrder.equals(this.getString(R.string.pref_most_popular))) {
            GetMorePopularMovie();
        } else if (sortOrder.equals(this.getString(R.string.favorite))) {
            Toast.makeText(getApplicationContext(), "Scroll Up ", Toast.LENGTH_SHORT).show();
            initialViews2();
        } else if (sortOrder.equals(this.getString(R.string.pref_highest_rated))) {
            GetMoreRatedMovie();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loadless(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_most_popular));

        if (sortOrder.equals(this.getString(R.string.pref_most_popular))) {
            GetLastPopularMovie();

        } else if (sortOrder.equals(this.getString(R.string.favorite))) {
            Toast.makeText(getApplicationContext(), "Scroll down ", Toast.LENGTH_SHORT).show();
            initialViews2();
        } else {
            GetLastRatedMovie();
        }
    }
}

