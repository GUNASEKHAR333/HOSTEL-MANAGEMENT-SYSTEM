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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class UserListActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    DatabaseReference rootRef;
    DatabaseReference usersdRef;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        auth = FirebaseAuth.getInstance();
        user = getInstance().getCurrentUser();
        final String userid = user.getEmail();
        String[] newparts = userid.split("@");
        String myRoll = newparts[0].toLowerCase();

        listView=(ListView)findViewById(R.id.list_user);

        rootRef = FirebaseDatabase.getInstance().getReference();
        usersdRef = rootRef.child("Users").child(myRoll).child("Attendance");
        dataModels = new ArrayList<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String date = null,breakfast = null,lunch=null,supper=null;

                    for(DataSnapshot ts : dataSnapshot.getChildren()){

                        date = ts.child("Date:").getValue(String.class);
                        breakfast = ts.child("Breakfast:").getValue(String.class);
                        lunch  = ts.child("Lunch:").getValue(String.class);
                        supper = ts.child("Supper:").getValue(String.class);

                        dataModels.add(new DataModel(date,breakfast,lunch,supper));
                    }

                    //name = name.toUpperCase();roll = roll.toUpperCase();


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

                Snackbar.make(view, dataModel.getName()+"\n"+"Breakfast"+"("+dataModel.getType()+")"+  "Lunch ("+dataModel.getVersion_number()+")  Supper("+dataModel.getFeature()+")", Snackbar.LENGTH_LONG)
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
            Intent refresh = new Intent(this, UserListActivity.class);
            startActivity(refresh);
            this.finish();
            return true;
        }
        if (id == R.id.action_back){
            startActivity(new Intent(UserListActivity.this,NavigationHomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

