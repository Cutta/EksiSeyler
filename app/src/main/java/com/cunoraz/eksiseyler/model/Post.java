package com.cunoraz.eksiseyler.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cuneytcarikci on 07/11/2016.
 */

public class Post implements Parcelable {

    private String url;
    private String img;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.img);
        dest.writeString(this.name);
    }

    public Post() {
    }

    private Post(Parcel in) {
        this.url = in.readString();
        this.img = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
