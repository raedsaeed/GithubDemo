package com.example.raed.githubdemo.model.local;

import com.example.raed.githubdemo.model.Repo;

import java.util.List;

/**
 * Created by raed on 9/6/18.
 */

public interface Controller {
    long addRepos (List<Repo> repoList);
    void clearRepos ();
    void loadFromStorage ();
}
