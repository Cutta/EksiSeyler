package com.cunoraz.eksiseyler.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cuneytcarikci on 08/11/2016.
 */

public class Channel implements Parcelable {

    private String urlName;
    private String name;

    public Channel(String urlName, String name) {
        this.urlName = urlName;
        this.name = name;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
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
        dest.writeString(this.urlName);
        dest.writeString(this.name);
    }

    private Channel(Parcel in) {
        this.urlName = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Channel> CREATOR = new Parcelable.Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel source) {
            return new Channel(source);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };
}
