package com.example.myfirebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;

public class SingupActivity extends AppCompatActivity {

    TextView signInLinkTextViewId;
    EditText signUpemail, signUpPass;
    Button signUpButton;

    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.progressBarId);

        signInLinkTextViewId = (TextView) findViewById(R.id.signInLinkTextViewId);

        signUpButton = (Button) findViewById(R.id.signUpButtonId);

        signInLinkTextViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signUpemail = (EditText) findViewById(R.id.signUpEmailEditTextId);
        signUpPass = (EditText) findViewById(R.id.signUpPasswordEditTextId);


        //signup function
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = signUpemail.getText().toString();
                String pass = signUpPass.getText().toString();

                if(email.isEmpty() || pass.isEmpty()){
                    signUpemail.setError("Field Must Not be Empty");
                    signUpemail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    signUpemail.setError("Please enter a valid email");
                    signUpemail.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()){
                            Toast.makeText(SingupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        }else{

                            if(task.getException() instanceof FirebaseAuthActionCodeException){
                                Toast.makeText(SingupActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SingupActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                });




            }
        });




    }
}
