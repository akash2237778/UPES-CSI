package com.example.googleauth;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterEvent extends AppCompatActivity {
    EditText Email;
    EditText passwd;
    EditText cnfrmPasswd;
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String name;
    String androidId;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        FirebaseUser oldUser = FirebaseAuth.getInstance().getCurrentUser();
        name = oldUser.getDisplayName();
        Email = findViewById(R.id.emailText);
        passwd = findViewById(R.id.passwdText);
        cnfrmPasswd = findViewById(R.id.confrmPass);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        Email.setFocusable(false);
        passwd.setFocusable(false);
        cnfrmPasswd.setFocusable(false);
        

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        mAuth = FirebaseAuth.getInstance();
        Log.i("uid", mAuth.getCurrentUser().getUid());
        androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.i("androidID :" , androidId);
        myRef.child("androidID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if(dataSnapshot.hasChild(androidId)){

                    dataSnapshot.child(androidId);
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            RegisterEvent.this);
                    builder.setTitle("Congrats !!");
                    builder.setMessage("Registered !!");

                    builder.setNeutralButton("CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }
                            });
                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            finish();
                        }
                    });
                    builder.show();
                }else{
                    Email.setFocusableInTouchMode(true);
                    passwd.setFocusableInTouchMode(true);
                    cnfrmPasswd.setFocusableInTouchMode(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegisterEvent.this, "Connect Internet !!", Toast.LENGTH_SHORT).show();
            }
        });


           // Log.d( "dff",secondary.getPersistenceKey() + " string" + mAuth);
    }
    public void submitOnClick(final View view){
        if( Email.getText().length() >8 &&  passwd.getText().length() >= 8 && cnfrmPasswd.getText().length() == passwd.getText().length()){
            Log.i("email" , Email.getText().toString());
            Log.i("passwd" , passwd.getText().toString()  +"   :   " + cnfrmPasswd.getText().toString());
            if(cnfrmPasswd.getText().toString().equals(passwd.getText().toString()) ){
                Log.i("eml" , Email.getText().toString());
        mAuth.createUserWithEmailAndPassword(Email.getText().toString(), passwd.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            myRef.child("Players").child(mAuth.getCurrentUser().getUid()).child("name").setValue(name);
                            myRef.child("Players").child(mAuth.getCurrentUser().getUid()).child("canPlay").setValue(false);
                            myRef.child("Players").child(mAuth.getCurrentUser().getUid()).child("score").setValue(0);
                            myRef.child("androidID").child(androidId).setValue(mAuth.getCurrentUser().getUid());




                           /* LayoutInflater inflater = (LayoutInflater)
                                    getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = inflater.inflate(R.layout.pop_up, null);

                            // create the popup window
                            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                            boolean focusable = true; // lets taps outside the popup also dismiss it
                            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                            // show the popup window
                            // which view you pass in doesn't matter, it is only used for the window tolken
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                            // dismiss the popup window when touched
                            popupView.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    popupWindow.dismiss();
                                    return true;
                                }
                            });*/




                          //  FirebaseUser user = mAuth.getCurrentUser();
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterEvent.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }else{                Toast.makeText(RegisterEvent.this, "Passwords are not same !!", Toast.LENGTH_SHORT).show();            }
        }
        else{
            Toast.makeText(RegisterEvent.this, "Incorrect Details", Toast.LENGTH_SHORT).show();
        }
    }
}
