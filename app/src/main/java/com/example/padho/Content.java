package com.example.padho;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class Content extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private TextView textView;
    private static final String TAG = "TextFileActivity";
    private CardView cardView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

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

        //
        Intent intent = getIntent();
        if (intent != null) {
            String receivedText = intent.getStringExtra("textKey");

            if (receivedText != null) {
                // Do something with the received text
                // For example, set it to a TextView in ActivityB
                TextView receivedTextView = findViewById(R.id.heading);
                receivedTextView.setText(receivedText);
            }
        }


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
        StorageReference videoRef = storageRef.child("Class_1/Videos/English/Chapter_1_Topic_1_Video.mp4");

        // Define the custom download directory path
        String customDownloadDirectory = "khulikitab/video/";

        // Convert the custom path to a File object
        File downloadDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), customDownloadDirectory);

        // Check if the video is already downloaded
        String videoFileName = "Chapter_1_Topic_1_Video.mp4";
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
                downloadTextFileLocally(uri,customDownloadDirectoryDoc,"text1Text.txt");
            }
        });
    }
    private String loadTextFromLocal(String customDirectory) {
        try {
            File file = new File(getFilesDir()+File.separator+customDirectory, "text1Text.txt");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
