package com.example.googleauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class TeamAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        WebView webView = findViewById(R.id.TeamWebview);
        webView.loadUrl("https://www.upescsi.in/team.php");
    }
}
