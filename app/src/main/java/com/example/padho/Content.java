package com.example.padho;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;


public class Content extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private CardView cardView1;
    private CardView cardView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        // Initialize CardView
        cardView1 = findViewById(R.id.textCon);
        cardView1.setOnClickListener(view -> {
            startActivity(new Intent(Content.this, textContent.class));
        });
        // Initialize CardView
        cardView2 = findViewById(R.id.Go_To_Quiz);
        cardView2.setOnClickListener(view -> {
            startActivity(new Intent(Content.this, Quiz.class));
        });

        // Initialize ExoPlayer
        playerView = findViewById(R.id.player_view);
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // Firebase Storage reference to your video
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference videoRef = storageRef.child("Class_1/Videos/This - That  English Grammar & Composition Grade 1  Periwinkle.mp4");

        // Define the custom download directory path
        String customDownloadDirectory = "khulikitab/video/";

        // Convert the custom path to a File object
        File downloadDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), customDownloadDirectory);

        // Check if the video is already downloaded
        String videoFileName = "This - That  English Grammar & Composition Grade 1  Periwinkle.mp4";
        File localVideoFile = new File(downloadDirectory, videoFileName);

        if (localVideoFile.exists()) {
//            Toast.makeText(Content.this, "Video already downloaded", Toast.LENGTH_SHORT).show();
            // Video is already downloaded, load it from local storage
            MediaItem mediaItem = MediaItem.fromUri(Uri.fromFile(localVideoFile));
            player.setMediaItem(mediaItem);
            player.prepare();
            player.setPlayWhenReady(false); // Auto-play
        } else {
//            Toast.makeText(Content.this, "Downloading video", Toast.LENGTH_SHORT).show();
            // Video is not downloaded, load it from online source
            videoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                MediaItem mediaItem = MediaItem.fromUri(uri);
                player.setMediaItem(mediaItem);
                player.prepare();
                player.setPlayWhenReady(false); // Auto-play

                // Start downloading the video
                downloadVideoLocally(uri, customDownloadDirectory, videoFileName);
            }).addOnFailureListener(e -> {
                Toast.makeText(Content.this, "Error loading video", Toast.LENGTH_SHORT).show();
            });
        }

    }

    private void downloadVideoLocally(Uri videoUri, String downloadDirectory, String videoFileName) {
        // Create a DownloadManager for downloading the video
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        // Create a request for downloading
        DownloadManager.Request request = new DownloadManager.Request(videoUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(true)
                .setTitle("Downloading Video")
                .setDescription("Download in progress")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Set the destination directory to the public external storage directory
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadDirectory+videoFileName);

        // Enqueue the download and start it
        downloadManager.enqueue(request);

        // Optionally, you can listen for download completion here
        // For simplicity, we won't include that in this code

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
