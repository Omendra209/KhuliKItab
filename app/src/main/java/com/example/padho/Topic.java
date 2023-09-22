package com.example.padho;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Topic extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topics);
        CardView topic1 = findViewById(R.id.Topic1);
        topic1.setOnClickListener(v -> startActivity(new Intent(Topic.this, Content.class)));
    }
}
