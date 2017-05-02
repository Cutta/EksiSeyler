package com.cunoraz.eksiseyler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.activity.DetailActivity;
import com.cunoraz.eksiseyler.adapter.PostAdapter;
import com.cunoraz.eksiseyler.di.main.content.ContentModule;
import com.cunoraz.eksiseyler.di.main.content.DaggerContentComponent;
import com.cunoraz.eksiseyler.model.Channel;
import com.cunoraz.eksiseyler.model.Post;
import com.cunoraz.eksiseyler.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cuneytcarikci on 07/11/2016.
 */

public class ContentFragment extends BaseFragment implements ContentContract.View, PostAdapter.ItemClick {

    private static final String TAG = ContentFragment.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    List<Post> posts;
    PostAdapter adapter;
    Channel channel;

    @Inject
    ContentPresenter contentPresenter;

    public static ContentFragment newInstance(Channel channel) {

        Bundle args = new Bundle();
        args.putParcelable("arg_channel", channel);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ContentFragment() {
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
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        contentPresenter.onViewReady();

        setupRecyclerView();

    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerContentComponent.builder().
                appComponent(getApplicationComponent())
                .contentModule(new ContentModule(this, channel.getUrlName()))
                .build().inject(this);

    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new PostAdapter(ContentFragment.this, new ArrayList<Post>());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("extra_post", posts.get(position));
        intent.putExtra("extra_category", channel.getName());

        String transitionName = getString(R.string.image_transition_name);
        ImageView viewStart = (ImageView) v.findViewById(R.id.row_image);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        viewStart,
                        transitionName
                );
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    @Override
    public void loadPosts(ArrayList<Post> posts) {

        adapter.updatePosts(posts);

    }
}
