package com.example.padho;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Content extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        // Initialize Exoplayer
        playerView=findViewById(R.id.player_view);
        player=new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference();
//        StorageReference videoRef = storageRef.child("Class_1/Videos/This - That  English Grammar & Composition Grade 1  Periwinkle.mp4");
        String videoRef = "gs://padho-52dba.appspot.com/Class_1/Videos/This - That  English Grammar & Composition Grade 1  Periwinkle.mp4";
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoRef));
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
//        videoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                videoView.setVideoURI(uri);
//                videoView.start();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Content.this, "error", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        player.release();
    }
}
