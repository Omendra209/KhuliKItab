package com.example.padho;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Content extends AppCompatActivity {
    private VideoView videoView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        videoView=findViewById(R.id.Vid);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference videoRef = storageRef.child("Class_1/Videos/This - That  English Grammar & Composition Grade 1  Periwinkle.mp4");

        videoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                videoView.setVideoURI(uri);
                videoView.start();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Content.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
