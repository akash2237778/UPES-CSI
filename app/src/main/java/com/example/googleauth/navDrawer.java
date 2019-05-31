package com.example.googleauth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class navDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
Intent intent;
TextView tname;
TextView temail;
ImageView UserImage;
FirebaseUser user;
NavigationView navigationView;
View headerView;
Intent ProfileIntent;
Intent AboutIntent;
Intent HomeIntent;
Intent BlogIntent;
Intent TeamIntent;
Intent ContactIntent;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListner;
    private ViewPager mPager;
    private int[] layouts = {R.layout.home_first_slide,R.layout.home_second_slide,R.layout.home_third_slide};
    private MpagerAdapter mpagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        setTitle("UPES-CSI");


        mPager = (ViewPager) findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);

        navigationView=(NavigationView)findViewById(R.id.nav_view);
        headerView=navigationView.getHeaderView(0);

        user=FirebaseAuth.getInstance().getCurrentUser();

        ProfileIntent = new Intent(getApplicationContext() , profile.class);
        AboutIntent = new Intent(getApplicationContext() , aboutAct.class);
        HomeIntent = new Intent(getApplicationContext(),navDrawer.class);
        BlogIntent = new Intent(getApplicationContext(),BlogAct.class);
        TeamIntent = new Intent(getApplicationContext(),TeamAct.class);
        ContactIntent = new Intent(getApplicationContext(),contactAct.class);

        tname=(TextView) headerView.findViewById(R.id.txtname);
        tname.setText(user.getDisplayName()+"");
        temail=(TextView) headerView.findViewById(R.id.txtemail);
        temail.setText(user.getEmail()+"");
        UserImage = (ImageView)headerView.findViewById(R.id.usrImage);

        new ImageLoadTask(user.getPhotoUrl().toString(), UserImage).execute();

        Toast.makeText(getApplicationContext(), "Logged in as :" + user.getDisplayName(),Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = new Intent(getApplicationContext(), fbLogin.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            new AlertDialog.Builder(this).setTitle("Exit?").setMessage("Are you sure you want to exit?").setNegativeButton(android.R.string.no,null).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //fbLogin.super.onBackPressed();

                    finishAffinity();

                }
            }).create().show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(ProfileIntent);
        } else if (id == R.id.nav_home) {
            startActivity(HomeIntent);
        } else if (id == R.id.nav_about) {
            startActivity(AboutIntent);
        } else if (id == R.id.nav_blog) {
            startActivity(BlogIntent);
        } else if (id == R.id.nav_team) {
            startActivity(TeamIntent);
        }else if (id == R.id.nav_contact) {
            startActivity(ContactIntent);
        }else if (id == R.id.nav_share) {

        }
        else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
             startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
