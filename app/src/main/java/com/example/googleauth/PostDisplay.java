package com.example.googleauth;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.View;

public class PostDisplay extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView PidView;
    TextView TitleView;
    TextView ContentView;
    ImageView InstaImage;
    ImageView FBImage;
    ImageView TwitterImage;
    ImageView WhatsappImage;
    String Pid;
    String InstaUrl;
    String FBUrl;
    String TwitterUrl;
    String WhatsappUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_display);

        PidView = findViewById(R.id.PidtextView);
        TitleView = findViewById(R.id.TitletextView);
        ContentView = findViewById(R.id.ContentTextView);
        InstaImage = findViewById(R.id.InstaImage);
        FBImage = findViewById(R.id.FBImage);
        TwitterImage = findViewById(R.id.TwitterImage);
        WhatsappImage = findViewById(R.id.whatsappImg);



        Pid = getIntent().getStringExtra("Pid");

        firebaseDatabase = FirebaseDatabase.getInstance();
        if(Pid != null)
            databaseReference = firebaseDatabase.getReference("Posts").child(Pid);

        PidView.setText("Post ID : " + Pid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TitleView.setText(dataSnapshot.child("Title").getValue().toString());
                ContentView.setText(dataSnapshot.child("Content").getValue().toString());
                InstaUrl = dataSnapshot.child("InstaLink").getValue().toString();
                FBUrl = dataSnapshot.child("FBLink").getValue().toString();
                TwitterUrl = dataSnapshot.child("TwitterLink").getValue().toString();
                WhatsappUrl = "whatsapp://send?text=*"+dataSnapshot.child("Title").getValue().toString()  +"*" + "\n"+dataSnapshot.child("Content").getValue().toString()+ "\n"+ "\n"+ "Facebook :" + FBUrl+  "\n"+ "Insta :" + InstaUrl+ "\n"+ "Twitter :" + TwitterUrl;



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        InstaImage.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(InstaUrl));
                startActivity(i);
            }
        });
        FBImage.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(FBUrl));
                startActivity(i);
            }
        });
        TwitterImage.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(TwitterUrl));
                startActivity(i);
            }
        });
        WhatsappImage.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(WhatsappUrl));
                startActivity(i);
            }
        });
       // Log.i("log" , databas);


       // Log.i("PID" , Pid);
    }
    public void send(String content){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
        i.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(i, "Share URL"));
    }
}
