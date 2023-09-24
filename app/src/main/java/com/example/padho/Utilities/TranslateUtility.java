package com.example.padho.Utilities;


import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.example.padho.R;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TranslateUtility {
    public static String gptTranslateTextStudyContent(String classLevel, String subject, String textContent, String language) {
        Log.d("TAG", "translateTextStudyContent: ");
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.get("application/json");
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-4 ");
        JsonArray messages = new JsonArray();
        JsonObject message = new JsonObject();
        message.addProperty("role", "assistant");
        message.addProperty("content", "I am a " + classLevel + " student and I want to study " + subject + ". please translate this text to " + language + " " + textContent);
//        message.addProperty("content", "I am a " + classLevel + " student and I want to study " + subject + ". I have a text Study Material in another Language i want to translate it to my Native language that is " + language
//                + ". Here is the study material that i want to be translated " + textContent);
        messages.add(message);
        requestBody.add("messages", messages);
        Request request = new Request.Builder()
                .url("https://cock-za06.onrender.com/v1/chat/completions")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(requestBody.toString(), mediaType))
                .build();

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.d("TAG", "Response Success");
                String responseBody = response.body().string();
                JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
                JsonArray choices = jsonResponse.getAsJsonArray("choices");
                if (!choices.isEmpty()) {
                    JsonObject firstChoice = choices.get(0).getAsJsonObject();
                    JsonObject apiMessage = firstChoice.getAsJsonObject("message");
                    Log.d("TAG", apiMessage.get("content").getAsString());
                    return apiMessage.get("content").getAsString();
                } else {
                    Log.e("TAG", "Error: " + response.code() + " - " + response.message());
                    return "Error: " + response.code() + " - " + response.message();
                }
            } else {
                Log.e("TAG", "Error: " + response.code() + " - " + response.message());
                return "Error: " + response.code() + " - " + response.message();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public static String translateTextGoogle(Context context, String text, String targetLanguage) {
        try {
            // Load the API key credentials
            InputStream credentialsStream = context.getResources().openRawResource(R.raw.secret);
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
            // Initialize the Translation client with the credentials
            Translate translate = TranslateOptions.newBuilder().setCredentials(credentials).build().getService();
            Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage(targetLanguage));
            String translatedText = translation.getTranslatedText();
            return translatedText;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
