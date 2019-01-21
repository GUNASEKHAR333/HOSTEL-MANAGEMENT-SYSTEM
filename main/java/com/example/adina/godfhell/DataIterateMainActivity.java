package com.example.adina.godfhell;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataIterateMainActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    DatabaseReference rootRef;
    DatabaseReference usersdRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_iterate_main);

        listView=(ListView)findViewById(R.id.list);

        rootRef = FirebaseDatabase.getInstance().getReference();
        usersdRef = rootRef.child("Users");
        dataModels = new ArrayList<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String name = ds.child("Name:").getValue(String.class);
                    String roll = ds.child("Rollno:").getValue(String.class);
                    String due  = ds.child("Due:").getValue(String.class);
                    String mobile = ds.child("Mobile:").getValue(String.class);

                    name = name.toUpperCase();roll = roll.toUpperCase();

                    dataModels.add(new DataModel(name,roll,due,mobile));

                }

                CustomAdapter adapter = new CustomAdapter(dataModels,getApplicationContext());

                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+"  Due: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent refresh = new Intent(this, DataIterateMainActivity.class);
            startActivity(refresh);
            this.finish();
            return true;
        }
        if (id == R.id.action_back){
            startActivity(new Intent(DataIterateMainActivity.this,Admin.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
