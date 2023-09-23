package com.example.padho;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.padho.databinding.ActivityTextContentBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TextContent extends AppCompatActivity {
    private static final String TAG = "TextFileActivity";
    private ActivityTextContentBinding binding;
    private String textFileName;
    File localTextFile;
    private String customDownloadDirectoryFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Text Fetch Code Start From Here
        customDownloadDirectoryFolder = "khulikitab/text/";
        textFileName = "text1.txt";

        // Convert the custom path to a File object
        File downloadDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), customDownloadDirectoryFolder);
        // Check if the video is already downloaded
        localTextFile = new File(downloadDirectory, textFileName);
        if (localTextFile.exists()) {
            String localText = loadTextFromLocal();
            // Text file is available locally, use it
            displayText(localText);
        } else {
            // Text file is not available locally, fetch it from Firebase Storage
            fetchTextFromFirebase();
        }
    }
    private String loadTextFromLocal() {
        try {
            FileInputStream fis = new FileInputStream(localTextFile);
            byte[] buffer = new byte[(int) localTextFile.length()];
            int bytesRead = fis.read(buffer);
            fis.close();
            if (bytesRead != -1) {
                return new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
            } else {
                Log.e(TAG, "Error reading text file: End of stream reached");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading text file: " + e.getMessage());
        }
        return null;
    }
    private void displayText(String text) {
        // TODO: Display the text content in your UI (e.g., TextView)
        binding.topicText.setText(text);
    }


    private void fetchTextFromFirebase() {
        // Firebase Storage reference to your text file
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference textFileRef = storageRef.child("Class_1/Text/topic1Text.txt");
        textFileRef.getBytes(1024 * 1024) // Set the maximum file size (1MB in this case)
                .addOnSuccessListener(bytes -> {
                    String text = new String(bytes, StandardCharsets.UTF_8);
                    displayText(text);
                })
                .addOnFailureListener(exception -> Log.e(TAG, "Error downloading text file: " + exception.getMessage()));
        textFileRef.getDownloadUrl().addOnSuccessListener(uri -> downloadTextFileLocally(uri,customDownloadDirectoryFolder,textFileName));
    }

    private void downloadTextFileLocally(Uri fileUri, String downloadDirectory, String fileName) {
        // Create a DownloadManager for downloading the text file
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        // Create a request for downloading
        DownloadManager.Request request = new DownloadManager.Request(fileUri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(true)
                .setTitle("Downloading Text File")
                .setDescription("Download in progress")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // Set the destination directory to the public external storage directory
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadDirectory + fileName);
        // Enqueue the download and start it
        downloadManager.enqueue(request);

    }

}