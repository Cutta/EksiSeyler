package com.cunoraz.eksiseyler.ui.search;

import com.cunoraz.eksiseyler.domain.search.SearchUseCase;
import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.cunoraz.eksiseyler.ui.base.BasePresenter;
import com.cunoraz.eksiseyler.ui.content.PostAdapter;

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
 * Created by cuneytcarikci on 08/05/2017.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private SearchUseCase mSearchUseCase;

    @Inject
    public SearchPresenter(SearchUseCase mSearchUseCase) {
        this.mSearchUseCase = mSearchUseCase;
    }


    @Override
    public void onViewReady() {

    }

    @Override
    public void onClickPost(PostAdapter.PostViewHolder viewHolder, Post post) {
        getView().openDetail(viewHolder, post);
    }

    @Override
    public void onQueryTextChange(String query) {
        mSearchUseCase.getSearchResultList(query).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ArrayList<Post> posts;
                posts = parseChannel(response);
                if (posts != null && posts.size() != 0)
                    getView().loadPosts(posts);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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
}
