package com.cunoraz.eksiseyler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.activity.DetailActivity;
import com.cunoraz.eksiseyler.adapter.PostAdapter;
import com.cunoraz.eksiseyler.model.Channel;
import com.cunoraz.eksiseyler.model.Post;
import com.cunoraz.eksiseyler.rest.ApiClient;
import com.cunoraz.eksiseyler.rest.ApiInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cuneytcarikci on 07/11/2016.
 */

public class ContextFragment extends Fragment implements PostAdapter.ItemClick {
    private static final String TAG = ContextFragment.class.getSimpleName();

    RecyclerView recyclerView;

    HashSet<Post> set;
    List<Post> posts;

    PostAdapter adapter;

    Channel channel;

    public static ContextFragment newInstance(Channel channel) {

        Bundle args = new Bundle();
        args.putParcelable("arg_channel", channel);
        ContextFragment fragment = new ContextFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ContextFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            channel = getArguments().getParcelable("arg_channel");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        prepareList();

        return view;
    }

    private void prepareList() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = apiService.getSiteContent(channel.getUrlName());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                set = new HashSet<>();
                if (channel.getUrlName().startsWith("kategori"))
                    parseCategory(response);
                else if (channel.getUrlName().startsWith("kanal"))
                    parseChannel(response);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void parseChannel(Response<ResponseBody> response) {
        if (response == null || response.body() == null)
            return;
        try {
            Element doc = Jsoup.parse((response.body().string()));
            Elements heros = doc.getElementsByClass("mashup-card-item col-5of1");
            Post post;
            for (Element element : heros) {
                post = new Post();
                post.setUrl(element.select("a").attr("href"));
                post.setImg(element.select("img").attr("src"));
                post.setName(element.select("div.mashup-title").text());
                set.add(post);
            }
            posts = new ArrayList<>();
            posts.addAll(set);

            adapter = new PostAdapter(ContextFragment.this, posts);
            recyclerView.setAdapter(adapter);
            Log.d(TAG, "onResponse: ");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseCategory(Response<ResponseBody> response) {
        if (response == null || response.body() == null)
            return;
        try {
            Element doc = Jsoup.parse((response.body().string()));
            //    Elements links = doc.select("div");
            Elements heros = doc.getElementsByClass("col col-1of4");
            Post post;
            for (Element element : heros) {
                post = new Post();
                post.setUrl(element.select("a").attr("href"));
                post.setImg(element.select("img").attr("style").replace("background-image: url('", "").replace("')", ""));
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
            posts = new ArrayList<>();
            posts.addAll(set);

            adapter = new PostAdapter(ContextFragment.this, posts);
            recyclerView.setAdapter(adapter);
            Log.d(TAG, "onResponse: ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("extra_post", posts.get(position));
        startActivity(intent);

    }
}
