package com.example.moviesjavaandroidurbanadarshverma1236.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moviesjavaandroidurbanadarshverma1236.model.Movies;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviefavorite2.db";
    private static final int DATABASE_VERSION = 1;
    private static final String LOGTAG = "FAVORITE";

    private SQLiteOpenHelper dbhandler;

    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteContract.FavoriteEntry.TABLE_NAME + " (" +
                FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID + " INTEGER, " +
                FavoriteContract.FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.COLUMN_Popularity + " REAL, " +
                FavoriteContract.FavoriteEntry.COLUMN_Vote_Count + " REAL, " +
                FavoriteContract.FavoriteEntry.COLUMN_Adult + " TEXT, " +
                FavoriteContract.FavoriteEntry.COLUMN_Original_Language + " TEXT, " +
                FavoriteContract.FavoriteEntry.COLUMN_Vote_Average + " REAL , " +
                FavoriteContract.FavoriteEntry.COLUMN_Overview + " TEXT , " +
                FavoriteContract.FavoriteEntry.COLUMN_Release_Date + " TEXT ," +
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH + " TEXT " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addFavorite(Movies movies) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID, movies.getId());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE, movies.getOriginalTitle());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_Popularity, movies.getPopularity());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_Vote_Count, movies.getVoteCount());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_Adult, movies.isAdult());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_Original_Language, movies.getOriginalLanguage());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_Vote_Average, movies.getVoteAverage());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_Overview, movies.getOverview());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_Release_Date, movies.getReleaseDate());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH, movies.getPosterPath());

        db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteFavorite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, FavoriteContract.FavoriteEntry.COLUMN_MOVIEID + "=" + id, null);
    }

    public List<Movies> getAllFavorite() {
        String[] columns = {
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
        String sortOrder =
                FavoriteContract.FavoriteEntry._ID + " ASC";
        List<Movies> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Movies movies = new Movies();
                movies.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID))));
                movies.setOriginalTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_TITLE)));
                movies.setPopularity(Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_Popularity))));
                movies.setVoteCount(Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_Vote_Count))));
                movies.setAdult(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_Adult))));
                movies.setOriginalLanguage(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_Original_Language)));
                movies.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_Vote_Average))));
                movies.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_Overview)));
                movies.setReleaseDate(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_Release_Date)));
                movies.setPosterPath(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH)));

                favoriteList.add(movies);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }
}
