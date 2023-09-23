package com.example.padho;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import com.example.padho.databinding.ActivityContentBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Content extends AppCompatActivity {
    private ActivityContentBinding binding;
    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        initializeTextContent();
        initializeVideoPlayer();
    }

    private void setListeners() {
//        binding.goToQuiz.setOnClickListener(v ->startActivity(new Intent(Content.this, TextContent.class)));
//        binding.textCon.setOnClickListener(v ->startActivity(new Intent(Content.this, TextContent.class)));
        binding.goToQuiz.setOnClickListener(v ->startActivity(new Intent(Content.this, Quiz.class)));
    }

    private StorageReference initializeFirebaseStorageReference(String storageRefPath) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        return storageRef.child(storageRefPath);
    }

    private void initializeTextContent() {
        // Define the custom download directory path
        String customDownloadDirectory = "khuliKitab/text/";
        String fileName = "Chapter_1_Topic_1_Text.txt";
        File localFile = fullFilePath(customDownloadDirectory, fileName);

        if (localFile.exists()) {
            String localText = loadTextFromLocal(localFile);
//             Text file is available locally, use it
            binding.topicText.setText(localText);
        } else {
            StorageReference textFileRef = initializeFirebaseStorageReference("Class_1/Text/topic1Text.txt");
            textFileRef.getBytes(1024 * 1024) // Set the maximum file size (1MB in this case)
                    .addOnSuccessListener(bytes -> {
                        String text = new String(bytes, StandardCharsets.UTF_8);
                        binding.topicText.setText(text);
                    })
                    .addOnFailureListener(exception -> Log.e("TAG", "Error downloading text file: " + exception.getMessage()));
            textFileRef.getDownloadUrl().addOnSuccessListener(uri -> downloadFileLocally(uri,customDownloadDirectory,fileName, "Downloading "+fileName, "Download in progress"));
        }
    }

    private String loadTextFromLocal(File localTextFile) {
        try {
            FileInputStream fis = new FileInputStream(localTextFile);
            byte[] buffer = new byte[(int) localTextFile.length()];
            int bytesRead = fis.read(buffer);
            fis.close();
            if (bytesRead != -1) {
                return new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
            } else {
                Log.e("TAG", "Error reading text file: End of stream reached");
            }
        } catch (IOException e) {
            Log.e("TAG", "Error reading text file: " + e.getMessage());
        }
        return null;
    }

    private void initializeVideoPlayer() {
        player = new ExoPlayer.Builder(this).build();
        binding.playerView.setPlayer(player);

        // Define the custom download directory path
        String customDownloadDirectory = "khuliKitab/video/";
        String fileName = "Chapter_1_Topic_1_Video.mp4";
        File localFile = fullFilePath(customDownloadDirectory, fileName);

        if (localFile.exists()) {
            MediaItem mediaItem = MediaItem.fromUri(Uri.fromFile(localFile));
            player.setMediaItem(mediaItem);
            player.prepare();
            player.setPlayWhenReady(false); // Auto-play
        } else {
            StorageReference videoRef = initializeFirebaseStorageReference("Class_1/Videos/English/Chapter_1_Topic_1_Video.mp4");

            videoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                MediaItem mediaItem = MediaItem.fromUri(uri);
                player.setMediaItem(mediaItem);
                player.prepare();
                player.setPlayWhenReady(true);
                downloadFileLocally(uri, customDownloadDirectory, fileName, "Downloading "+fileName, "Download in progress");
            }).addOnFailureListener(e -> Toast.makeText(Content.this, "Error loading video", Toast.LENGTH_SHORT).show());
        }
    }

    private File fullFilePath(String fileTypeDownloadDirectory, String videoFileName) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileTypeDownloadDirectory + videoFileName);
    }

    private void downloadFileLocally(Uri fileUri, String downloadDirectory, String videoFileName, String downloadTitle, String downloadDescription) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(fileUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(true)
                .setTitle(downloadTitle)
                .setDescription(downloadDescription)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadDirectory + videoFileName);
        downloadManager.enqueue(request);
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}