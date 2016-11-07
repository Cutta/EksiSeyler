package com.cunoraz.eksiseyler.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.adapter.PostAdapter;
import com.cunoraz.eksiseyler.model.Post;
import com.cunoraz.eksiseyler.rest.ApiClient;
import com.cunoraz.eksiseyler.rest.ApiInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cuneytcarikci on 07/11/2016.
 *
 */

public class ContextFragment extends Fragment {
    private static final String TAG = ContextFragment.class.getSimpleName();

    RecyclerView recyclerView;

    HashSet<Post> set;

    PostAdapter adapter;

    public ContextFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = apiService.getSiteContent("eglence");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    set = new HashSet<>();
                    Element doc = Jsoup.parse((response.body().string()));
                    //    Elements links = doc.select("div");
                    Elements heros = doc.getElementsByClass("col col-1of4");
                    Post post;
                    for (Element element : heros) {
                        post = new Post();
                        post.setUrl(element.select("a").attr("href"));
                        post.setImg(element.select("img").attr("style").replace("background-image: url('","").replace("')",""));
                        post.setName(element.select("span").text());
                        set.add(post);
                    }
                    Elements others = doc.select("div.content-box");
                    for (Element element : others) {
                        post = new Post();
                        post.setUrl(element.select("a").attr("href"));
                        post.setImg(element.select("img").attr("data-src"));
                        post.setName(element.select("div.content-title").text());
                        set.add(post);
                    }
                     List<Post> posts = new ArrayList<>();
                    posts.addAll(set);

                    adapter = new PostAdapter(posts);
                    recyclerView.setAdapter(adapter);
                    Log.d(TAG, "onResponse: ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


        return view;
    }

}
