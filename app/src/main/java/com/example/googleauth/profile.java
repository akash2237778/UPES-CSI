package com.example.googleauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile extends AppCompatActivity {

    FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    TextView NameTxtV;
    TextView EmailTxtv;
    TextView PhoneTxtv;
    TextView Txtv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user=FirebaseAuth.getInstance().getCurrentUser();

        NameTxtV = (TextView)findViewById(R.id.txtName);
        EmailTxtv = (TextView)findViewById(R.id.textEmail);
        PhoneTxtv = (TextView)findViewById(R.id.txtPhone);
        Txtv = (TextView)findViewById(R.id.textView);


        NameTxtV.setText(user.getDisplayName());
        EmailTxtv.setText(user.getEmail());
       // PhoneTxtv.setText("Phone Number" + user.getPhoneNumber());
        Txtv.setText("SetText");

    }
}
