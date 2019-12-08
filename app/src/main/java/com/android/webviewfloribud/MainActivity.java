package com.android.webviewfloribud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String LINK_WEBSITE = "http://floribud.blogspot.com/";
    private WebView mBloggerWebview;
    private ProgressBar mLoadingProgress;
    private ImageView mRefreshImageView;
    private ConstraintLayout mEmptyConnectionTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBloggerWebview = findViewById(R.id.webView_blogger);
        mLoadingProgress = findViewById(R.id.progress_loading);
        mEmptyConnectionTextview = findViewById(R.id.empty_view);
        mRefreshImageView = findViewById(R.id.imageview_refresh);

        mShowWebsite();
        mButtonClicked();
    }

    private void mShowWebsite() {
        if (mCheckInternet() == true) {
            mLoadingProgress.setVisibility(View.GONE);
            mEmptyConnectionTextview.setVisibility(View.GONE);
            mBloggerWebview.setVisibility(View.VISIBLE);
            mInitialWebsite();
        } else {
            mLoadingProgress.setVisibility(View.GONE);
            mEmptyConnectionTextview.setVisibility(View.VISIBLE);
            mBloggerWebview.setVisibility(View.GONE);
        }
    }

    private void mButtonClicked() {
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoadingProgress.setVisibility(View.VISIBLE);
                mShowWebsite();
            }
        });
    }

    private void mInitialWebsite() {
        mBloggerWebview.getSettings().setLoadsImagesAutomatically(true);
        mBloggerWebview.getSettings().setJavaScriptEnabled(true);
        mBloggerWebview.getSettings().setDomStorageEnabled(true);

        mBloggerWebview.getSettings().setSupportZoom(true);
        mBloggerWebview.getSettings().setBuiltInZoomControls(true);
        mBloggerWebview.getSettings().setDisplayZoomControls(false);

        mBloggerWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mBloggerWebview.setWebViewClient(new WebViewClient());
        mBloggerWebview.loadUrl(LINK_WEBSITE);
    }

    private boolean mCheckInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else
            return false;
    }

    @Override
    public void onBackPressed() {
        if (mBloggerWebview.canGoBack()) {
            mBloggerWebview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
