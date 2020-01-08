package com.example.moviesjavaandroidurbanadarshverma1236.MainActivityComponents;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;

import com.example.moviesjavaandroidurbanadarshverma1236.R;
import com.example.moviesjavaandroidurbanadarshverma1236.SettingActivity;

public abstract class maMenu extends maLoadRatedMovies {
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
}
