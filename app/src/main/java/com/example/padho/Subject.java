package com.example.padho;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Subject extends AppCompatActivity {
    private ImageButton UrduButton;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject);
        UrduButton = findViewById(R.id.imageView4);
        UrduButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Subject.this, "Hello", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Subject.this, Topic.class));
            }
        });
    }
}
