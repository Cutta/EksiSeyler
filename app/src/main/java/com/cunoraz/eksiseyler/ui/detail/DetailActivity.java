package com.cunoraz.eksiseyler.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaderFactory;
import com.bumptech.glide.load.model.LazyHeaders;
import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.di.detail.DaggerDetailComponent;
import com.cunoraz.eksiseyler.di.detail.DetailModule;
import com.cunoraz.eksiseyler.model.rest.entity.Post;
import com.cunoraz.eksiseyler.ui.base.BaseActivity;
import com.cunoraz.eksiseyler.util.DialogBuilder;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cuneytcarikci on 08/11/2016.
 * display post's detail
 */

public class DetailActivity extends BaseActivity implements DetailContract.View {

    public static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_POST = "extra_post";
    private static final String EXTRA_CHANNEL = "extra_channel";

    @BindView(R.id.root_layout)
    CoordinatorLayout rootLayout;

    @BindView(R.id.context_image)
    ImageView image;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @BindView(R.id.context_webview)
    WebView webView;

    @BindView(R.id.ic_action_share)
    FloatingActionButton actionButton;

    @Inject
    DetailPresenter mPresenter;

    private Post mPost;
    private String mChannel;
    private Menu mMenu;

    public static Intent newIntent(Context context, Post post, String channel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_POST, post);
        bundle.putString(EXTRA_CHANNEL, channel);

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);
        setupToolbar();
        setupWebView();
        setupFloatingActionButton();
        mPresenter.onViewReady();
    }

    @Override
    protected void resolveDaggerDependency() {
        getExtras();
        DaggerDetailComponent.builder()
                .appComponent(getApplicationComponent())
                .detailModule(new DetailModule(this, mPost, mChannel))
                .build().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_activity, menu);
        mMenu = menu;
        updateFavouriteMenuItem(mPresenter.isOneOfFavouritePosts());
        updateSavingModeMenuItem(mPresenter.isSavingModeActive());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.ic_menu_show_image:
                mPresenter.onClickSavingModeMenuItem();
                return true;

            case R.id.ic_menu_save_content:
                mPresenter.onClickAddRemoveFavourite();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            if (extras.containsKey(EXTRA_URL)) {

                mPost = new Post();
                mPost.setImg("");
                mPost.setName("Ekşi Şeyler");
                mPost.setUrl(extras.getString(EXTRA_URL));
                mChannel = "Ekşi Şeyler";//URL den gelince yazı başlığı olmadıgı için bunu yazsın dedik

            } else if (extras.containsKey(EXTRA_POST) &&
                    extras.containsKey(EXTRA_CHANNEL)) {

                mPost = extras.getParcelable(EXTRA_POST);
                mChannel = extras.getString(EXTRA_CHANNEL);

            }

        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void setupFloatingActionButton() {//// TODO: 03/05/2017 direk kendi cagirabilmeli mi?
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mPost.getUrl());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    private void setupWebView() {
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                nestedScrollView.scrollTo(0, 0);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:document.getElementById(\"header\").innerHTML = \"\";void(0);");
                view.loadUrl("javascript:document.getElementById(\"header-nav\").innerHTML = \"\";void(0);");
                view.loadUrl("javascript:document.getElementById(\"mobil-sticky-share-topbar\").innerHTML = \"\";void(0);");

            }
        });
    }

    //region View Methods

    @Override
    public void updateWebViewLoadImage(boolean isSavingModeActive) {
        webView.getSettings().setLoadsImagesAutomatically(!isSavingModeActive);
    }

    @Override
    public void updateToolbarTitle(String title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }

    @Override
    public void loadWebView(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void loadHeaderImage(String url) {
        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("referer", new LazyHeaderFactory() {
                    @Override
                    public String buildHeader() {
                        return "https://seyler.eksisozluk.com/";
                    }
                })
                .build());

        Glide.with(DetailActivity.this)
                .load(glideUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(image);
    }

    @Override
    public void showSavingModeActiveDialog() {
        DialogBuilder.infoDialog(DetailActivity.this,
                R.string.dialog_title_saving_mode_active,
                R.string.dialog_message_saving_mode_active)
                .show();
    }

    @Override
    public void showSavingModeNotActiveDialog() {
        DialogBuilder.infoDialog(DetailActivity.this,
                R.string.dialog_title_saving_mode_passive,
                R.string.dialog_message_saving_mode_passive)
                .show();
    }


    @Override
    public void showAddedToFavourites() {
        Snackbar.make(rootLayout, "Favorilere eklendi.", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showRemovedFromFavourites() {
        Snackbar.make(rootLayout, "Favorilerden silindi!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void updateSavingModeMenuItem(boolean isActive) {
        if (isActive)
            mMenu.getItem(0).setIcon(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_photo_gray_24dp));
        else
            mMenu.getItem(0).setIcon(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_photo_white_24dp));
    }

    @Override
    public void updateFavouriteMenuItem(boolean isOneOfFavouritePosts) {
        if (isOneOfFavouritePosts)
            mMenu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_bookmarked));
        else
            mMenu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_non_bookmarked));
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackbarMessage(String message) {
        Snackbar.make(rootLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        finish();
    }


}