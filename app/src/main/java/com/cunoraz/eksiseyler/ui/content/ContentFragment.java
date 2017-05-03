package com.cunoraz.eksiseyler.ui.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.di.content.ContentModule;
import com.cunoraz.eksiseyler.di.content.DaggerContentComponent;
import com.cunoraz.eksiseyler.model.rest.entity.Channel;
import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.cunoraz.eksiseyler.ui.base.BaseFragment;
import com.cunoraz.eksiseyler.ui.detail.DetailActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cuneytcarikci on 07/11/2016.
 */

public class ContentFragment extends BaseFragment implements ContentContract.View, PostAdapter.PostAdapterCallback {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    ContentPresenter mPresenter;

    private Channel mChannel;
    private PostAdapter mAdapter;

    public static ContentFragment newInstance(Channel channel) {

        Bundle args = new Bundle();
        args.putParcelable("arg_channel", channel);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //region Override Methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mChannel = getArguments().getParcelable("arg_channel");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        setupRecyclerView();
        mPresenter.onViewReady();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerContentComponent.builder().
                appComponent(getApplicationComponent())
                .contentModule(new ContentModule(this, mChannel.getUrlName()))
                .build().inject(this);
    }

    //endregion

    //region Setup Methods

    private void setupRecyclerView() {
        mAdapter = new PostAdapter(ContentFragment.this, new ArrayList<Post>());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    //endregion

    //region View Methods

    @Override
    public void loadPosts(ArrayList<Post> posts) {
        mAdapter.updatePosts(posts);
    }

    @Override
    public void openDetail(PostAdapter.PostViewHolder viewHolder, Post post) {

        Intent intent = DetailActivity.newIntent(getContext(), post, mChannel.getName());

        String transitionName = getString(R.string.image_transition_name);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        viewHolder.image,
                        transitionName
                );

        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());

    }

    //endregion

    //region Adapter Callback

    @Override
    public void onClickPost(PostAdapter.PostViewHolder viewHolder, Post post) {
        mPresenter.onClickPost(viewHolder, post);
    }

    //endregion

}