package com.example.padho;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.padho.databinding.ActivityChapterListBinding;

public class ChapterList extends AppCompatActivity {
    private ActivityChapterListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChapterListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.chapter1.setOnClickListener(v -> startActivity(new Intent(ChapterList.this, Topic.class)));
    }
}
