package com.diu.helpbdadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.diu.helpbdadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class NIDDetailActivity extends AppCompatActivity {

    TextView nIdNo,name,fatherName,motherName,birthDate,account,balance,union,upazila,district,gotFund,applied;
    Button paid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_i_d_detail);
        Objects.requireNonNull(getSupportActionBar()).setTitle("NID Details");

        nIdNo=findViewById(R.id.nIdNo);
        name=findViewById(R.id.name);
        fatherName=findViewById(R.id.fatherName);
        motherName=findViewById(R.id.motherName);
        birthDate=findViewById(R.id.birthDate);
        account=findViewById(R.id.account);
        balance=findViewById(R.id.balance);
        union=findViewById(R.id.union);
        upazila=findViewById(R.id.upazila);
        district=findViewById(R.id.district);
        gotFund=findViewById(R.id.gotFund);
        applied=findViewById(R.id.applied);
        paid=findViewById(R.id.paid);


        nIdNo.setText("NID No: "+getIntent().getExtras().getString("nidNo"));
        name.setText("Name: "+getIntent().getExtras().getString("name"));
        fatherName.setText("Father's Name: "+getIntent().getExtras().getString("fatherName"));
        motherName.setText("Mother's Name: "+getIntent().getExtras().getString("motherName"));
        birthDate.setText("Date of Birth: "+getIntent().getExtras().getString("birthDate"));
        account.setText("Bank Account: "+getIntent().getExtras().getString("account"));
        balance.setText("Total Balance: "+getIntent().getExtras().getString("balance"));
        union.setText("Union: "+getIntent().getExtras().getString("union"));
        upazila.setText("Upazila: "+getIntent().getExtras().getString("upazila"));
        district.setText("District: "+getIntent().getExtras().getString("district"));
        gotFund.setText("Got Fund: "+getIntent().getExtras().getString("fund"));
        applied.setText("Applied: "+getIntent().getExtras().getString("applied"));

        if(getIntent().getExtras().getString("fund").toLowerCase().contains("yes")){
            paid.setVisibility(View.GONE);
        }

        if(getIntent().getExtras().getString("applied").toLowerCase().contains("yes")){
            paid.setVisibility(View.GONE);
        }

        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryForPaid();
            }
        });

    }

    private void QueryForPaid() {

        Query query = FirebaseDatabase.getInstance().getReference().child("nids");
        query.orderByKey().equalTo(getIntent().getExtras().getString("nidNo")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("nids");
                databaseReference.child(getIntent().getExtras().getString("nidNo")).
                        child("gotFund").setValue("yes");
                databaseReference.child(getIntent().getExtras().getString("nidNo")).
                        child("applied").setValue("Not applied. Manually Done!");

                Toast.makeText(NIDDetailActivity.this,"Done",Toast.LENGTH_SHORT).show();
                NIDListActivity.nidListActivity.finish();
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}