package com.cunoraz.eksiseyler.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cunoraz.eksiseyler.R;
import com.cunoraz.eksiseyler.model.Post;

/**
 * Created by cuneytcarikci on 08/11/2016.
 * display post's detail
 */


public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();
    Post post;

    ImageView image;
    WebView webView;
    NestedScrollView nestedScrollView;

    FloatingActionButton actionButton;

    Toolbar toolbar;

    String categoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        post = getIntent().getExtras().getParcelable("extra_post");
        categoryName = getIntent().getExtras().getString("extra_category");
        image = (ImageView) findViewById(R.id.context_image);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(categoryName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Glide.with(DetailActivity.this)
                .load(post.getImg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(image);
        actionButton = (FloatingActionButton) findViewById(R.id.ic_action_share);
        webView = (WebView) findViewById(R.id.context_webview);
        webView.loadUrl(post.getUrl());
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

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, post.getUrl());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

    }

}
