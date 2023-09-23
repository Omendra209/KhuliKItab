package com.example.padho;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.padho.databinding.ActivitySubjectBinding;

public class Subject extends AppCompatActivity {
    private ActivitySubjectBinding binding;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.imageViewEnglish.setOnClickListener( v -> startActivity(new Intent(Subject.this, Topic.class)));
    }
}
