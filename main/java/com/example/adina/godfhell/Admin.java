package com.example.adina.godfhell;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin extends AppCompatActivity {

    private Button scanButton;
    private Button logoutAdmin;
    private Button adminUserList;
    private Button searchByRollBtn;
    private Button changePriceBtn;

    private TextView totalSumView;
    private TextView dueView;
    private FirebaseDatabase mdatabase;

    private DatabaseReference ref;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        scanButton = (Button)findViewById(R.id.btn_scan);
        logoutAdmin = (Button)findViewById(R.id.admin_logout);
        ref = mdatabase.getInstance().getReference().child("Caterer").child("Sum:");

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this,MainBarcodeActivity.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();

        logoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this,LoginActivity.class));
                auth.signOut();
            }
        });

        adminUserList = (Button)findViewById(R.id.user_list);

        adminUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this,DataIterateMainActivity.class));

            }
        });

        searchByRollBtn = (Button)findViewById(R.id.search_by_user);

        searchByRollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this,ParticularUser.class));
            }
        });

        changePriceBtn = (Button)findViewById(R.id.change_price);

        changePriceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this,change_price.class));
            }
        });

        dueView = (TextView)findViewById(R.id.admin_due_view);
        totalSumView = (TextView)findViewById(R.id.admin_total_view);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String totalSum = dataSnapshot.getValue(String.class);
                totalSumView.setText(totalSum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);




    }

}

