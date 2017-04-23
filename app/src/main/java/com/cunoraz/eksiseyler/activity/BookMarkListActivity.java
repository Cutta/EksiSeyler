package com.cunoraz.eksiseyler.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.adapter.PostAdapter;
import com.cunoraz.eksiseyler.app.MyApplication;
import com.cunoraz.eksiseyler.model.Post;
import com.cunoraz.eksiseyler.utility.AppSettings;

import java.util.List;

/**
 * Created by cuneytcarikci on 21/04/2017.
 * Kaydedilendleri gösterdigimiz activty
 */

public class BookMarkListActivity extends AppCompatActivity implements PostAdapter.ItemClick {
    AppSettings manager;
    RecyclerView recyclerView;
    TextView emptyListText;
    PostAdapter adapter;
    Toolbar toolbar;
    List<Post> posts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list);
        manager = ((MyApplication) getApplication()).getSharedPrefManager();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        emptyListText = (TextView) findViewById(R.id.empty_list_text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(BookMarkListActivity.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        posts = manager.getBookmarkedPostList();
        if (posts.size() != 0) {
            adapter = new PostAdapter(BookMarkListActivity.this, posts);
            recyclerView.setAdapter(adapter);
        } else
            emptyListText.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (manager != null && adapter != null && recyclerView != null) {
            posts = manager.getBookmarkedPostList();
            if (posts.size() != 0) {
                adapter = new PostAdapter(BookMarkListActivity.this, posts);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.resetAdapter();
                emptyListText.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(BookMarkListActivity.this, DetailActivity.class);
        intent.putExtra("extra_post", posts.get(position));
        intent.putExtra("extra_category", "Favori Yazı");

        String transitionName = getString(R.string.image_transition_name);
        ImageView viewStart = (ImageView) v.findViewById(R.id.row_image);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(BookMarkListActivity.this,
                        viewStart,   // Starting view
                        transitionName    // The String
                );
        ActivityCompat.startActivity(BookMarkListActivity.this, intent, options.toBundle());
    }
}
