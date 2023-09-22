package com.example.padho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private EditText Email;
    private EditText Password;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Email = findViewById(R.id.EmailLogin);
        Password = findViewById(R.id.PasswordLogin);
        Button loginButton = findViewById(R.id.LoginButton);
        Auth=FirebaseAuth.getInstance();

        // For user stay login
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Intent intent = new Intent(Login.this,Subject.class);
            startActivity(intent);
            finish();
        }
        loginButton.setOnClickListener(view -> {
            String emailL = Email.getText().toString();
            String passwordL = Password.getText().toString();
            if (TextUtils.isEmpty(emailL) || TextUtils.isEmpty(passwordL))
            {
                Toast.makeText(Login.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            }
            else
            {
                login(emailL,passwordL);
            }
        });
    }

    private void login(String email, String password)
    {
        Auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, task -> {
            if (task.isSuccessful())
            {
                startActivity(new Intent(Login.this, Subject.class));
            }
            else
            {
                Toast.makeText(Login.this, "Authentication Failed " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}