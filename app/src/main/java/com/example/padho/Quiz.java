package com.example.padho;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.padho.databinding.ActivityQuizBinding;

public class Quiz extends AppCompatActivity {
    private ActivityQuizBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
    }

    private void setListeners() {
        binding.submitButton.setOnClickListener(v -> {
            // Calculate and display the score
            int score = calculateScore();
            displayScore(score);

            // You can also perform other actions here, like showing correct answers, etc.
        });
    }

    private int calculateScore() {
        int score = 0;

        // Question 1
        RadioGroup question1RadioGroup = binding.question1RadioGroup;
        int selectedOption1Id = question1RadioGroup.getCheckedRadioButtonId();
        RadioButton selectedOption1 = findViewById(selectedOption1Id);
        if (selectedOption1 != null && selectedOption1.getText().toString().equals("Paris")) {
            score++;
        }

        // Question 2
        RadioGroup question2RadioGroup = binding.question2RadioGroup;
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