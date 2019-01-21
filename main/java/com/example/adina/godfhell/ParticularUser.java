package com.example.adina.godfhell;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ParticularUser extends AppCompatActivity {

    private TextView textView;
    private TextView textView2;
    private EditText editText;
    private Button getButton;
    private Button backButton;
    private String rolli;
    private String  name;
    private String duet;
    private TextInputLayout text;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference mdatabaseref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_user);

        textView = (TextView)findViewById(R.id.due_got_id);
        textView2 =(TextView)findViewById(R.id.get_due_id);
        editText = (EditText)findViewById(R.id.boarder_id);
        getButton =(Button)findViewById(R.id.get_details_id);
        backButton =(Button)findViewById(R.id.back_button_id);
        textView2.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rolli=editText.getText().toString();
                name =rolli.toLowerCase();
                mdatabaseref=database.getReference().child("Caterer").child(name);
                mdatabaseref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        duet = dataSnapshot.getValue(String.class);
                        textView.setText(duet);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if(duet == "") {

                    textView.setVisibility(View.GONE);
                    textView2.setVisibility(View.GONE);

                    Toast.makeText(ParticularUser.this,"invalid roll no",Toast.LENGTH_SHORT).show();

                }
                else {
                    textView.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParticularUser.this,Admin.class));
            }
        });
    }
}
