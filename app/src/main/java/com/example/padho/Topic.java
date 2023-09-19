package com.example.padho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class Topic extends AppCompatActivity {
    private CardView Topic1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topics);
        Topic1 = findViewById(R.id.Topic1);
        Topic1.setOnClickListener(v -> {
                startActivity(new Intent(Topic.this, Content.class));
        });
    }
}
