package com.example.raed.githubdemo.mainactivity;

import com.example.raed.githubdemo.model.Repo;

import java.util.List;

/**
 * Created by raed on 9/5/18.
 */

public interface Main {
    interface Presenter {
        void requestData ();
    }

    interface View {
        void showData (List<Repo> repoList);
    }
}
