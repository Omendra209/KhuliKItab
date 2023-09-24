package com.example.padho;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.padho.databinding.ActivityQuizBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.nio.charset.StandardCharsets;

public class Quiz extends AppCompatActivity {
    private ActivityQuizBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseApp.initializeApp(this);
        displayPreviousScore();
        setListeners();
    }

    private void setListeners() {
        binding.submitButton.setOnClickListener(v -> {
            // Calculate and display the score
            int score = calculateScore();
            displayScore(score);

            // You can also perform other actions here, like showing correct answers, etc.
            // Get the current user
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userUid = user.getUid();
                int newScore = score; // Replace with the user's new quiz score

                // Get a reference to the Firebase Storage instance
                FirebaseStorage storage = FirebaseStorage.getInstance();

                // Create storage references to the user's score files
                StorageReference previousScoreRef = storage.getReference().child("scores/" + userUid + "/previous_score.txt");
                StorageReference currentScoreRef = storage.getReference().child("scores/" + userUid + "/current_score.txt");

                // Convert the score to a string and store it in the file
                String scoreAsString = String.valueOf(newScore);

                // Update the previous score file with the current score
                previousScoreRef.putBytes(scoreAsString.getBytes(StandardCharsets.UTF_8))
                        .addOnSuccessListener(taskSnapshot -> {
                            // Score updated successfully
                            System.out.println("Previous score updated successfully!");
                        })
                        .addOnFailureListener(exception -> {
                            // Handle any errors
                            System.err.println("Error updating previous score: " + exception.getMessage());
                        });

                // Display the new current score
                binding.currentScoreTextView.setText("Current Score: " + newScore);
            }
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
        int selectedOption3Id = question3RadioGroup.getCheckedRadioButtonId();
        RadioButton selectedOption3 = findViewById(selectedOption3Id);
        if (selectedOption3 != null && selectedOption3.getText().toString().equals("red")) {
            score++;
        }

        RadioGroup question4RadioGroup = binding.question4RadioGroup;
        int selectedOption4Id = question4RadioGroup.getCheckedRadioButtonId();
        RadioButton selectedOption4 = findViewById(selectedOption4Id);
        if (selectedOption4 != null && selectedOption4.getText().toString().equals("cat")) {
            score++;
        }

        RadioGroup question5RadioGroup = binding.question5RadioGroup;
        int selectedOption5Id = question5RadioGroup.getCheckedRadioButtonId();
        RadioButton selectedOption5 = findViewById(selectedOption5Id);
        if (selectedOption5 != null && selectedOption5.getText().toString().equals("bit")) {
            score++;
        }


        return score;
    }

    private void displayPreviousScore() {
        // Get the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userUid = user.getUid();

            // Get a reference to the Firebase Storage instance
            FirebaseStorage storage = FirebaseStorage.getInstance();

            // Create a storage reference to the previous score file
            StorageReference previousScoreRef = storage.getReference().child("scores/" + userUid + "/previous_score.txt");

            // Check if the previous score file exists
            previousScoreRef.getMetadata()
                    .addOnSuccessListener(metadata -> {
                        if (metadata.getSizeBytes() > 0) {
                            // The file exists, proceed to download it
                            previousScoreRef.getBytes(1024 * 1024)
                                    .addOnSuccessListener(bytes -> {
                                        String previousScoreAsString = new String(bytes, StandardCharsets.UTF_8);
                                        long previousScore = Long.parseLong(previousScoreAsString);

                                        // Display the previous score
                                        binding.previousScoreTextView.setText("Previous Score: " + previousScore);
                                    })
                                    .addOnFailureListener(exception -> {
                                        // Handle any errors while downloading the previous score file
                                        System.err.println("Error downloading previous score: " + exception.getMessage());
                                    });
                        } else {
                            // The file does not exist, handle accordingly (e.g., display a default score)
                            binding.previousScoreTextView.setText("Previous Score: N/A");
                        }
                    })
                    .addOnFailureListener(exception -> {
                        // Handle metadata retrieval failure
                        System.err.println("Error checking previous score existence: " + exception.getMessage());
                    });
        }
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