package com.example.googleauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class contactAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        WebView webView = findViewById(R.id.ContactWebview);
        webView.loadUrl("https://www.upescsi.in/contact.php");
    }
}
