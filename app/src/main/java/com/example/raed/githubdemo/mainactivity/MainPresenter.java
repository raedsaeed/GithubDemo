package com.example.raed.githubdemo.mainactivity;


import android.content.Context;

import com.example.raed.githubdemo.model.Repo;
import com.example.raed.githubdemo.model.local.Interceptor;
import com.example.raed.githubdemo.network.RepoCallBack;

import java.util.List;


/**
 * Created by raed on 9/5/18.
 */

public class MainPresenter implements Main.Presenter, RepoCallBack.CompletedRequestListener,
        com.example.raed.githubdemo.model.local.Interceptor.OnLoadData{
    private static final String TAG = "MainPresenter";

    // View Listener which is implemented in MainActivity class
    private Main.View viewListener;

    // Static member variable of MainPresenter
    private static MainPresenter presenter;

    // Interceptor member variable that handle database operations.
    private Interceptor interceptor;

    // Integer used in case if there is no local data
    private static int pageNumber = 1;


    private MainPresenter (Context context) {
            this.viewListener = (Main.View)context;
            interceptor = new Interceptor(context, this);
    }

    public static MainPresenter getInstance(Context context) {
        if (presenter == null) {
            return new MainPresenter(context);
        }
        return presenter;
    }

    // Pull data from network for the first time
    @Override
    public void requestData(int pageNumber) {
        RepoCallBack.getInstance(this).getRepoList(pageNumber);
    }

    // Refresh available data
    @Override
    public void refreshData(int pageNumber) {
        RepoCallBack.getInstance(this).refreshList(pageNumber);
    }

    // Load Data from storage if it exist
    @Override
    public void loadLocalData() {
        interceptor.loadFromStorage();
    }

    // Clear data in storage
    @Override
    public void clearData() {
        interceptor.clearRepos();
    }

    @Override
    public void onCompleteRequest(List<Repo> repoList) {
        viewListener.showNewData(repoList);
        interceptor.addRepos(repoList);
    }

    @Override
    public void onCompleteMoreRequest(List<Repo> repoList) {
        viewListener.showMoreData(repoList);
        interceptor.addRepos(repoList);
    }

    @Override
    public void onFailureRequest() {
        viewListener.savedState();
    }

    @Override
    public void onLoadData(List<Repo> repoList) {
        if (repoList == null) {
            requestData(pageNumber);
        }else {
            viewListener.showLocalData(repoList);
        }
    }
}
