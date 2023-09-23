package com.example.padho;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.padho.databinding.ActivityChapterListBinding;

public class ChapterList extends AppCompatActivity {
    private ActivityChapterListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChapterListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();


    }

    private void setListeners() {
        binding.chapter1.setOnClickListener(v -> {
            String textTOSend = binding.textViewChapter1.getText().toString();
            Intent intent = new Intent(ChapterList.this, TextContent.class);
            intent.putExtra("textKey", textTOSend);
            startActivity(intent);
        });
        binding.chapter2.setOnClickListener(v -> {
            String textTOSend = binding.textViewChapter2.getText().toString();
            Intent intent = new Intent(ChapterList.this, TextContent.class);
            intent.putExtra("textKey", textTOSend);
            startActivity(intent);
        });
    }
}
