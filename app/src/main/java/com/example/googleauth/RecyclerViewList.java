package com.example.googleauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecyclerViewList extends AppCompatActivity {

    private RecyclerView recycler_View;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Intent PostIntent;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String[] Pid = {"","","","",""};
    String[] Title = {"","","","",""};
    String[] Content = {"","","","",""};
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_list);

        PostIntent = new Intent(getApplicationContext(),PostDisplay.class);

        recycler_View = (RecyclerView) findViewById(R.id.my_recycler_view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler_View.setHasFixedSize(true);
        //mAdapter = new MyAdapter(Pid, Title,Content);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recycler_View.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = 0;
                Iterable<DataSnapshot> iterablesDataSnapshots = dataSnapshot.getChildren();
                    for(DataSnapshot child : iterablesDataSnapshots){
                        Pid[count] = child.getKey();
                        Title[count] = dataSnapshot.child(child.getKey()).child("Title").getValue().toString();
                        Content[count] = dataSnapshot.child(child.getKey()).child("Content").getValue().toString();
                        count++;
//                        Log.i("Pid",Pid[count]);
                    }
                recycler_View.setAdapter(new MyAdapter(Pid,Title,Content));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FormIntent = new Intent(getApplicationContext(),FormActivity.class);
                startActivity(FormIntent);
            }
        });



        recycler_View.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recycler_View, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(Pid[position] != null){
                PostIntent.putExtra("Pid",Pid[position]);
                startActivity(PostIntent);
        }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
}
