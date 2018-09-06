package com.example.raed.githubdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raed on 9/5/18.
 */

public class Repo implements Parcelable{
    private String name;
    private Owner owner;
    private String html_url;
    private String description;
    private boolean fork;

    public Repo(String name, Owner owner, String html_url, String description, boolean fork) {
        this.name = name;
        this.owner = owner;
        this.html_url = html_url;
        this.description = description;
        this.fork = fork;
    }

    protected Repo(Parcel in) {
        name = in.readString();
        owner = in.readParcelable(Owner.class.getClassLoader());
        html_url = in.readString();
        description = in.readString();
        fork = in.readByte() != 0;
    }

    public static final Creator<Repo> CREATOR = new Creator<Repo>() {
        @Override
        public Repo createFromParcel(Parcel in) {
            return new Repo(in);
        }

        @Override
        public Repo[] newArray(int size) {
            return new Repo[size];
        }
    };

    public String getName() {
        return name;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getHtmlUrl() {
        return html_url;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFork() {
        return fork;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(owner, flags);
        dest.writeString(html_url);
        dest.writeString(description);
        dest.writeByte((byte) (fork ? 1 : 0));
    }
}
