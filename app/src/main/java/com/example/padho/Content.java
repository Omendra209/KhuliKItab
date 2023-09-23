package com.example.padho;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import com.example.padho.databinding.ActivityContentBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;

public class Content extends AppCompatActivity {
    private ActivityContentBinding binding;
    private ExoPlayer player;
    private StorageReference videoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        initialiseFirebase();
        initializePlayer();

    }

    private void setListeners() {
        binding.textCon.setOnClickListener(v ->startActivity(new Intent(Content.this, TextContent.class)));
        binding.goToQuiz.setOnClickListener(v ->startActivity(new Intent(Content.this, Quiz.class)));
    }

    private void initialiseFirebase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        videoRef = storageRef.child("Class_1/Videos/This - That  English Grammar & Composition Grade 1  Periwinkle.mp4");
    }

    private void initializePlayer() {
        player = new ExoPlayer.Builder(this).build();
        binding.playerView.setPlayer(player);

        // Define the custom download directory path
        String customDownloadDirectory = "khuliKitab/video/";
        String videoFileName = "This - That  English Grammar & Composition Grade 1  Periwinkle.mp4";

        // Convert the custom path to a File object
        File downloadDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), customDownloadDirectory);
        // Check if the video is already downloaded
        File localVideoFile = new File(downloadDirectory, videoFileName);

        if (localVideoFile.exists()) {
            MediaItem mediaItem = MediaItem.fromUri(Uri.fromFile(localVideoFile));
            player.setMediaItem(mediaItem);
            player.prepare();
            player.setPlayWhenReady(false); // Auto-play
        } else {
            videoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                MediaItem mediaItem = MediaItem.fromUri(uri);
                player.setMediaItem(mediaItem);
                player.prepare();
                player.setPlayWhenReady(true);
                downloadVideoLocally(uri, customDownloadDirectory, videoFileName);
            }).addOnFailureListener(e -> Toast.makeText(Content.this, "Error loading video", Toast.LENGTH_SHORT).show());
        }
    }


    private void downloadVideoLocally(Uri videoUri, String downloadDirectory, String videoFileName) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(videoUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(true)
                .setTitle("Downloading Chapter Video")
                .setDescription("Chapter Video is being downloaded")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadDirectory + videoFileName);
        downloadManager.enqueue(request);
    }


    protected void onPause() {
        super.onPause();
        player.release();
    }

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
