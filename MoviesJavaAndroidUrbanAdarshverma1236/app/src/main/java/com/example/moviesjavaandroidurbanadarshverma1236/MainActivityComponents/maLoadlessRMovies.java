package com.example.moviesjavaandroidurbanadarshverma1236.MainActivityComponents;

import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.moviesjavaandroidurbanadarshverma1236.BuildConfig;
import com.example.moviesjavaandroidurbanadarshverma1236.adapter.MoviesAdapter;
import com.example.moviesjavaandroidurbanadarshverma1236.api.Client;
import com.example.moviesjavaandroidurbanadarshverma1236.api.Service;
import com.example.moviesjavaandroidurbanadarshverma1236.model.Movies;
import com.example.moviesjavaandroidurbanadarshverma1236.model.MoviesComparator;
import com.example.moviesjavaandroidurbanadarshverma1236.model.MoviesResponse;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class maLoadlessRMovies extends maLoadlessPMovies{
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void GetLastRatedMovie() {LoadLessPageNumber2 -= 1;
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

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                        Log.d("Error", Objects.requireNonNull(t.getMessage()));
                        Toast.makeText(getApplicationContext(), "Error Fetching Data! OR Internet Problem", Toast.LENGTH_SHORT).show();
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
        }}
}
