package com.example.googleauth;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FormActivity extends AppCompatActivity {

    private TextView PidText;
    public EditText TitleText;
    private EditText ContentText;
    private EditText InstaText;
    private EditText FbText;
    private EditText TwitterText;
   // private Button sendButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public Intent RecyclerViewIntent;
    public int Pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        PidText = (TextView) findViewById(R.id.Post_ID);
        TitleText = (EditText) findViewById(R.id.TitleEditText);
        ContentText = (EditText) findViewById(R.id.ContentEditText);
        InstaText = (EditText) findViewById(R.id.InstaEditText);
        FbText = (EditText) findViewById(R.id.FbEditText);
        TwitterText = (EditText)findViewById(R.id.twitterEditText);

        RecyclerViewIntent = new Intent(getApplicationContext(),RecyclerViewList.class);

       // sendButton= findViewById(R.id.Send);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();





        databaseReference.child("Pid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pid = Integer.parseInt(dataSnapshot.child("Count").getValue().toString())+1;
                setPid(Integer.parseInt(dataSnapshot.child("Count").getValue().toString())+1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void setPid(int Pid){

            PidText.setText("Post ID: " + Pid);

    }

    public  void OnClickSend(View view){
        if(TitleText.getText() != null && ContentText.getText() != null && InstaText.getText() != null && FbText.getText() != null && TwitterText.getText() != null) {
            databaseReference = databaseReference.child("Posts").child(String.valueOf(Pid));
            databaseReference.child("Title").setValue( TitleText.getText().toString());
            databaseReference.child("Content").setValue( ContentText.getText().toString());
            databaseReference.child("InstaLink").setValue( InstaText.getText().toString());
            databaseReference.child("FBLink").setValue( FbText.getText().toString());
            databaseReference.child("TwitterLink").setValue( TwitterText.getText().toString());
            databaseReference.getParent().getParent().child("Count").setValue(Pid);

            }
        else{
            Toast.makeText(FormActivity.this, "Fill all the Fields", Toast.LENGTH_SHORT).show();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Send Post");
        builder.setMessage("Do you want to post it ?");
        builder.setIcon(R.drawable.plus_f1);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(RecyclerViewIntent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();


        }
}


