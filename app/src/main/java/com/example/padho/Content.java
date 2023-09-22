package com.example.padho;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.ui.PlayerView;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;

public class Content extends AppCompatActivity {
    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        // Initialize ExoPlayer
        PlayerView playerViewAndroid = findViewById(R.id.player_view);
        player = new ExoPlayer.Builder(this).build();
        playerViewAndroid.setPlayer(player);

        // Firebase Storage reference to your video
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference videoRef = storageRef.child("Class_1/Videos/This - That  English Grammar & Composition Grade 1  Periwinkle.mp4");

        // Define the custom download directory path
        String customDownloadDirectory = "khuliKitab/video/";
        String videoFileName = "This - That  English Grammar & Composition Grade 1  Periwinkle.mp4";

        // Convert the custom path to a File object
        File downloadDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), customDownloadDirectory);
        // Check if the video is already downloaded
        File localVideoFile = new File(downloadDirectory, videoFileName);

        if (localVideoFile.exists()) {
            // Toast.makeText(Content.this, "Video already downloaded", Toast.LENGTH_SHORT).show();
            // Video is already downloaded, load it from local storage
            MediaItem mediaItem = MediaItem.fromUri(Uri.fromFile(localVideoFile));
            player.setMediaItem(mediaItem);
            player.prepare();
            player.setPlayWhenReady(false); // Auto-play
        } else {
            //Toast.makeText(Content.this, "Downloading video", Toast.LENGTH_SHORT).show();
            // Video is not downloaded, load it from online source
            videoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                MediaItem mediaItem = MediaItem.fromUri(uri);
                player.setMediaItem(mediaItem);
                player.prepare();
                player.setPlayWhenReady(true); // Auto-play
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
