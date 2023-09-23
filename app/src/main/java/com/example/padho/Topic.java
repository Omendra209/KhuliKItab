package com.example.padho;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.padho.databinding.ActivityTopicBinding;

public class Topic extends AppCompatActivity {
    private ActivityTopicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.Topic1.setOnClickListener(v -> startActivity(new Intent(Topic.this, Content.class)));
    }
}
