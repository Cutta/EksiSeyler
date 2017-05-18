package com.cunoraz.eksiseyler.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.di.search.DaggerSearchComponent;
import com.cunoraz.eksiseyler.di.search.SearchModule;
import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.cunoraz.eksiseyler.ui.base.BaseActivity;
import com.cunoraz.eksiseyler.ui.content.PostAdapter;
import com.cunoraz.eksiseyler.ui.detail.DetailActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cuneytcarikci on 08/05/2017.
 */

public class SearchActivity extends BaseActivity implements SearchContract.View, PostAdapter.PostAdapterCallback {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.empty_list_text)
    TextView emptyListText;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    SearchPresenter mPresenter;

    PostAdapter mAdapter;

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);

        setToolbar();
        setupRecyclerView();

        mPresenter.onViewReady();
    }

    @Override
    public void clearList() {
        mAdapter.updatePosts(new ArrayList<Post>());
    }

    @Override
    public void loadPosts(ArrayList<Post> posts) {
        mAdapter.updatePosts(posts);
    }

    @Override
    public void openDetail(PostAdapter.PostViewHolder viewHolder, Post post) {

        Intent intent = DetailActivity.newIntent(SearchActivity.this, post, "Ekşi Şeyler'de Ara");
        startActivity(intent);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }

    private void setupRecyclerView() {

        mAdapter = new PostAdapter(SearchActivity.this, new ArrayList<Post>());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SearchActivity.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void resolveDaggerDependency() {

        DaggerSearchComponent.builder()
                .appComponent(getApplicationComponent())
                .searchModule(new SearchModule(this))
                .build().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_activity, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.ic_menu_search).getActionView();
        menu.findItem(R.id.ic_menu_search).expandActionView();
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.ic_menu_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {//todo rx kullaninca debounca yapilacak
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPresenter.onSearchTextChange(newText);
                return true;
            }
        });

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickPost(PostAdapter.PostViewHolder viewHolder, Post post) {
        mPresenter.onClickPost(viewHolder, post);
    }
}
