package com.example.myfirebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText signInemail, signInpass;
    Button singInButton;
    TextView signupTextView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInemail = (EditText) findViewById(R.id.loginEmailEditTextId);
        signInpass = (EditText) findViewById(R.id.loginPasswordEditTextId);

        singInButton = (Button) findViewById(R.id.signInButtonId);

        signupTextView = (TextView) findViewById(R.id.signUpLinkTextViewId);

        mAuth = FirebaseAuth.getInstance();


        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SingupActivity.class);
                startActivity(intent);

            }
        });

        //login Button Funtion
        singInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = signInemail.getText().toString();
                String pass = signInpass.getText().toString();

                if(email.isEmpty() || pass.isEmpty()){
                    signInemail.setError("Field Must Not be Empty");
                    signInemail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    signInemail.setError("Please enter a valid email");
                    signInemail.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this, "Login Not Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });







    }
}
