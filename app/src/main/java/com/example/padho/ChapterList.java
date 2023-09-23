package com.example.padho;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ChapterList extends AppCompatActivity {
    private CardView chapter_1;
    private CardView chapter_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_list);
        chapter_1=findViewById(R.id.Chapter1);
        chapter_1.setOnClickListener(view -> {
            TextView textView = findViewById(R.id.Chapter1TextView);
            String textToSend = textView.getText().toString();

            Intent intent = new Intent(ChapterList.this, Topic.class);
            intent.putExtra("textKey", textToSend);
            startActivity(intent);
        });
        chapter_2=findViewById(R.id.Chapter2);
        chapter_2.setOnClickListener(view -> {
            TextView textView = findViewById(R.id.Chapter2TextView);
            String textToSend = textView.getText().toString();

            Intent intent = new Intent(ChapterList.this, Topic.class);
            intent.putExtra("textKey", textToSend);
            startActivity(intent);
        });
    }
}
