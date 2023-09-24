package com.example.padho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.padho.databinding.ActivityTopicBinding;

public class Topic extends AppCompatActivity {
    private ActivityTopicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        Intent intent = getIntent();
        if (intent.hasExtra("testKey")) {
            String subject = intent.getStringExtra("testKey");
            binding.textViewTopic.setText(subject);
        }

        setListeners();
    }

    private void setListeners() {
        binding.Topic1.setOnClickListener(v -> {
                startActivity(new Intent(Topic.this, Content.class));
        });

        Intent intent = getIntent();
        if(intent!=null){
            int data = intent.getIntExtra("key",0);
            if (data>=1){
                binding.Topic2.setOnClickListener(v -> {
                    Toast.makeText(this, "topic2gdd", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Topic.this, Content.class));
                });
            }
        }

    }
}
