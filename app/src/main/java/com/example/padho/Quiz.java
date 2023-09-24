package com.example.padho;

import android.content.Intent;
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
        if (selectedOption1 != null && selectedOption1.getText().toString().equals("bed")) {
            score++;
        }

        // Question 2
        RadioGroup question2RadioGroup = binding.question2RadioGroup;
        int selectedOption2Id = question2RadioGroup.getCheckedRadioButtonId();
        RadioButton selectedOption2 = findViewById(selectedOption2Id);
        if (selectedOption2 != null && selectedOption2.getText().toString().equals("cap")) {
            score++;
        }

        RadioGroup question3RadioGroup = binding.question3RadioGroup;
        int selectedOption3Id = question1RadioGroup.getCheckedRadioButtonId();
        RadioButton selectedOption3 = findViewById(selectedOption1Id);
        if (selectedOption3 != null && selectedOption3.getText().toString().equals("red")) {
            score++;
        }

        RadioGroup question4RadioGroup = binding.question4RadioGroup;
        int selectedOption4Id = question1RadioGroup.getCheckedRadioButtonId();
        RadioButton selectedOption4 = findViewById(selectedOption1Id);
        if (selectedOption4 != null && selectedOption4.getText().toString().equals("cat")) {
            score++;
        }

        RadioGroup question5RadioGroup = binding.question5RadioGroup;
        int selectedOption5Id = question1RadioGroup.getCheckedRadioButtonId();
        RadioButton selectedOption5 = findViewById(selectedOption5Id);
        if (selectedOption5 != null && selectedOption5.getText().toString().equals("bit")) {
            score++;
        }


        return score;
    }

    private void displayScore(int score) {
        String message = "Your score is: " + score;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (score>=3){
            Intent intent = new Intent(Quiz.this,Topic.class);
            intent.putExtra("key",score);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "You Are Fail. Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}