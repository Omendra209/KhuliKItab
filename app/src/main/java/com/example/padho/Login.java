package com.example.padho;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.padho.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private FirebaseAuth Auth;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }



    private void setListeners() {
        binding.LoginButton.setOnClickListener(view -> {
            String email = binding.inputEmailLogin.getText().toString();
            String password = binding.inputPasswordLogin.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            }
            else {
                login(email,password);
            }
        });
    }

    private void login(String email, String password) {
        Auth=FirebaseAuth.getInstance();
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