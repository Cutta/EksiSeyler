package com.cunoraz.eksiseyler.fragment;

import com.cunoraz.eksiseyler.domain.content.ContentUsecase;
import com.cunoraz.eksiseyler.model.Post;
import com.cunoraz.eksiseyler.ui.base.BasePresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

public class ContentPresenter extends BasePresenter<ContentContract.View> implements ContentContract.Presenter {

    ContentUsecase contentUsecase;
    String channel;


    @Inject
    public ContentPresenter(ContentUsecase contentUsecase, ContentContract.View view, String channel) {
        this.contentUsecase = contentUsecase;
        this.channel = channel;
        this.view = view;
    }

    @Override
    public void onViewReady() {
        contentUsecase.getPostList(channel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ArrayList<Post> posts;
                if (channel.startsWith("kategori"))
                    posts = parseCategory(response);
                else if (channel.startsWith("kanal"))
                    posts = parseChannel(response);
                else
                    posts = new ArrayList<>();

                getView().loadPosts(posts);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // TODO: 03/05/2017
            }
        });

    }

    private ArrayList<Post> parseChannel(Response<ResponseBody> response) {
        HashSet<Post> set = new HashSet<>();

        if (response == null || response.body() == null)
            return null;
        try {
            Element doc = Jsoup.parse((response.body().string()));
            Elements heroes = doc.getElementsByClass("mashup-card-item col-5of1");
            Post post;
            for (Element element : heroes) {
                post = new Post();
                post.setUrl(element.select("a").attr("href"));
                post.setImg(element.select("img").attr("src"));
                post.setName(element.select("div.mashup-title").text());
                if (post.getName() != null && !post.getName().equals(""))
                    set.add(post);
            }
            ArrayList<Post> posts = new ArrayList<>();
            posts.addAll(set);

            return posts;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private ArrayList<Post> parseCategory(Response<ResponseBody> response) {
        HashSet<Post> set = new HashSet<>();
        if (response == null || response.body() == null)
            return null;
        try {
            Element doc = Jsoup.parse((response.body().string()));
            Elements heroes = doc.getElementsByClass("col col-1of4");
            Post post;
            for (Element element : heroes) {
                post = new Post();
                post.setUrl(element.select("a").attr("href"));
                post.setImg(element.select("img").attr("style").replace("background-image: url('", "").replace("')", ""));
                post.setName(element.select("span").text());
                if (post.getName() != null && !post.getName().equals(""))
                    set.add(post);
            }
            Elements others = doc.select("div.content-box");
            for (Element element : others) {
                post = new Post();
                post.setUrl(element.select("a").get(2).attr("href"));
                post.setImg(element.select("img").attr("data-src"));
                post.setName(element.select("div.content-title").text());
                if (post.getName() != null && !post.getName().equals(""))
                    set.add(post);
            }
            ArrayList<Post> posts = new ArrayList<>();
            posts.addAll(set);

            return posts;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
