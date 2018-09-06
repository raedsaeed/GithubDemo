package com.example.raed.githubdemo.model.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.example.raed.githubdemo.model.Owner;
import com.example.raed.githubdemo.model.Repo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raed on 9/6/18.
 */

public class Interceptor implements LoaderManager.LoaderCallbacks<Cursor>, Controller {
    private static final String TAG = "Interceptor";
    private Context context;
    private AppCompatActivity appCompatActivity;

    private static final int ID_REPO_LOADER = 6;
    private static String [] projection = new String [] {
            RepoContract.RepoEntry.COLUMN_REPO_NAME,
            RepoContract.RepoEntry.COLUMN_REPO_DESCRIPTION,
            RepoContract.RepoEntry.COLUMN_REPO_URL,
            RepoContract.RepoEntry.COLUMN_REPO_FORK,
            RepoContract.RepoEntry.COLUMN_REPO_OWNER_NAME,
            RepoContract.RepoEntry.COLUMN_REPO_OWNER_URL
    };

    private OnLoadData loadData;

    public interface OnLoadData {
        void onLoadData (List<Repo> repoList);
    }

    public Interceptor (Context context, OnLoadData loadData){
        this.context = context;
        this.loadData = loadData;
        this.appCompatActivity = (AppCompatActivity) context;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id) {
            case ID_REPO_LOADER:
                Uri uri = RepoContract.RepoEntry.CONTENT_URI;
                return new CursorLoader(context,
                        uri,
                        projection,
                        null,
                        null,
                        null);
                default:
                    throw new RuntimeException("Loader isn't implemented " + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        List<Repo> repoResults = new ArrayList<>();
        while (data.moveToNext()) {
            String repoName = data.getString(data.getColumnIndex(RepoContract.RepoEntry.COLUMN_REPO_NAME));
            String repoDescription = data.getString(data.getColumnIndex(RepoContract.RepoEntry.COLUMN_REPO_DESCRIPTION));
            String repoUrl = data.getString(data.getColumnIndex(RepoContract.RepoEntry.COLUMN_REPO_URL));
            boolean repoFork = data.getInt(data.getColumnIndex(RepoContract.RepoEntry.COLUMN_REPO_FORK)) > 0;
            String ownerName = data.getString(data.getColumnIndex(RepoContract.RepoEntry.COLUMN_REPO_OWNER_NAME));
            String ownerUrl = data.getString(data.getColumnIndex(RepoContract.RepoEntry.COLUMN_REPO_OWNER_URL));
            Owner owner = new Owner(ownerName, ownerUrl);
            Repo repo = new Repo(repoName, owner, repoUrl, repoDescription, repoFork);
            repoResults.add(repo);
        }
        loadData.onLoadData(repoResults);
        data.close();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public long addRepos(List<Repo> repoList) {
        if (repoList == null ) {
            return 0;
        }
        long insertCount;
        ContentValues [] contentValues = new ContentValues[repoList.size()];
        int i = 0;
        for (Repo repo : repoList) {
            ContentValues value = new ContentValues();
            value.put(RepoContract.RepoEntry.COLUMN_REPO_NAME, repo.getName());
            value.put(RepoContract.RepoEntry.COLUMN_REPO_DESCRIPTION, repo.getDescription());
            value.put(RepoContract.RepoEntry.COLUMN_REPO_URL, repo.getHtmlUrl());
            value.put(RepoContract.RepoEntry.COLUMN_REPO_FORK, repo.isFork());
            value.put(RepoContract.RepoEntry.COLUMN_REPO_OWNER_NAME, repo.getOwner().getLogin());
            value.put(RepoContract.RepoEntry.COLUMN_REPO_OWNER_URL, repo.getOwner().getHtmlUrl());
            contentValues[i++] = value;
        }
        insertCount = appCompatActivity.getContentResolver().bulkInsert(RepoContract.RepoEntry.CONTENT_URI, contentValues);
        return insertCount;
    }

    @Override
    public void clearRepos() {
        appCompatActivity.getContentResolver().delete(RepoContract.RepoEntry.CONTENT_URI,
                null,
                null);
    }

    @Override
    public void loadFromStorage () {
        appCompatActivity.getSupportLoaderManager().initLoader(ID_REPO_LOADER, null, this);
    }

}
