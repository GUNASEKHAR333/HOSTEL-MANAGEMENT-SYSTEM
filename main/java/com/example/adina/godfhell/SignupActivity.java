package com.example.adina.godfhell;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class SignupActivity extends AppCompatActivity{

    private static final String TAG = "SignupActivity" ;
    private Button btnSignUp,btnLinkToLogIn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private EditText signupInputEmail, signupInputPassword,signupInputRoll;
    private TextInputLayout  signupInputLayoutEmail, signupInputLayoutPassword;
    private Spinner dropdown;
    private EditText signupInputRoom,signupInputPhone,signupInputName;
    private Integer Due;
    private DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();

        signupInputLayoutEmail = (TextInputLayout) findViewById(R.id.signup_input_layout_email);
        signupInputLayoutPassword = (TextInputLayout) findViewById(R.id.signup_input_layout_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        signupInputPhone = (EditText)findViewById(R.id.signup_input_mobile);
        signupInputRoom = (EditText)findViewById(R.id.signup_input_room);
        signupInputName = (EditText)findViewById(R.id.signup_input_name);
        signupInputRoll = (EditText) findViewById(R.id.signup_input_roll);
        signupInputEmail = (EditText) findViewById(R.id.signup_input_email);
        signupInputPassword = (EditText) findViewById(R.id.signup_input_password);

        Due = 0;
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLinkToLogIn = (Button) findViewById(R.id.btn_link_login);

        dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"C V R","K M S","S S B","M V","G D B","D B A","M S S","H B","V S","S D"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);
        dropdown.setAdapter(adapter);

        String dropdownitem = String.valueOf(dropdown.getSelectedItem());

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();

            }
        });

        btnLinkToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Validating form
     */
    private void submitForm() {

        final String email = signupInputEmail.getText().toString().trim();
        String password = signupInputPassword.getText().toString().trim();

        if(!checkEmail()) {
            return;
        }
        if(!checkPassword()) {
            return;
        }
        signupInputLayoutEmail.setErrorEnabled(false);
        signupInputLayoutPassword.setErrorEnabled(false);

        progressBar.setVisibility(View.VISIBLE);
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"createUserWithEmail:onComplete:" + task.isSuccessful());
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        String userid = signupInputRoll.getText().toString();
                                        Toast.makeText(SignupActivity.this,"verification email sent",Toast.LENGTH_LONG).show();

                                        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                                        DatabaseReference catererDatabase = FirebaseDatabase.getInstance().getReference().child("Caterer");

                                        HashMap<String ,String> data = new HashMap<>();


                                        data.put("Name:",signupInputName.getText().toString());
                                        data.put("Rollno:",signupInputRoll.getText().toString());
                                        data.put("Hall:",dropdown.getSelectedItem().toString());
                                        data.put("Room:",signupInputRoom.getText().toString());
                                        data.put("Mobile:",signupInputPhone.getText().toString());
                                        data.put("Email:",signupInputEmail.getText().toString());
                                        data.put("Due:",Due.toString());

                                        catererDatabase.child(signupInputRoll.getText().toString()).setValue(Due.toString());

                                        mdatabase.setValue(data);

                                        signupInputPhone.setText("");
                                        signupInputName.setText("");
                                        signupInputRoom.setText("");
                                        signupInputEmail.setText("");
                                        signupInputPassword.setText("");
                                        signupInputRoll.setText("");
                                        dropdown.setSelection(0);
                                    }
                                    else {
                                        Toast.makeText(SignupActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }

                                }
                            });


                        }
                        else {
                            Log.d(TAG,"Authentication failed." + task.getException());
                        }
                    }
                });
    }

    private boolean checkEmail() {
        String email = signupInputEmail.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {

            signupInputLayoutEmail.setErrorEnabled(true);
            signupInputLayoutEmail.setError(getString(R.string.err_msg_email));
            signupInputEmail.setError(getString(R.string.err_msg_required));
            requestFocus(signupInputEmail);
            return false;
        }
        signupInputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword() {

        String password = signupInputPassword.getText().toString().trim();
        if (password.isEmpty() || !isPasswordValid(password)) {

            signupInputLayoutPassword.setError(getString(R.string.err_msg_password));
            signupInputPassword.setError(getString(R.string.err_msg_required));
            requestFocus(signupInputPassword);
            return false;
        }
        signupInputLayoutPassword.setErrorEnabled(false);
        return true;
    }

    private static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isPasswordValid(String password){
        return (password.length() >= 8);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
