package com.example.padho;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ChapterList extends AppCompatActivity {
    private CardView chapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_list);
        chapter1=findViewById(R.id.chapter_1);
        chapter1.setOnClickListener(view -> {
            startActivity(new Intent(ChapterList.this, Topic.class));
        });

    }
}
