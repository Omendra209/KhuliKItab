package com.example.padho;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.padho.databinding.ActivitySubjectBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Subject extends AppCompatActivity {
    private ActivitySubjectBinding binding;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.buttonAsk.setOnClickListener( v -> startActivity(new Intent(Subject.this, ChatAI.class)));
        binding.imageViewEnglish.setOnClickListener( v -> startActivity(new Intent(Subject.this, Topic.class)));
        binding.buttonLogout.setOnClickListener( v -> logOut() );
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Subject.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
