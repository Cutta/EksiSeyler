package com.cunoraz.eksiseyler.model.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andanicalik on 03/05/17.
 */

public class PreferencesHelperImpl implements PreferencesHelper {

    private static final String BOOKMARKED_LIST = "BookmarkedPostList";
    private static final String DISPLAY_IMAGE = "showImage";

    private Gson mGson;
    private SharedPreferences mSharedPreferences;

    public PreferencesHelperImpl(Context context, Gson gson) {
        this.mGson = gson;
        this.mSharedPreferences = context.getSharedPreferences("eksi_seyler_db", Context.MODE_PRIVATE);
    }

    @Override
    public List<Post> getFavouritePosts() {
        List<Post> favouritePosts = null;

        try {
            favouritePosts = mGson.fromJson(mSharedPreferences.getString(BOOKMARKED_LIST, null),
                    new TypeToken<List<Post>>() {
                    }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (favouritePosts == null)
            favouritePosts = new ArrayList<>();

        return favouritePosts;
    }

    @Override
    public void addToFavouritePosts(Post post) {
        List<Post> favouritePosts = getFavouritePosts();

        if (favouritePosts.contains(post))
            favouritePosts.remove(post);

        favouritePosts.add(post);
        mSharedPreferences.edit().putString(BOOKMARKED_LIST, mGson.toJson(favouritePosts)).apply();
    }

    @Override
    public void removeFromFavouritePosts(Post post) {
        List<Post> favouritePosts = getFavouritePosts();
        favouritePosts.remove(post);
        mSharedPreferences.edit().putString(BOOKMARKED_LIST, mGson.toJson(favouritePosts)).apply();
    }

    @Override
    public boolean isOneOfFavouritePosts(Post post) {
        return getFavouritePosts().contains(post);
    }

    @Override
    public void storeSavingModeStatus(boolean isActive) {
        mSharedPreferences.edit().putBoolean(DISPLAY_IMAGE, isActive).apply();
    }

    @Override
    public boolean isSavingModeActive() {
        return mSharedPreferences.getBoolean(DISPLAY_IMAGE, false);
    }

}