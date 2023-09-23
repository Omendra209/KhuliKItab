package com.example.padho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.padho.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
    }

    private void setListeners() {
        binding.loginButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,com.example.padho.Login.class)));
        binding.signupButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,com.example.padho.Signup.class)));
    }
}