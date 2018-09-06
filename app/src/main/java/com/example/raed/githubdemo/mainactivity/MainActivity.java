package com.example.raed.githubdemo.mainactivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.raed.githubdemo.R;
import com.example.raed.githubdemo.model.Repo;
import com.example.raed.githubdemo.recyclershelpers.EndlessRecyclerViewScrollListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Main.View {
    private static final String TAG = "MainActivity";

    private RepoAdapter adapter;
    private RecyclerView recyclerView;
    private int currentPageNumber = 1;
    private MainPresenter presenter;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = MainPresenter.getInstance(this);
        presenter.loadLocalData();

        adapter = new RepoAdapter(this);
        recyclerView = findViewById(R.id.repo_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        final EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(final int page, int totalItemsCount, RecyclerView view) {
                currentPageNumber = page;
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        presenter.requestData(page);
                    }
                });

            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollListener.resetState();
                refreshLayout.setRefreshing(true);
                presenter.refreshData(currentPageNumber);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLocalData(List<Repo> repoList) {
        adapter.clear();
        adapter.loadData(repoList);
    }

    @Override
    public void showNewData(List<Repo> repoList) {
        adapter.clear();
        presenter.clearData();
        if (repoList != null) {
            adapter.loadData(repoList);
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showMoreData(List<Repo> repoList) {
        adapter.loadMoreData(repoList);
    }

    @Override
    public void savedState() {
        refreshLayout.setRefreshing(false);
    }
}
