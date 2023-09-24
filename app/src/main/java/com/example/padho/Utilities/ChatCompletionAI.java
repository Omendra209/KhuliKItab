package com.example.padho.Utilities;

import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatCompletionAI {
    public static void testAPI() {
        Log.d("TAG", "testAPI: ");
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.get("application/json");

        // Prepare the request body
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-4");
        requestBody.addProperty("messages", String.valueOf(createMessagesJson()));
        RequestBody body = RequestBody.create(requestBody.toString(), mediaType);

        // Prepare the request
        Request request = new Request.Builder()
                .url("https://cock-za06.onrender.com/v1/chat/completions")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try {
            // Send the request and get the response
            Log.d("TAG", "Sending Request ");


            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Response response = client.newCall(request).execute();

            // Process the response
            if (response.isSuccessful()) {
                // The response is successful
                Log.d("TAG", "Responses");
                String responseBody = response.body().string();
                JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
                JsonArray choices = jsonResponse.getAsJsonArray("choices");
                if (choices.size() > 0) {
                    JsonObject firstChoice = choices.get(0).getAsJsonObject();
                    JsonObject message = firstChoice.getAsJsonObject("message");
                    String content = message.get("content").getAsString();
//                    System.out.println(content);
                    Log.d("TAG", content);
                }
            } else {
                // Handle the error response
//                System.out.println("Error: " + response.code() + " - " + response.message());
                Log.d("TAG", "Error: " + response.code() + " - " + response.message());
            }
        } catch (Exception e) {
//            Log.e("TAG", "Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static JsonObject createMessagesJson() {
        JsonObject messages = new JsonObject();

        JsonArray messageArray = new JsonArray();

        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", "There are 50 books in a library. Sam decides to read 5 of the books. How many books are there now? If there are 45 books, say \"1\". Else, if there is the same amount of books, say \"2\".");

        messageArray.add(userMessage);

        messages.add("messages", messageArray);

        return messages;
    }

    public static String chatCompletionRequest(String messageRequestText) {
        Log.d("TAG", "chatCompletionRequestInitiated ");
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.get("application/json");

        // Prepare the request body
        Log.d("TAG", "preparingRequestBody ");
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-4 ");

        JsonArray messagesArray = new JsonArray();

        // Add user messages to the array
        Log.d("TAG", "addingUserMessages ");
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "assistant");
        userMessage.addProperty("content", messageRequestText);
        messagesArray.add(userMessage);

        // Add assistant messages to the array if needed
        // JsonObject assistantMessage = new JsonObject();
        // assistantMessage.addProperty("role", "assistant");
        // assistantMessage.addProperty("content", "Assistant message content");
        // messagesArray.add(assistantMessage);

        Log.d("TAG", "addingMessages ");
        requestBody.add("messages", messagesArray);
        Log.d("TAG", "creating request body ");
        RequestBody body = RequestBody.create(requestBody.toString(), mediaType);

        // Prepare the request
        Log.d("TAG", "preparingRequest ");
        Request request = new Request.Builder()
                .url("https://cock-za06.onrender.com/v1/chat/completions")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try {
            // Send the request and get the response

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Response response = client.newCall(request).execute();
            // Process the response
            if (response.isSuccessful()) {
                // The response is successful
                Log.d("TAG", "Response Successfully");;
                String responseBody = response.body().string();
                Log.d("TAG", responseBody);
                JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
                JsonArray choices = jsonResponse.getAsJsonArray("choices");
                if (choices.size() > 0) {
                    JsonObject firstChoice = choices.get(0).getAsJsonObject();
                    JsonObject message = firstChoice.getAsJsonObject("message");
                    String content = message.get("content").getAsString();
                    Log.d("TAG", content);
                    return content;
                    // Handle the generated response content
                    // For example, update UI with the response
                    // textView.setText(content);
                }
            } else {
                // Handle the error response
                Log.d("TAG", "Error: " + response.code() + " - " + response.message());
            }
        } catch (Exception e) {
            Log.e("TAG", "Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return "Error";
    }

}