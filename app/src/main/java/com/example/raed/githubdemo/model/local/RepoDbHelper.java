package com.example.raed.githubdemo.model.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.raed.githubdemo.model.Repo;

/**
 * Created by raed on 9/6/18.
 */

public class RepoDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "repoDb.db";
    private static final int VERSION = 1;

    public RepoDbHelper (Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + RepoContract.RepoEntry.TABLE_NAME +  " ("+
                RepoContract.RepoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                RepoContract.RepoEntry.COLUMN_REPO_NAME + " TEXT NOT NULL, "+
                RepoContract.RepoEntry.COLUMN_REPO_DESCRIPTION + " TEXT NOT NULL, "+
                RepoContract.RepoEntry.COLUMN_REPO_URL + " TEXT NOT NULL, "+
                RepoContract.RepoEntry.COLUMN_REPO_FORK + " TEXT NOT NULL, "+
                RepoContract.RepoEntry.COLUMN_REPO_OWNER_NAME + " TEXT NOT NULL, "+
                RepoContract.RepoEntry.COLUMN_REPO_OWNER_URL + " TEXT NOT NULL );";

        Log.d(getClass().getSimpleName(), "onCreate: " + CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RepoContract.RepoEntry.TABLE_NAME);
        onCreate(db);
    }
}
