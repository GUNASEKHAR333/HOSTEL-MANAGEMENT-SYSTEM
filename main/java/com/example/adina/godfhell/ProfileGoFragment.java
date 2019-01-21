package com.example.adina.godfhell;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileGoFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase  database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseRef=database.getReference();
    private TextView viewName;
    private TextView viewRollno;
    private EditText viewMobile;
    private TextView viewWebMail;
    private TextView viewHall;
    private TextView viewRoom;
    private Button editButton,backButton;
    private TextView due;
    private String userid;
    private DatabaseReference mdatabase,mobileDatabase;
    private DatabaseReference mdatabaseRoll,mdatabaseMobile,mdatabaseWebmail,mdatabaseDue,mdatabaseHall,mdatabaseRoom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_profile_go, container, false);

    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");

        Bundle bundle = getActivity().getIntent().getExtras();
        String goEmail = bundle.getString("message");
        String[] parts = goEmail.split("@");
        String splitMail = parts[0];

        viewName = (TextView)getView().findViewById(R.id.name_input);
        viewRollno = (TextView)getView().findViewById(R.id.roll_no_input);
        viewMobile = (EditText)getView().findViewById(R.id.mobile_no_input);
        viewWebMail = (TextView)getView().findViewById(R.id.email_input);
        viewHall = (TextView)getView().findViewById(R.id.hall_input);
        viewRoom = (TextView)getView().findViewById(R.id.room_no_input);
        editButton = (Button)getView().findViewById(R.id.button_edit);
        due = (TextView)getView().findViewById(R.id.Due_input);

        auth = FirebaseAuth.getInstance();

        //from scan to database user id roll no scrutiny
        userid = splitMail ;
        userid = userid.toLowerCase();

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Name:");
        mdatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                String getName = dataSnapshot.getValue(String.class);
                viewName.setText(getName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mdatabaseRoll = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Rollno:");
        mdatabaseRoll.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                String getRoll = dataSnapshot.getValue(String.class);
                viewRollno.setText(getRoll);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mdatabaseHall = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Hall:");
        mdatabaseHall.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                String getHall = dataSnapshot.getValue(String.class);
                viewHall.setText(getHall);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mdatabaseRoom = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Room:");
        mdatabaseRoom.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                String getRoom = dataSnapshot.getValue(String.class);
                viewRoom.setText(getRoom);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mdatabaseMobile = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Mobile:");
        mdatabaseMobile.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                String getMobile = dataSnapshot.getValue(String.class);
                viewMobile.setText(getMobile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        mdatabaseWebmail = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Email:");
        mdatabaseWebmail.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                String getMail = dataSnapshot.getValue(String.class);
                viewWebMail.setText(getMail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        mdatabaseDue = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Due:");
        mdatabaseDue.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                String getDue = dataSnapshot.getValue(String.class);
                due.setText(getDue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("Mobile:");

                mobileDatabase.setValue(viewMobile.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            getFragmentManager().beginTransaction().detach(ProfileGoFragment.this).attach(ProfileGoFragment.this).commit();

                            Toast.makeText(getActivity(),"Upadated Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else if (!task.isSuccessful())
                        {
                            Toast.makeText(getActivity(),"Unable to Upadate",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        backButton = (Button)getView().findViewById(R.id.profile_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getActivity(),NavigationHomeActivity.class);
            startActivity(intent);
            }
        });

    }

}
