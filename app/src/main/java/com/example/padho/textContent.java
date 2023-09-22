package com.example.padho;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class textContent extends AppCompatActivity {
    private TextView textView;
    private static final String TAG = "TextFileActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_content);

        //Initialize TextView
        textView=findViewById(R.id.Topic_Text);



        //Text Fetch Code Start From Here
        String customDownloadDirectoryDoc = "khulikitab/text/";
        String localText = loadTextFromLocal(customDownloadDirectoryDoc);
        if (localText != null) {
            // Text file is available locally, use it
            displayText(localText);
        } else {
            // Text file is not available locally, fetch it from Firebase Storage
            fetchTextFromFirebase();
        }
    }

    private void fetchTextFromFirebase() {
        // Firebase Storage reference to your text file
        String customDownloadDirectoryDoc = "khulikitab/text/";
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference textFileRef = storageRef.child("Class_1/Text/topic1Text.txt");
        textFileRef.getBytes(1024 * 1024) // Set the maximum file size (1MB in this case)
                .addOnSuccessListener(bytes -> {
                    String text = new String(bytes, StandardCharsets.UTF_8);
                    // Handle the text content (e.g., save it locally and display)
                    displayText(text);
                })
                .addOnFailureListener(exception -> {
                    // Handle any errors
                    Log.e(TAG, "Error downloading text file: " + exception.getMessage());
                });
        textFileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                downloadTextFileLocally(uri,customDownloadDirectoryDoc,"text1.txt");
            }
        });
    }
    private String loadTextFromLocal(String customDirectory) {
        try {
            File file = new File(getFilesDir()+File.separator+customDirectory, "text1.txt");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);
            fis.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void displayText(String text) {
        // TODO: Display the text content in your UI (e.g., TextView)
        textView.setText(text);
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
