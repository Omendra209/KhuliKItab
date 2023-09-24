package com.example.padho;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.padho.Utilities.ChatCompletionAI;
import com.example.padho.databinding.ActivityChatAiBinding;

public class ChatAI extends AppCompatActivity {

    private ActivityChatAiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatAiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        TranslateUtility.translateText(EN,HI,"How are you");
        setListeners();
    }

    private final Handler handler = new Handler(Looper.getMainLooper());
    private void setListeners() {
        binding.buttonAsk.setOnClickListener(v -> {
            String message = binding.editTextQuery.getText().toString();
            if (!message.isEmpty()) {
                isLoading (true);
                new Thread(() -> {
                    String response = ChatCompletionAI.chatCompletionRequest(message);
//                    String translatedResponse = TranslateUtility.gptTranslateTextStudyContent("10th", "English", response, "Hindi");
//                        String translatedResponse = TranslateUtility.translateTextGoogle(this, response, "hi");

                    handler.post(() -> {
                        binding.textViewResponse.setText(response);
//                        binding.textViewResponse.setText(translatedResponse);
                        isLoading(false);
                    });
                }).start();
            }
        });
    }

    private void isLoading(boolean isLoading) {
        if (isLoading) {
            binding.buttonAsk.setEnabled(false);
            binding.editTextQuery.setEnabled(false);
            binding.textViewResponse.setText("");
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.buttonAsk.setEnabled(true);
            binding.editTextQuery.setEnabled(true);
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}