package com.cunoraz.eksiseyler.ui.favourites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.di.favourites.DaggerFavouritesComponent;
import com.cunoraz.eksiseyler.di.favourites.FavouritesModule;
import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.cunoraz.eksiseyler.ui.base.BaseActivity;
import com.cunoraz.eksiseyler.ui.content.PostAdapter;
import com.cunoraz.eksiseyler.ui.detail.DetailActivity;
import com.cunoraz.eksiseyler.util.DialogBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cuneytcarikci on 21/04/2017.
 * Kaydedilendleri gösterdigimiz activty
 */

public class FavouritesListActivity extends BaseActivity implements FavouritiesContract.View, PostAdapter.PostAdapterCallback {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.empty_list_text)
    TextView emptyListText;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    FavouritePresenter mPresenter;

    PostAdapter mAdapter;

    private Menu mMenu;

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);
        setToolbar();
        setupRecyclerView();
        mPresenter.onViewReady();
    }

    private void setupRecyclerView() {

        mAdapter = new PostAdapter(FavouritesListActivity.this, new ArrayList<Post>());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(FavouritesListActivity.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_favourities_list;
    }

    @Override
    protected void resolveDaggerDependency() {

        DaggerFavouritesComponent.builder()
                .appComponent(getApplicationComponent())
                .favouritesModule(new FavouritesModule(this))
                .build().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMenu != null)
            updateSavingModeMenuItem(mPresenter.isSavingModeActive());

        if (mAdapter != null && recyclerView != null) {
            mPresenter.onResume();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_favourites_activity, menu);
        this.mMenu = menu;
        updateSavingModeMenuItem(mPresenter.isSavingModeActive());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ic_menu_show_image:
                mPresenter.onClickSavingModeMenuItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickPost(PostAdapter.PostViewHolder viewHolder, Post post) {
        mPresenter.onClickPost(viewHolder, post);

    }

    @Override
    public void showSavingModeActiveDialog() {
        DialogBuilder.infoDialog(FavouritesListActivity.this,
                R.string.dialog_title_saving_mode_active,
                R.string.dialog_message_saving_mode_active)
                .show();
    }

    @Override
    public void showSavingModeNotActiveDialog() {
        DialogBuilder.infoDialog(FavouritesListActivity.this,
                R.string.dialog_title_saving_mode_passive,
                R.string.dialog_message_saving_mode_passive)
                .show();
    }

    @Override
    public void updateSavingModeMenuItem(boolean isActive) {
        if (isActive)
            mMenu.getItem(0).setIcon(ContextCompat.getDrawable(FavouritesListActivity.this, R.drawable.ic_photo_gray_24dp));
        else
            mMenu.getItem(0).setIcon(ContextCompat.getDrawable(FavouritesListActivity.this, R.drawable.ic_photo_white_24dp));
    }

    @Override
    public void loadPosts(ArrayList<Post> posts) {
        if (posts.size() != 0) {
            mAdapter.updatePosts(posts);
        } else {
            mAdapter.resetAdapter();
            emptyListText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void openDetail(PostAdapter.PostViewHolder viewHolder, Post post) {

        Intent intent = DetailActivity.newIntent(this, post, getString(R.string.favourite_title));

        String transitionName = getString(R.string.image_transition_name);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(FavouritesListActivity.this,
                        viewHolder.image,
                        transitionName
                );

        ActivityCompat.startActivity(FavouritesListActivity.this, intent, options.toBundle());
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}