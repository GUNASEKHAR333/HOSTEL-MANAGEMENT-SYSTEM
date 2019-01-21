package com.example.adina.godfhell;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AfterMainBarcode extends AppCompatActivity {

    private String rollnum;
    public String due;
    private String upam;
    FirebaseDatabase  database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseRef=database.getReference(),mdatabase2,mdatabase3,mdatabase4;
    DatabaseReference fdatabase1,fdatabase2,fdatabase3,cdatabase,cdatabase2;
    private RadioGroup radioGroup;
    private Button button_update;
    private RadioButton radioButton;

    private int d;
    private int i,j;
    private int dt;
    private int bt,lt,st;
    private String dat;

    private TextView rollnumber;
    private String b,l,s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_main_barcode);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mdatabase4=FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        rollnum = intent.getStringExtra("messageroll");
        //mdatabase3=FirebaseDatabase.getInstance().getReference().child("Users");
        mdatabase2=database.getReference().child("Users").child(rollnum).child("Due:");
        mdatabase3=database.getReference().child("Caterer").child("Sum:");
        rollnumber = (TextView)findViewById(R.id.new_text);
        rollnumber.setTextIsSelectable(true);
        rollnumber.setText(rollnum);

        fdatabase1=database.getReference().child("Price").child("Breakfast:");
        fdatabase2=database.getReference().child("Price").child("Lunch:");
        fdatabase3=database.getReference().child("Price").child("Supper:");
        cdatabase =database.getReference();cdatabase2 = database.getReference();

        Calendar c=Calendar.getInstance();
        int m=c.get(Calendar.MONTH)+1;
        dat=c.get(Calendar.DATE)+"-"+m+"-"+c.get(Calendar.YEAR);


        fdatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bts=dataSnapshot.getValue(String.class);
                bt=Integer.valueOf(bts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fdatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lts=dataSnapshot.getValue(String.class);
                lt=Integer.valueOf(lts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fdatabase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sts=dataSnapshot.getValue(String.class);
                st=Integer.valueOf(sts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radiomeal);
        button_update = (Button) findViewById(R.id.updateid);

        button_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                i=1;j=1;
                //final int dt;
                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                if(radioButton.getText().toString().equals("Breakfast")){
                    // mdatabase2=database.getReference().child("Users").child(rollnum).child("Due:");
                    mdatabase2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String duet=dataSnapshot.getValue(String.class);
                            dt = Integer.valueOf(duet);
                            //int mt=Integer.valueOf(b);
                            dt = dt + bt;
                            String upamt = String.valueOf(dt);
                            if(i==1) {
                                mDatabaseRef.child("Users").child(rollnum).child("Due:").setValue(upamt);
                                mDatabaseRef.child("Caterer").child(rollnum).setValue(upamt);
                                cdatabase.child("Users").child(rollnum).child("Attendance").child(dat).child("Date:").setValue(dat);
                                cdatabase2.child("Users").child(rollnum).child("Attendance").child(dat).child("Breakfast:").setValue("1");
                                //Log.d(due,"key");
                                i=i+1;
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    mdatabase3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String sum=dataSnapshot.getValue(String.class);
                            int s=Integer.valueOf(sum);
                            //int mt=Integer.valueOf(l);
                            s=s+bt;
                            String s2=String.valueOf(s);
                            if(j==1) {
                                mdatabase4.child("Caterer").child("Sum:").setValue(s2);
                                j=j+1;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(AfterMainBarcode.this,"Due is successfully updated",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AfterMainBarcode.this,Admin.class));

                }
                else if (radioButton.getText().toString().equals("Lunch")){

                    // mdatabase2=database.getReference().child("Users").child(rollnum).child("Due:");
                    mdatabase2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String duet=dataSnapshot.getValue(String.class);
                            int dt = Integer.valueOf(duet);
                            dt = dt + lt;
                            String upamt = String.valueOf(dt);
                            if(i==1) {
                                mDatabaseRef.child("Users").child(rollnum).child("Due:").setValue(upamt);
                                mDatabaseRef.child("Caterer").child(rollnum).setValue(upamt);
                                cdatabase.child("Users").child(rollnum).child("Attendance").child(dat).child("Date:").setValue(dat);
                                cdatabase2.child("Users").child(rollnum).child("Attendance").child(dat).child("Lunch:").setValue("1");

                                //Log.d(due,"key");
                                i=i+1;
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    mdatabase3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String sum=dataSnapshot.getValue(String.class);
                            int s=Integer.valueOf(sum);
                            s=s+lt;
                            String s2=String.valueOf(s);
                            if(j==1) {
                                mdatabase4.child("Caterer").child("Sum:").setValue(s2);
                                j=j+1;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(AfterMainBarcode.this,"Due is successfully updated",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AfterMainBarcode.this,Admin.class));
                }
                else if (radioButton.getText().toString().equals("Supper")){


                    mdatabase2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String duet=dataSnapshot.getValue(String.class);
                            int dt = Integer.valueOf(duet);
                            dt = dt + st;
                            String upamt = String.valueOf(dt);
                            if(i==1) {
                                mDatabaseRef.child("Users").child(rollnum).child("Due:").setValue(upamt);
                                mDatabaseRef.child("Caterer").child(rollnum).setValue(upamt);
                                cdatabase.child("Users").child(rollnum).child("Attendance").child(dat).child("Date:").setValue(dat);
                                cdatabase2.child("Users").child(rollnum).child("Attendance").child(dat).child("Supper:").setValue("1");

                                //Log.d(due,"key");
                                i=i+1;
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    mdatabase3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String sum=dataSnapshot.getValue(String.class);
                            int s=Integer.valueOf(sum);
                            s=s+st;
                            String s2=String.valueOf(s);
                            if(j==1) {
                                mdatabase4.child("Caterer").child("Sum:").setValue(s2);
                                j=j+1;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(AfterMainBarcode.this,"Due is successfully updated",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AfterMainBarcode.this,Admin.class));
                }

            }

        });

    }

}