package com.example.raed.githubdemo.network;

import com.example.raed.githubdemo.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by raed on 9/5/18.
 */

public interface GithubApiService {
    @GET("repos")
    Call<List<Repo>> getRepos (@Query("page") int pageNumber,
                               @Query("per_page") int limit);
}
