package com.cunoraz.eksiseyler.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.cunoraz.eksiseyler.app.MyApplication;
import com.cunoraz.eksiseyler.utility.AppSettings;
import com.cunoraz.eksiseyler.model.Post;

/**
 * Created by cuneytcarikci on 08/11/2016.
 * display post's detail
 */


public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();
    Post post;

    AppSettings manager;
    ImageView image;
    WebView webView;
    NestedScrollView nestedScrollView;
    FloatingActionButton actionButton;
    CoordinatorLayout rootLayout;
    Toolbar toolbar;
    String categoryName;
    String fromIntentUrl;

    boolean showImage = true;
    private Menu menu;

    private boolean isBookmarked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        manager = ((MyApplication) getApplication()).getSharedPrefManager();

        webView = (WebView) findViewById(R.id.context_webview);
        image = (ImageView) findViewById(R.id.context_image);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        rootLayout = (CoordinatorLayout) findViewById(R.id.root_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        actionButton = (FloatingActionButton) findViewById(R.id.ic_action_share);
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("extra_url")) {
            fromIntentUrl = getIntent().getExtras().getString("extra_url");
            post = new Post();
            post.setName("Ekşi Şeyler");
            post.setImg("");
            post.setUrl(fromIntentUrl);
            webView.loadUrl(fromIntentUrl);
        } else {
            post = getIntent().getExtras().getParcelable("extra_post");
            categoryName = getIntent().getExtras().getString("extra_category");
            toolbar.setTitle(categoryName);
            GlideUrl glideUrl = new GlideUrl(post.getImg(), new LazyHeaders.Builder()
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

            webView.loadUrl(post.getUrl());

        }

        showImage = manager.getBoolean(MainActivity.IMAGE_SHOW);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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
        webView.getSettings().setLoadsImagesAutomatically(showImage);
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

        isBookmarked = manager.isBookmarked(post);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_activity, menu);

        this.menu = menu;
        if (showImage)
            menu.getItem(0).setIcon(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_photo_white_24dp));
        else
            menu.getItem(0).setIcon(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_photo_gray_24dp));

        if (isBookmarked)
            menu.getItem(1).setIcon(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_bookmarked));
        else
            menu.getItem(1).setIcon(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_non_bookmarked));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.ic_menu_show_image:
                if (showImage) {
                    AlertDialog dialog = new AlertDialog.Builder(DetailActivity.this)
                            .setTitle("\u2713 " + "Tasarruf Modu Etkin")
                            .setMessage("Bundan sonraki içerik görüntülemenizde, resimler yüklenmez ve data paketinizden tasarruf edersiniz.")
                            .setPositiveButton("Tamam", null)
                            .create();
                    dialog.show();
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_photo_gray_24dp));
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(DetailActivity.this)
                            .setTitle("Tasarruf Modu Devredışı")
                            .setMessage("Bundan sonraki içerik görüntülemenizde, resimleri görebilirsiniz.")
                            .setPositiveButton("Tamam", null)
                            .create();
                    dialog.show();
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_photo_white_24dp));

                }
                manager.saveBoolean(MainActivity.IMAGE_SHOW, !showImage);
                webView.getSettings().setLoadsImagesAutomatically(!showImage);
                showImage = !showImage;
                return true;
            case R.id.ic_menu_save_content:
              /*  webView.saveWebArchive(getFilesDir() + File.separator + post.getUrl() + ".xml");*/
                isBookmarked = !isBookmarked;
                if (!isBookmarked) {
                    manager.deletePost(post);
                    Snackbar.make(rootLayout, "Favorilerden silindi!", Snackbar.LENGTH_SHORT).show();
                    menu.getItem(1).setIcon(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_non_bookmarked));
                } else {
                    if (post.getImg() != null && !post.getImg().equals("")) {
                        manager.savePost(post);
                        Snackbar.make(rootLayout, "Favorilere eklendi.", Snackbar.LENGTH_SHORT).show();
                        menu.getItem(1).setIcon(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_bookmarked));
                    } else {
                        Toast.makeText(this, "Favorilere eklenemedi, lütfen daha sonra tekrar deneyin!", Toast.LENGTH_SHORT).show();
                    }
                }
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
}
