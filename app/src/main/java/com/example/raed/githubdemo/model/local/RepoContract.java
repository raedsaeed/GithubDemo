package com.example.raed.githubdemo.model.local;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by raed on 9/6/18.
 */

public class RepoContract {
    private static final String TAG = "RepoContract";

    public static final String CONTENT_AUTHORITY = "com.example.raed.githubdemo";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH = "repos";

    public static final class RepoEntry implements BaseColumns {

        public static final String TABLE_NAME = "repos";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH).build();

        public static final String COLUMN_REPO_NAME = "repo_name";
        public static final String COLUMN_REPO_URL = "repo_url";
        public static final String COLUMN_REPO_DESCRIPTION = "repo_description";
        public static final String COLUMN_REPO_FORK = "repo_fork";
        public static final String COLUMN_REPO_OWNER_NAME = "repo_owner_name";
        public static final String COLUMN_REPO_OWNER_URL = "repo_owner_url";
    }
}
