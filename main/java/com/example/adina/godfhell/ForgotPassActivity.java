package com.example.adina.godfhell;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    private EditText email;
    private Button forgot;
    private ProgressBar progressbar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        email=(EditText)findViewById(R.id.email_reset);
        forgot=(Button)findViewById(R.id.button_reset);
        progressbar=(ProgressBar)findViewById(R.id.progressbar_reset);
        auth = FirebaseAuth.getInstance();

        progressbar.setVisibility(View.GONE);
        forgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if(task.isSuccessful()){
                            progressbar.setVisibility(View.GONE);
                            Toast.makeText(ForgotPassActivity.this,"Password reset link send to your mail",Toast.LENGTH_LONG).show();
                            email.setText("");
                        }
                        else{
                            Toast.makeText(ForgotPassActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
