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

    private Main.View viewListner;
    private static MainPresenter presenter;
    private Interceptor interceptor;
    private static int pageNumber = 1;


    private MainPresenter (Context context) {
            this.viewListner = (Main.View)context;
            interceptor = new Interceptor(context, this);
    }

    public static MainPresenter getInstance(Context context) {
        if (presenter == null) {
            return new MainPresenter(context);
        }
        return presenter;
    }

    @Override
    public void requestData(int pageNumber) {
        RepoCallBack.getInstance(this).getRepoList(pageNumber);
    }

    @Override
    public void refreshData(int pageNumber) {
        RepoCallBack.getInstance(this).refreshList(pageNumber);
    }

    @Override
    public void loadLocalData() {
        interceptor.loadFromStorage();
    }

    @Override
    public void clearData() {
        interceptor.clearRepos();
    }

    @Override
    public void onCompleteRequest(List<Repo> repoList) {
        viewListner.showNewData(repoList);
        interceptor.addRepos(repoList);
    }

    @Override
    public void onCompleteMoreRequest(List<Repo> repoList) {
        viewListner.showMoreData(repoList);
        interceptor.addRepos(repoList);
    }

    @Override
    public void onFailureRequest() {
        viewListner.savedState();
    }

    @Override
    public void onLoadData(List<Repo> repoList) {
        if (repoList == null) {
            requestData(pageNumber);
        }else {
            viewListner.showLocalData(repoList);
        }
    }
}
