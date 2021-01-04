package com.example.test_mma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class
LoginActivity extends AppCompatActivity {

    private EditText uName;
    private EditText password;
    private Button login;
    private TextView registration;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uName = (EditText)findViewById(R.id.regUname);
        password = (EditText)findViewById(R.id.regPassW);
        login = (Button) findViewById(R.id.btnLogin);
        registration = (TextView)findViewById(R.id.signUp);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uPassword = password.getText().toString();
                String email = uName.getText().toString();
                if(email.isEmpty() || uPassword.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please insert all the details", Toast.LENGTH_LONG).show();
                }
                else {
                    loginvalidate(uName.getText().toString(), password.getText().toString());
                }
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }


    private void loginvalidate(String username, String password){
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    emailVerified();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG);
                }
            }
        });
    }
    private void emailVerified() {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean verified = firebaseUser.isEmailVerified();

        if(verified){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        else {
            Toast.makeText(this, "You need to verify your email, please.", Toast.LENGTH_LONG).show();
            firebaseAuth.signOut();
        }
    }
}
