package com.diu.helpbdadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.diu.helpbdadmin.Adapter.NIDListAdapter;
import com.diu.helpbdadmin.Model.ModelNID;
import com.diu.helpbdadmin.Model.ModelUser;
import com.diu.helpbdadmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private  Menu menu;

    private Button applicationList,nidList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SplashActivity.splash.finish();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Help for Poor People of Bangladesh");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        if (firebaseUser==null){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        applicationList=findViewById(R.id.applicationList);
        nidList=findViewById(R.id.nidList);

        applicationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ApplicationListActivity.class);
                startActivity(intent);
            }
        });

        nidList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,NIDListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        this.menu= menu;
        updateMenuTitles();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        switch (id){
            case R.id.itemLogOut:
                firebaseAuth.signOut();
                Intent intent3= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent3);
                finish();
                break;
        }

        return true;
    }


    private void updateMenuTitles() {
        final MenuItem usernameMenu = menu.findItem(R.id.itemLogOut);

        Query query = FirebaseDatabase.getInstance().getReference().child("users");
        query.orderByKey().equalTo(firebaseAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ModelUser modelUser=dataSnapshot.getValue(ModelUser.class);
                usernameMenu.setTitle("Log Out ("+modelUser.getUsername()+")");

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}