package com.example.googleauth;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    TextView NameTxtV;
    TextView EmailTxtv;
    TextView PhoneTxtv;
    ImageView UserImage;
    Button EditButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        user=FirebaseAuth.getInstance().getCurrentUser();

        NameTxtV = (TextView)findViewById(R.id.txtName);
        EmailTxtv = (TextView)findViewById(R.id.textEmail);
        PhoneTxtv = (TextView)findViewById(R.id.txtPhone);
        UserImage = (ImageView)findViewById(R.id.UserImage);


        NameTxtV.setText(user.getDisplayName());
        EmailTxtv.setText(user.getEmail());

        UserImage.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);

        new ImageLoadTask(user.getPhotoUrl().toString(), UserImage).execute();

        EditButton = (Button) findViewById(R.id.EditProfile);
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Feature not available yet", Snackbar.LENGTH_LONG);
            }
        });


         databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PhoneTxtv.setText(dataSnapshot.child("Phone Number").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
