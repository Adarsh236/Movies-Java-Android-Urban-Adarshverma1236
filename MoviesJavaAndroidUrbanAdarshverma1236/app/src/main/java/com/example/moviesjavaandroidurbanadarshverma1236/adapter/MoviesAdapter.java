package com.example.moviesjavaandroidurbanadarshverma1236.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesjavaandroidurbanadarshverma1236.DetailActivity;
import com.example.moviesjavaandroidurbanadarshverma1236.R;
import com.example.moviesjavaandroidurbanadarshverma1236.model.Movies;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movies> mMoviesList;

    public MoviesAdapter(Context mContext, List<Movies> moviesList) {
        this.mContext = mContext;
        this.mMoviesList = moviesList;
    }

    @NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_card, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(mMoviesList.get(i).getOriginalTitle());
        String vote = Double.toString(mMoviesList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote);

        String poster = "https://image.tmdb.org/t/p/w500" + mMoviesList.get(i).getPosterPath();

        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, userrating;
        ImageView thumbnail;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            userrating = view.findViewById(R.id.userrating);
            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Movies clickedDataItem = mMoviesList.get(pos);
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("movies", clickedDataItem);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // set filter
    public void setFilter(ArrayList<Movies> newList) {
        mMoviesList = new ArrayList<>();
        mMoviesList.addAll(newList);
        notifyDataSetChanged();

    }
}
