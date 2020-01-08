package com.example.moviesjavaandroidurbanadarshverma1236;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moviesjavaandroidurbanadarshverma1236.adapter.MoviesAdapter;
import com.example.moviesjavaandroidurbanadarshverma1236.api.Client;
import com.example.moviesjavaandroidurbanadarshverma1236.api.Service;
import com.example.moviesjavaandroidurbanadarshverma1236.data.FavoriteDbHelper;
import com.example.moviesjavaandroidurbanadarshverma1236.model.Movies;
import com.example.moviesjavaandroidurbanadarshverma1236.model.MoviesComparator;
import com.example.moviesjavaandroidurbanadarshverma1236.model.MoviesResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private MoviesAdapter mMoviesAdapter;
    private List<Movies> mMoviesList;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    private FavoriteDbHelper favoriteDbHelper;
    private AppCompatActivity activity = MainActivity.this;
    public static final String LOG_TAG = MoviesAdapter.class.getName();
    private int FirstPageNumber = 1;
    private int LoadMorePageNumber = 1;
    private int LoadLessPageNumber = 1;
    private int LoadMorePageNumber2 = 1;
    private int LoadLessPageNumber2 = 1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    public Activity getActivity() {
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initViews() {
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

        favoriteDbHelper = new FavoriteDbHelper(activity);

        swipeContainer = findViewById(R.id.main_content);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(MainActivity.this, "Movies Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
        checkSortOrder();
    }

    private void initViews2() {
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
        favoriteDbHelper = new FavoriteDbHelper(activity);

        getAllFavorite();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadJSON() {

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please obtain API Key Firstly", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }

            Client Client = new Client();
            Service apiService =
                    Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, FirstPageNumber);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movies> movies = response.body().getResults();
                    Collections.sort(movies, MoviesComparator.BY_NAME_ALPHABETICAL);//implement comparator
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    LoadMorePageNumber = 1;
                    LoadLessPageNumber = 1;
                    mMoviesList = movies;
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadJSON1() {

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please obtain API Key Firstly", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }

            Client Client = new Client();
            Service apiService =
                    Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, FirstPageNumber);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movies> movies = response.body().getResults();
                    Collections.sort(movies, MoviesComparator.BY_NAME_ALPHABETICAL);//implement comparator
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    LoadMorePageNumber = 1;
                    LoadLessPageNumber = 1;
                    mMoviesList = movies;
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    Log.d("Error", Objects.requireNonNull(t.getMessage()));
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Error", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            initViews2();
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

    @SuppressLint("StaticFieldLeak")
    private void getAllFavorite() {
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loadMore(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_most_popular));

        if (sortOrder.equals(this.getString(R.string.pref_most_popular))) {
            LoadMorePageNumber += 1;
            LoadLessPageNumber = LoadMorePageNumber;
            int lastPageNumber = 500;
            if (LoadMorePageNumber <= lastPageNumber) {
                try {

                    Client Client = new Client();
                    final Service apiService =
                            Client.getClient().create(Service.class);
                    Call<MoviesResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, LoadMorePageNumber);
                    call.enqueue(new Callback<MoviesResponse>() {
                        @Override
                        public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                            List<Movies> movies = response.body().getResults();
                            Collections.sort(movies, MoviesComparator.BY_NAME_ALPHABETICAL);
                            recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                            recyclerView.smoothScrollToPosition(0);
                            if (swipeContainer.isRefreshing()) {
                                swipeContainer.setRefreshing(false);
                            }
                            mMoviesList = movies;
                            Toast.makeText(getApplicationContext(), "Popular Movies: " + "Current Page: " + LoadMorePageNumber, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<MoviesResponse> call, Throwable t) {

                            Log.d("Error", Objects.requireNonNull(t.getMessage()));
                            Toast.makeText(MainActivity.this, "Error Fetching Data! OR Internet Problem", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Log.d("Error", Objects.requireNonNull(e.getMessage()));
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            } else {
                LoadMorePageNumber = lastPageNumber;
                LoadLessPageNumber = lastPageNumber;
                Toast.makeText(getApplicationContext(), "Popular Movies: " + "this is the last page: " + LoadMorePageNumber, Toast.LENGTH_SHORT).show();
                return;
            }

        } else if (sortOrder.equals(this.getString(R.string.favorite))) {   //p3
            Toast.makeText(getApplicationContext(), "Scroll down ", Toast.LENGTH_SHORT).show();
            initViews2();
        } else {
            LoadMorePageNumber2 += 1;
            LoadLessPageNumber2 = LoadMorePageNumber2;
            Log.d(LOG_TAG, "Sorting by planets");
            int lastPageNumber2 = 500;
            if (LoadMorePageNumber2 <= lastPageNumber2) {
                try {
                    if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please obtain API Key Firstly", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        return;
                    }

                    Client Client = new Client();
                    Service apiService =
                            Client.getClient().create(Service.class);
                    Call<MoviesResponse> call = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, LoadMorePageNumber2);
                    call.enqueue(new Callback<MoviesResponse>() {
                        @Override
                        public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                            List<Movies> movies = response.body().getResults();
                            Collections.sort(movies, MoviesComparator.BY_NAME_ALPHABETICAL);
                            recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                            recyclerView.smoothScrollToPosition(0);
                            if (swipeContainer.isRefreshing()) {
                                swipeContainer.setRefreshing(false);
                            }
                            mMoviesList = movies;
                            Toast.makeText(getApplicationContext(), "Rated Movies: " + "Current Page: " + LoadMorePageNumber2, Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onFailure(Call<MoviesResponse> call, Throwable t) {

                            Log.d("Error", Objects.requireNonNull(t.getMessage()));
                            Toast.makeText(MainActivity.this, "Error Fetching Data! OR Internet Problem", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Log.d("Error", Objects.requireNonNull(e.getMessage()));
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            } else {
                LoadMorePageNumber2 = lastPageNumber2;
                LoadLessPageNumber2 = lastPageNumber2;
                Toast.makeText(getApplicationContext(), "Rated Movies: " + "this is the last page: " + LoadMorePageNumber, Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loadless(View view) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_most_popular));

        if (sortOrder.equals(this.getString(R.string.pref_most_popular))) {
            LoadLessPageNumber -= 1;
            LoadMorePageNumber -= 1;
            if (LoadLessPageNumber >= FirstPageNumber) {
                try {
                    Client Client = new Client();
                    final Service apiService =
                            Client.getClient().create(Service.class);
                    Call<MoviesResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, LoadLessPageNumber);
                    call.enqueue(new Callback<MoviesResponse>() {
                        @Override
                        public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                            List<Movies> movies = response.body().getResults();
                            Collections.sort(movies, MoviesComparator.BY_NAME_ALPHABETICAL);
                            recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                            recyclerView.smoothScrollToPosition(0);
                            if (swipeContainer.isRefreshing()) {
                                swipeContainer.setRefreshing(false);
                            }
                            mMoviesList = movies;
                            Toast.makeText(getApplicationContext(), "Popular Movies: " + "Current Page: " + LoadMorePageNumber, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<MoviesResponse> call, Throwable t) {

                            Log.d("Error", Objects.requireNonNull(t.getMessage()));
                            Toast.makeText(MainActivity.this, "Error Fetching Data! OR Internet Problem", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Log.d("Error", Objects.requireNonNull(e.getMessage()));
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            } else {
                LoadLessPageNumber = 1;
                LoadMorePageNumber = 1;
                Toast.makeText(getApplicationContext(), "Popular Movies: " + "this is the last page: " + LoadMorePageNumber, Toast.LENGTH_SHORT).show();
                return;
            }

        } else if (sortOrder.equals(this.getString(R.string.favorite))) {
            Toast.makeText(getApplicationContext(), "Scroll down ", Toast.LENGTH_SHORT).show();
            initViews2();
        } else {
            LoadLessPageNumber2 -= 1;
            LoadMorePageNumber2 -= 1;
            Log.d(LOG_TAG, "Sorting by planets");
            if (LoadLessPageNumber2 >= FirstPageNumber) {
                try {
                    if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please obtain API Key Firstly", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        return;
                    }

                    Client Client = new Client();
                    Service apiService =
                            Client.getClient().create(Service.class);
                    Call<MoviesResponse> call = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, LoadMorePageNumber2);
                    call.enqueue(new Callback<MoviesResponse>() {
                        @Override
                        public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                            List<Movies> movies = response.body().getResults();
                            Collections.sort(movies, MoviesComparator.BY_NAME_ALPHABETICAL);
                            recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                            recyclerView.smoothScrollToPosition(0);
                            if (swipeContainer.isRefreshing()) {
                                swipeContainer.setRefreshing(false);
                            }
                            mMoviesList = movies;
                            Toast.makeText(getApplicationContext(), "Rated Movies: " + "Current Page: " + LoadMorePageNumber2, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<MoviesResponse> call, Throwable t) {

                            Log.d("Error", Objects.requireNonNull(t.getMessage()));
                            Toast.makeText(MainActivity.this, "Error Fetching Data! OR Internet Problem", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Log.d("Error", Objects.requireNonNull(e.getMessage()));
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            } else {
                LoadLessPageNumber2 = 1;
                LoadMorePageNumber2 = 1;
                Toast.makeText(getApplicationContext(), "Rated Movies: " + "this is the last page: " + LoadMorePageNumber, Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }


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

