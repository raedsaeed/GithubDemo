package com.example.raed.githubdemo.network;

import android.util.Log;

import com.example.raed.githubdemo.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by raed on 9/5/18.
 */

public class RepoCallBack implements Callback<List<Repo>> {
    private static final String TAG = "RepoCallBack";

    private static final String BASE_URL = "https://api.github.com/users/square/";
    private static Retrofit retrofit;
    private static GithubApiService apiService;
    private CompletedRequestListener listener;
    private static final int CURRENT_PAGE = 1;
    private int pageNumber = 1;


    private RepoCallBack (CompletedRequestListener listener) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(GithubApiService.class);
        }

        this.listener = listener;
    }

    public static RepoCallBack getInstance (CompletedRequestListener listener){
        return new RepoCallBack(listener);
    }

    public void getRepoList (int pageNumber) {
        this.pageNumber = pageNumber;
        Call<List<Repo>> response = apiService.getRepos(pageNumber, 10);
        response.enqueue(this);
    }

    public void refreshList (int pageNumber) {
        this.pageNumber = 1;
        Call<List<Repo>> response = apiService.getRepos(pageNumber, 10);
        response.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
        List<Repo> repos = response.body();
        Log.d(TAG, "onResponse: " + pageNumber);
        if (pageNumber > CURRENT_PAGE) {
            listener.onCompleteMoreRequest(repos);
        }else {
            listener.onCompleteRequest(repos);
        }
    }

    @Override
    public void onFailure(Call<List<Repo>> call, Throwable t) {
        listener.onFailureRequest();
    }

    public interface CompletedRequestListener {
        void onCompleteRequest(List<Repo> repoList);
        void onCompleteMoreRequest (List<Repo> repoList);
        void onFailureRequest ();
    }
}
