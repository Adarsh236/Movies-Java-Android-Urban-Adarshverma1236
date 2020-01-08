package com.example.moviesjavaandroidurbanadarshverma1236.DetailActivityComponents;

import android.database.Cursor;

import com.example.moviesjavaandroidurbanadarshverma1236.data.FavoriteContract;

public abstract class dCheckExitFavorites extends dSaveFavorite {
    public boolean Exists(String searchItem) {

        String[] projection = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_Popularity,
                FavoriteContract.FavoriteEntry.COLUMN_Vote_Count,
                FavoriteContract.FavoriteEntry.COLUMN_Adult,
                FavoriteContract.FavoriteEntry.COLUMN_Original_Language,
                FavoriteContract.FavoriteEntry.COLUMN_Vote_Average,
                FavoriteContract.FavoriteEntry.COLUMN_Overview,
                FavoriteContract.FavoriteEntry.COLUMN_Release_Date,
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH

        };
        String selection = FavoriteContract.FavoriteEntry.COLUMN_TITLE + " =?";
        String[] selectionArgs = {searchItem};
        String limit = "1";

        Cursor cursor = mDb.query(FavoriteContract.FavoriteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}
