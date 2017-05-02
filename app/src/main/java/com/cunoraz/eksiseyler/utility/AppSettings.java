package com.cunoraz.eksiseyler.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.cunoraz.eksiseyler.model.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AppSettings {

    private static AppSettings INSTANCE;
    private static Gson gson;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static final String BOOKMARKED_LIST = "BookmarkedPostList";


    public static synchronized AppSettings getInstance() {

        if (INSTANCE == null) {
          //  INSTANCE = new AppSettings(EksiSeylerApp.getInstance());
            gson = new Gson();
        }

        return INSTANCE;
    }

    public AppSettings(Context context) {
        initInstance(context);
    }

    private void initInstance(Context context) {
        pref = context.getSharedPreferences("eksi_seyler_db", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return pref.getBoolean(key, true);
    }

    public boolean isBookmarked(Post post) {
        return getBookmarkedPostList().contains(post);
    }

    public void savePost(Post post) {
        List<Post> posts = getBookmarkedPostList();

        if (posts.contains(post)) {
            posts.remove(post);
        }
        posts.add(post);
        editor.putString(BOOKMARKED_LIST, gson.toJson(posts)).commit();
    }

    public void deletePost(Post post) {

        List<Post> posts = getBookmarkedPostList();
        posts.remove(post);
        editor.putString(BOOKMARKED_LIST, gson.toJson(posts)).commit();
    }

    public Post getPost(Post keyPost) {
        List<Post> posts = getBookmarkedPostList();
        for (Post post : posts) {
            if (post.equals(keyPost))
                return post;
        }
        return null;
    }

    public List<Post> getBookmarkedPostList() {

        List<Post> postList = null;

        try {
            postList = gson.fromJson(pref.getString(BOOKMARKED_LIST, null),
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
}
