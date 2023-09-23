package com.example.padho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.padho.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();

    }

    private void setListeners() {
        binding.SignupButton.setOnClickListener(v -> validateCredentials() );
    }

    private void validateCredentials() {
        String email = binding.EmailSignup.getText().toString();
        String password = binding.PasswordSignup.getText().toString();
        String confirmPassword = binding.ConfirmPasswordSignup.getText().toString();
        String username = binding.UsernameSignup.getText().toString();
        if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || username.isEmpty()) {
            Toast.makeText(Signup.this, "Enter All Details First", Toast.LENGTH_SHORT).show();
        }else{
            if (!password.equals(confirmPassword)) {
                Toast.makeText(Signup.this, "Password not match", Toast.LENGTH_SHORT).show();
            } else {
                SignupUser(email,password);
            }
        }
    }

    private void SignupUser(String Email,String Password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(Signup.this, task -> {
                if(task.isSuccessful()){
                    Toast.makeText(Signup.this, "User SuccessFully created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Signup.this,Login.class));
                }
                else {
                    Exception e = task.getException();
                    Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}