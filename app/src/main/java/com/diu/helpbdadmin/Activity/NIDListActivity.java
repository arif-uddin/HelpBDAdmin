package com.diu.helpbdadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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
import java.util.List;
import java.util.Objects;

public class NIDListActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    RecyclerView recyclerView;
    NIDListAdapter nidListAdapter;

    public List<ModelNID> nids;

    private String union;

    static NIDListActivity nidListActivity;
    private  Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_i_d_list);
        Objects.requireNonNull(getSupportActionBar()).setTitle("NID List");
        nidListActivity=this;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView=findViewById(R.id.recyclerView);
        nids = new ArrayList<>();

        LinearLayoutManager linearVertical = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearVertical);
        nidListAdapter = new NIDListAdapter(getApplicationContext(),nids);
        recyclerView.setAdapter(nidListAdapter);

        Query query = FirebaseDatabase.getInstance().getReference().child("users");
        query.orderByKey().equalTo(firebaseAuth.getCurrentUser().getUid()).addChildEventListener(new QueryForUserInfo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_nid_list,menu);
        this.menu= menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        switch (id){
            case R.id.action_search:
                item=menu.findItem(R.id.action_search);
                SearchView searchView= (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        nidListAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

        }
        return true;
    }

    private class QueryForUserInfo implements ChildEventListener{

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            ModelUser modelUser=dataSnapshot.getValue(ModelUser.class);
            union=modelUser.getUnion();

            Query query = FirebaseDatabase.getInstance().getReference().child("nids");
            query.addChildEventListener(new QueryForNIDList());
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
    }

    private class QueryForNIDList implements ChildEventListener {

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            final ModelNID modelNID = dataSnapshot.getValue(ModelNID.class);

            if ( modelNID.getUnion().toLowerCase().contains(union.toLowerCase()) || modelNID.getDistrict().toLowerCase().contains(union.toLowerCase()) ){
                nidListAdapter.setValues(modelNID);
            }
            nidListAdapter.notifyDataSetChanged();
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
    }
}