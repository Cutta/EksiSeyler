package com.cunoraz.eksiseyler.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.StringRes;
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
import android.webkit.JavascriptInterface;
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
import com.cunoraz.eksiseyler.util.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

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
    private String mPageSource;

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
                .detailModule(new DetailModule(this, mPost, mChannel, mPageSource))
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
                mPageSource = "";

            } else if (extras.containsKey(EXTRA_POST) &&
                    extras.containsKey(EXTRA_CHANNEL)) {

                mPost = extras.getParcelable(EXTRA_POST);
                mChannel = extras.getString(EXTRA_CHANNEL);
                mPageSource = getContentFromInternalStorage();

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

    private void setupFloatingActionButton() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    private void setupWebView() {

        class MyJavaScriptInterface {
            @JavascriptInterface
            @SuppressWarnings("unused")
            public void processHTML(String html) {
                mPageSource = html;
            }
        }
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
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
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

                view.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");

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
                .diskCacheStrategy(DiskCacheStrategy.ALL)
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
        Snackbar.make(rootLayout, R.string.favourite_adding_success_text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showRemovedFromFavourites() {
        Snackbar.make(rootLayout, R.string.favourite_removing_success_text, Snackbar.LENGTH_SHORT).show();
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
    public void showToastMessage(@StringRes int message) {
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

    @Override
    public boolean isConnect() {
        return Utils.isConnected(DetailActivity.this);
    }

    @Override
    public boolean saveToInternalStorage() {
        try {
            Element doc = Jsoup.parse((mPageSource));
            Element link = doc.getElementsByClass("content-detail-inner").first();
            String contentMain = link.outerHtml();

            String fileName = Utils.getEncodedPostName(mPost.getUrl()) + ".html";
            File cacheFolder = getDir("cache", Context.MODE_PRIVATE);
            cacheFolder.mkdirs();
            File cacheFile = new File(cacheFolder, fileName);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(cacheFile), Charset.forName("UTF-8"));
            outputStreamWriter.write(contentMain);
            outputStreamWriter.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.favourite_not_loaded_content_warning_text, Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void loadWebViewWithTemplate(String contentHtml) {
        Toast.makeText(this, R.string.not_connected_content_warning, Toast.LENGTH_LONG).show();
        String htmlString;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("detail_template.html"), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while ((htmlString = reader.readLine()) != null) {
                sb.append(htmlString).append("\n");
            }
            reader.close();
            htmlString = sb.toString();
            htmlString = htmlString.replace("--CONTENT--", contentHtml);

        } catch (Exception e) {
            e.printStackTrace();
            htmlString = "";
        }

        webView.loadDataWithBaseURL("file:///android_asset/", htmlString, "text/html", "utf-8", null);
        //  webView.loadData(contentHtml, "text/html", "UTF-8");
    }

    public String getContentFromInternalStorage() {

        String html = null;

        try {

            File cacheFolder = getDir("cache", Context.MODE_PRIVATE);
            File cacheFile = new File(cacheFolder, Utils.getEncodedPostName(mPost.getUrl()) + ".html");

            FileInputStream fileInputStream = new FileInputStream(cacheFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString;
            StringBuilder stringBuilder = new StringBuilder();

            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString);
            }
            fileInputStream.close();
            html = stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return html;

    }

}