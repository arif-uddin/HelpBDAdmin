package com.diu.helpbdadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.diu.helpbdadmin.Model.ModelNID;
import com.diu.helpbdadmin.Model.ModelUser;
import com.diu.helpbdadmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ApplicationStatusActivity extends AppCompatActivity {

    TextView nIdNo,name,fatherName,motherName,birthDate,account,balance,union,upazila,district,gotFund,status;
    TextView nIdNo1,name1,fatherName1,motherName1,birthDate1,account1,balance1,union1,upazila1,district1,gotFund1,applied1;
    Button check,confirm,remove,paid;
    CardView cardView1;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_status);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Application Details");

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
        status=findViewById(R.id.status);

        nIdNo1=findViewById(R.id.nIdNo1);
        name1=findViewById(R.id.name1);
        fatherName1=findViewById(R.id.fatherName1);
        motherName1=findViewById(R.id.motherName1);
        birthDate1=findViewById(R.id.birthDate1);
        account1=findViewById(R.id.account1);
        balance1=findViewById(R.id.balance1);
        union1=findViewById(R.id.union1);
        upazila1=findViewById(R.id.upazila1);
        district1=findViewById(R.id.district1);
        gotFund1=findViewById(R.id.gotFund1);
        applied1=findViewById(R.id.applied1);
        cardView1=findViewById(R.id.cardView1);

        check=findViewById(R.id.check);
        confirm=findViewById(R.id.confirm);
        remove=findViewById(R.id.remove);
        paid=findViewById(R.id.paid);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

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
        status.setText("Status: "+getIntent().getExtras().getString("status"));

        if(getIntent().getExtras().getString("status").toLowerCase().equals("confirmed")){
            remove.setVisibility(View.GONE);
        }

        if(getIntent().getExtras().getString("fund").toLowerCase().contains("yes")){
            check.setVisibility(View.GONE);
        }

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkApplication();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmApplication();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryForRemove();
            }
        });

        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryForPaid();
            }
        });
    }


    private void checkApplication() {

        if(getIntent().getExtras().getString("status").toLowerCase().equals("confirmed")){
            confirm.setVisibility(View.GONE);
        }

        Query query = FirebaseDatabase.getInstance().getReference().child("nids");
        query.orderByKey().equalTo(getIntent().getExtras().getString("nidNo")).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                cardView1.setVisibility(View.VISIBLE);
                check.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);

                ModelNID modelNID=dataSnapshot.getValue(ModelNID.class);
                nIdNo1.setText("NID No: "+modelNID.getNidNo());
                name1.setText("Name: "+modelNID.getName());
                fatherName1.setText("Father's Name: "+modelNID.getFatherName());
                motherName1.setText("Mother's Name: "+modelNID.getMotherName());
                birthDate1.setText("Date of Birth: "+modelNID.getBirthDate());
                account1.setText("Date of Birth: "+modelNID.getBankAccount());
                balance1.setText("Total Balance: "+modelNID.getBalance());
                union1.setText("Union: "+modelNID.getUnion());
                upazila1.setText("Upazila: "+modelNID.getUpazila());
                district1.setText("District: "+modelNID.getDistrict());
                gotFund1.setText("Got Fund: "+modelNID.getGotFund());
                applied1.setText("Applied: "+modelNID.getApplied());
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

    private void confirmApplication() {

        Query query = FirebaseDatabase.getInstance().getReference().child("applications");
        query.orderByKey().equalTo(getIntent().getExtras().getString("id")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("applications");
                databaseReference.child(getIntent().getExtras().getString("id")).
                        child("status").setValue("Confirmed");

                Query query = FirebaseDatabase.getInstance().getReference().child("nids");
                query.orderByKey().equalTo(getIntent().getExtras().getString("nidNo")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("nids");
                        databaseReference.child(getIntent().getExtras().getString("nidNo")).
                                child("applied").setValue("yes");

                        Toast.makeText(ApplicationStatusActivity.this,"Confirmed",Toast.LENGTH_SHORT).show();
                        ApplicationListActivity.applicationListActivity.finish();
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void QueryForRemove() {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("applications");
        databaseReference.child(getIntent().getExtras().getString("id")).removeValue();
        Toast.makeText(ApplicationStatusActivity.this,"Removed",Toast.LENGTH_SHORT).show();
        ApplicationListActivity.applicationListActivity.finish();
        finish();
    }

    private void QueryForPaid() {
        Query query = FirebaseDatabase.getInstance().getReference().child("applications");
        query.orderByKey().equalTo(getIntent().getExtras().getString("id")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("applications");
                databaseReference.child(getIntent().getExtras().getString("id")).
                        child("gotFund").setValue("yes");

                Query query = FirebaseDatabase.getInstance().getReference().child("nids");
                query.orderByKey().equalTo(getIntent().getExtras().getString("nidNo")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("nids");
                        databaseReference.child(getIntent().getExtras().getString("nidNo")).
                                child("gotFund").setValue("yes");

                        Toast.makeText(ApplicationStatusActivity.this,"Done",Toast.LENGTH_SHORT).show();
                        ApplicationListActivity.applicationListActivity.finish();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}