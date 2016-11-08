package com.cunoraz.eksiseyler.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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

    FloatingActionButton actionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        post = getIntent().getExtras().getParcelable("extra_post");
        image = (ImageView) findViewById(R.id.context_image);
        webView = (WebView) findViewById(R.id.context_webview);
        actionButton = (FloatingActionButton) findViewById(R.id.ic_action_share);

        Glide.with(DetailActivity.this).load(post.getImg()).into(image);
        Log.d(TAG, "onCreateee: " + post.getUrl());
        webView.loadUrl(post.getUrl());

        webView.setWebViewClient(new WebViewController());

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
            }
        });

        Log.d(TAG, "onCreate: " + post.getName());
    }
    public class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
