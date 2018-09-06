package com.example.raed.githubdemo.mainactivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raed.githubdemo.R;
import com.example.raed.githubdemo.model.Repo;

import java.util.List;

/**
 * Created by raed on 9/5/18.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    private static final String TAG = "RepoAdapter";

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private boolean isLoadingAdded = false;

    private List<Repo> repoList;
    private Context context;

    public RepoAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.repo_item, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        Repo repo = repoList.get(position);
        if (repo != null) {
            holder.repoName.setText(repo.getName());
            holder.repoDescription.setText(repo.getDescription());
            holder.repoOwner.setText(repo.getOwner().getLogin());
            if (!repo.isFork()){
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.primary_light_card));
            }else {
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.white_card_background));
            }
        }
    }

    @Override
    public int getItemCount() {
        return (repoList != null) ? repoList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == repoList.size() -1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void loadData(List<Repo> repoList) {
        this.repoList = repoList;
        notifyDataSetChanged();
    }

    public void loadMoreData (List<Repo> repos) {
        this.repoList.addAll(repos);
        notifyItemRangeInserted(getItemCount(), repoList.size() -1);
    }

    public void clear() {
        if (repoList != null) {
            repoList.clear();
            notifyDataSetChanged();
        }
    }

//    public boolean isEmpty () {
//        return getItemCount() == 0;
//    }
//
//    public void addLoadingFooter () {
//        isLoadingAdded = true;
//    }
//
//    public void removeLoadingFooter () {
//        isLoadingAdded = false;
//    }

    class RepoViewHolder extends RecyclerView.ViewHolder {
        TextView repoName, repoDescription, repoOwner;
        CardView cardView;

        public RepoViewHolder(View itemView) {
            super(itemView);
            repoName = itemView.findViewById(R.id.repo_name);
            repoDescription = itemView.findViewById(R.id.repo_description);
            repoOwner = itemView.findViewById(R.id.repo_owner);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
