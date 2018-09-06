package com.example.raed.githubdemo.mainactivity;


import com.example.raed.githubdemo.model.Repo;
import com.example.raed.githubdemo.network.RepoCallBack;

import java.util.List;

/**
 * Created by raed on 9/5/18.
 */

public class MainPresenter implements Main.Presenter, RepoCallBack.CompletedRequestListener {
    private static final String TAG = "MainPresenter";

    private Main.View viewListner;
    private static MainPresenter presenter;

    private MainPresenter (Main.View view) {
            this.viewListner = view;
    }

    public static MainPresenter getInstance(Main.View view) {
        if (presenter == null) {
            return new MainPresenter(view);
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
    public void onCompleteRequest(List<Repo> repoList) {
        viewListner.showData(repoList);
    }

    @Override
    public void onCompleteMoreRequest(List<Repo> repoList) {
        viewListner.showMoreData(repoList);
    }
}
