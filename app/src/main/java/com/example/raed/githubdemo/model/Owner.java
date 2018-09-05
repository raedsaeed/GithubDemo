package com.example.raed.githubdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raed on 9/5/18.
 */

public class Owner implements Parcelable{
    private String login;

    public Owner(String login) {
        this.login = login;
    }

    protected Owner(Parcel in) {
        login = in.readString();
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };

    public String getLogin() {
        return login;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
    }
}
