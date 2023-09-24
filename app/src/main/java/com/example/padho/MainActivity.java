package com.example.padho;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.padho.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();

        testAPI();
    }

    private void testAPI() {
//        ChatCompletionAI.chatCompletionRequest("What is value of gravity");
    }

    private void setListeners() {
        binding.loginButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,com.example.padho.Login.class)));
        binding.signupButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,com.example.padho.Signup.class)));
    }
}