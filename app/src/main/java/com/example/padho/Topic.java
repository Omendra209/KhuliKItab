package com.example.padho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class Topic extends AppCompatActivity {
    private CardView Topic1;
    private CardView Topic2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topics);

        Intent getintent = getIntent();
        if (getintent != null) {
            String receivedText = getintent.getStringExtra("textKey");

            if (receivedText != null) {
                // Do something with the received text
                // For example, set it to a TextView in ActivityB
                TextView receivedTextView = findViewById(R.id.Topic_Text);
                receivedTextView.setText(receivedText);
            }
        }

        Topic1 = findViewById(R.id.Topic1);
        Topic1.setOnClickListener(v -> {
            Intent intent = new Intent(Topic.this, Content.class);
            TextView textView = findViewById(R.id.Topic1TextView);
            String textToSend = textView.getText().toString();
            intent.putExtra("textKey",textToSend);
            startActivity(intent);
        });
        Topic2 = findViewById(R.id.Topic2);
        Topic2.setOnClickListener(v -> {
            Intent intent = new Intent(Topic.this, Content.class);
            TextView textView = findViewById(R.id.Topic2TextView);
            String textToSend = textView.getText().toString();
            intent.putExtra("textKey",textToSend);
            startActivity(intent);
        });
    }
}
