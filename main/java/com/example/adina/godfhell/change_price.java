package com.example.adina.godfhell;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

public class change_price extends AppCompatActivity {

    private TextView breakfast,lunch,supper;
    private TextView new_breakfast,new_lunch,new_supper;
    private EditText edit_breakfast,edit_lunch,edit_supper;
    private Button editbutton,savebutton,backb;
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference mdatabase1=database.getInstance().getReference().child("Price");
    private DatabaseReference mdatabaseref=database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_price);

        breakfast =(TextView)findViewById(R.id.breakfast_id);
        lunch = (TextView)findViewById(R.id.lunch_id);
        supper =(TextView)findViewById(R.id.supper_id);
        new_breakfast = (TextView)findViewById(R.id.new_break_id);
        new_lunch = (TextView)findViewById(R.id.new_lunch_id);
        new_supper = (TextView)findViewById(R.id.new_supper_id);
        edit_breakfast =(EditText)findViewById(R.id.new_edit_break);
        edit_lunch = (EditText)findViewById(R.id.new_edit_lunch);
        edit_supper = (EditText)findViewById(R.id.new_edit_supper);

        editbutton =(Button)findViewById(R.id.button_e);
        savebutton = (Button)findViewById(R.id.button_save);
        backb = (Button)findViewById(R.id.back_id);

        new_breakfast.setVisibility(View.INVISIBLE);
        new_lunch.setVisibility(View.INVISIBLE);
        new_supper.setVisibility(View.INVISIBLE);
        edit_breakfast.setVisibility(View.INVISIBLE);
        edit_lunch.setVisibility(View.INVISIBLE);
        edit_supper.setVisibility(View.INVISIBLE);
        savebutton.setVisibility(View.INVISIBLE);

        mdatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String value = dataSnapshot.getValue(String.class);


                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                final Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator );

                breakfast.setText(map.get("Breakfast:"));
                lunch.setText(map.get("Lunch:"));
                supper.setText(map.get("Supper:"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_breakfast.setVisibility(View.VISIBLE);
                new_lunch.setVisibility(View.VISIBLE);
                new_supper.setVisibility(View.VISIBLE);
                edit_breakfast.setVisibility(View.VISIBLE);
                edit_lunch.setVisibility(View.VISIBLE);
                edit_supper.setVisibility(View.VISIBLE);
                savebutton.setVisibility(View.VISIBLE);

            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String b=edit_breakfast.getText().toString();
                String l=edit_lunch.getText().toString();
                String s=edit_supper.getText().toString();

                mdatabaseref.child("Price").child("Breakfast:").setValue(b);
                mdatabaseref.child("Price").child("Lunch:").setValue(l);
                mdatabaseref.child("Price").child("Supper:").setValue(s);
                startActivity(new Intent(change_price.this,change_price.class));
            }
        });

        backb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(change_price.this,Admin.class));
            }
        });

    }
}
