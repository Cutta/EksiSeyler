package com.cunoraz.eksiseyler.ui.content;

import com.cunoraz.eksiseyler.domain.content.ContentUsecase;
import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.cunoraz.eksiseyler.ui.base.BasePresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cuneytcarikci on 02/05/2017.
 */

public class ContentPresenter extends BasePresenter<ContentContract.View> implements ContentContract.Presenter {

    private String mChannel;
    private ContentUsecase mContentUsecase;

    @Inject
    public ContentPresenter(ContentUsecase contentUsecase, @Named("channel") String channel) {
        this.mContentUsecase = contentUsecase;
        this.mChannel = channel;
    }

    //region Presenter Methods

    @Override
    public void onViewReady() {
        mContentUsecase.getPostList(mChannel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ArrayList<Post> posts;
                if (mChannel.startsWith("kategori"))
                    posts = parseCategory(response);
                else if (mChannel.startsWith("kanal"))
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

    @Override
    public void onClickPost(PostAdapter.PostViewHolder viewHolder, Post post) {
        getView().openDetail(viewHolder, post);
    }

    //endregion

    //region Parse Methods

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
                try {
                    post.setUrl(element.select("a").get(2).attr("href"));
                    post.setImg(element.select("img").attr("data-src"));
                    post.setName(element.select("div.content-title").text());
                    if (post.getName() != null && !post.getName().equals(""))
                        set.add(post);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            ArrayList<Post> posts = new ArrayList<>();
            posts.addAll(set);

            return posts;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //endregion

}