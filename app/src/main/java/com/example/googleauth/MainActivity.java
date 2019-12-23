package com.example.googleauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import  com.facebook.login.LoginManager;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    Intent loginIntent;
    Intent navDrawIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.setApplicationId("1303933753107188");
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        loginIntent = new Intent(getApplicationContext(), fbLogin.class);
        navDrawIntent=new Intent(getApplicationContext(),FormActivity.class);



       // Toast.makeText(getApplicationContext(),AccessToken.getCurrentAccessToken()+"",Toast.LENGTH_SHORT).show();
        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
        }
        else
        {startActivity(navDrawIntent);}

    }



    public void goLoginScreen() {


            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginIntent);
        }


}
