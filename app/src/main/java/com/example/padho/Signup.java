package com.example.padho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    private EditText Password,  email, ConfirmPassword, UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        UserName = findViewById(R.id.UsernameSignup);
        email = findViewById(R.id.EmailSignup);
        Password = findViewById(R.id.PasswordSignup);
        ConfirmPassword = findViewById(R.id.ConfirmPasswordSignup);
        Button signup_button = findViewById(R.id.SignupButton);
        signup_button.setOnClickListener(v ->{
                String emailS = email.getText().toString();
                String passwordS1 = Password.getText().toString();
                String passwordS2 = ConfirmPassword.getText().toString();
                String usernameS = UserName.getText().toString();
                if(TextUtils.isEmpty(emailS) || TextUtils.isEmpty(passwordS1) || TextUtils.isEmpty(passwordS2) || TextUtils.isEmpty(usernameS)) {
                    Toast.makeText(Signup.this, "Enter All Details First", Toast.LENGTH_SHORT).show();
                }else{
                    if (!passwordS1.equals(passwordS2)) {
                        Toast.makeText(Signup.this, "Password not match", Toast.LENGTH_SHORT).show();
                    } else {
                        SignupUser(emailS,passwordS1);
                    }
                }
            });

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