package com.bottom.files;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private File fileName;
    private Button saveFile,loadFile;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveFile  = findViewById(R.id.saveFile);
        loadFile  = findViewById(R.id.loadFile);
        videoView = findViewById(R.id.vidView);

        saveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveVideoToCache();
            }
        });
        loadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSavedVideo();
            }
        });


    }

    private void saveVideoToCache() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.fi);
            File tempDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + "sample.mp4");
            OutputStream outputStream = new FileOutputStream(tempDir);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            fileName = tempDir;
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Log.d("TAG","File name " + e.getMessage());
        }
    }
    private void loadSavedVideo(){
        // Display the video from the temporary file in the VideoView
        if(fileName != null){
            videoView.setVideoURI(Uri.fromFile(fileName));
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
            videoView.start();
        } else {
            Toast.makeText(this,"File is null",Toast.LENGTH_LONG).show();
        }
    }

}