package com.example.raed.githubdemo.model.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by raed on 9/6/18.
 */

public class RepoContentProvider extends ContentProvider {
    private static final String TAG = "RepoContentProvider";

    private static final int REPOS = 100;

    public static UriMatcher matcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher () {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(RepoContract.CONTENT_AUTHORITY, RepoContract.PATH, 100);
        return matcher;
    }

    private RepoDbHelper dbHelper;
    private Context context;


    @Override
    public boolean onCreate() {
        context = getContext();
        dbHelper = new RepoDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase database = dbHelper.getReadableDatabase();
        int match = matcher.match(uri);
        Cursor cursor;
        switch (match) {
            case REPOS:
                cursor = database.query(RepoContract.RepoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
                default:
                    throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        cursor.setNotificationUri(context.getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        int match = matcher.match(uri);
        int insertCount = 0;
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        switch (match) {
            case REPOS:
                try {
                    database.beginTransaction();
                    for (ContentValues contentValues : values) {
                        long id = database.insert(RepoContract.RepoEntry.TABLE_NAME, null, contentValues);
                        if (id >0) {
                            insertCount++;
                        }
                    }
                    database.setTransactionSuccessful();
                }catch (Exception e) {

                }finally {
                    database.endTransaction();
                }
                break;
            default: throw new IllegalArgumentException("Unknown Uri" + uri);

        }
        return insertCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(RepoContract.RepoEntry.TABLE_NAME, null, null);
        context.getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
