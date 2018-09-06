package com.example.raed.githubdemo.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.raed.githubdemo.R;
import com.example.raed.githubdemo.model.Repo;

/**
 * Created by raed on 9/6/18.
 */

public class BrowseDialog extends DialogFragment {
    private static final String TAG = "BrowseDialog";
    public static final String EXTRA_REPO = "com.example.raed.dialog.EXTRA_REPO";

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.browse_dialog, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        TextView repoLink = view.findViewById(R.id.repo_link);
        repoLink.setMovementMethod(LinkMovementMethod.getInstance());

        TextView repoOwner = view.findViewById(R.id.repo_owner);
        repoOwner.setMovementMethod(LinkMovementMethod.getInstance());

        Bundle bundle = getArguments();
        Repo repo;
        if (bundle != null) {
            repo = bundle.getParcelable(EXTRA_REPO);
            repoLink.setText(repo.getHtmlUrl());
            repoOwner.setText(repo.getOwner().getHtmlUrl());
        }

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
