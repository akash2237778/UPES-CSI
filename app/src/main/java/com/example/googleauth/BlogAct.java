package com.example.googleauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class BlogAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        WebView webView = findViewById(R.id.BlogWebview);
        webView.loadUrl("https://www.upescsi.in/blog/index.php");
    }
}
