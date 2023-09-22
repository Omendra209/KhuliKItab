package com.example.padho;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Quiz extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        Button submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calculate and display the score
                int score = calculateScore();
                displayScore(score);

                // You can also perform other actions here, like showing correct answers, etc.
            }
        });
    }

    private int calculateScore() {
        int score = 0;

        // Question 1
        RadioGroup question1RadioGroup = findViewById(R.id.question1RadioGroup);
        int selectedOption1Id = question1RadioGroup.getCheckedRadioButtonId();
        RadioButton selectedOption1 = findViewById(selectedOption1Id);
        if (selectedOption1 != null && selectedOption1.getText().toString().equals("Paris")) {
            score++;
        }

        // Question 2
        RadioGroup question2RadioGroup = findViewById(R.id.question2RadioGroup);
        int selectedOption2Id = question2RadioGroup.getCheckedRadioButtonId();
        RadioButton selectedOption2 = findViewById(selectedOption2Id);
        if (selectedOption2 != null && selectedOption2.getText().toString().equals("4")) {
            score++;
        }

        return score;
    }

    private void displayScore(int score) {
        String message = "Your score is: " + score;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
