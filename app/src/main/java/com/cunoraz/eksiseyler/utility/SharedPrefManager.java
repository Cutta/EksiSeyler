package com.cunoraz.eksiseyler.utility;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.cunoraz.eksiseyler.model.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuneytcarikci on 02/01/2017.
 * sharedpref islemleri burada yapiliyor
 */

public class SharedPrefManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static final String BOOKMARKED_LIST = "BookmarkedPostList";

    public SharedPrefManager(Application context) {
        prefs = context.getSharedPreferences("eksi_seyler_db", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return prefs.getBoolean(key, true);//default resimleri goster
    }

    public void savePost(Post post) {
        List<Post> posts = getBookmarkedPostList();

        if (posts.contains(post)) {
            posts.remove(post);
        }
        posts.add(post);
        editor.putString(BOOKMARKED_LIST, new Gson().toJson(posts)).commit();
    }

    public Post getPost(Post keyPost) {
        List<Post> posts = getBookmarkedPostList();
        for (Post post : posts) {
            if (post.equals(keyPost))
                return post;
        }
        return null;
    }

    public boolean isBookmarked(Post post) {
        List<Post> postList = getBookmarkedPostList();
        return postList.contains(post);
    }

    public List<Post> getBookmarkedPostList() {
        List<Post> postList = null;

        try {
            postList = new Gson().fromJson(prefs.getString(BOOKMARKED_LIST, null),
                    new TypeToken<List<Post>>() {
                    }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (postList == null) {
            postList = new ArrayList<>();
        }

        return postList;
    }

    public void deletePost(Post post) {

        List<Post> posts = getBookmarkedPostList();
        posts.remove(post);
        editor.putString(BOOKMARKED_LIST, new Gson().toJson(posts)).commit();
    }
}
