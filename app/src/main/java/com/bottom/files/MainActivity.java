package com.bottom.files;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private File tempoFile;
    private File finalFile;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saveFile = findViewById(R.id.saveFile);
        Button loadFile = findViewById(R.id.loadFile);
        videoView = findViewById(R.id.vidView);

        saveFile.setOnClickListener(v -> {
            try {
                File dcimTestDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "TEST");
                if (!dcimTestDir.exists()) {
                    boolean wasSuccessful =  dcimTestDir.mkdirs();
                }

                File destFile = new File(dcimTestDir, "Sample.mp4");
                if (!destFile.exists()) {
                    boolean wasSuccessful = destFile.createNewFile();
                }

                FileInputStream inputStream = new FileInputStream(tempoFile);
                FileOutputStream outputStream = new FileOutputStream(destFile);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                Log.d("TAG", "File moved successfully to: " + destFile.getAbsolutePath());
                finalFile  = destFile;
                // Close streams
                inputStream.close();
                outputStream.close();


            } catch (IOException e) {
                Log.d("TAG", "File Exception " + e.getMessage());
            }
        });
        loadFile.setOnClickListener(v -> loadSavedVideo());

        saveVideoToCache();
    }

    // TODO : Save video to temporary directory
    private void saveVideoToCache() {
        try {
            String fileName = "Dummy" + System.currentTimeMillis();
            InputStream inputStream = getResources().openRawResource(R.raw.fi);
            File tempFile = File.createTempFile(fileName, ".mp4", getCacheDir());
            OutputStream outputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            tempoFile = tempFile;
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Log.d("TAG","File Exception " + e.getMessage());
        }
    }
    private void loadSavedVideo(){
        if(finalFile != null){
            videoView.setVideoURI(Uri.fromFile(finalFile));
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
            videoView.start();
        } else {
            Toast.makeText(this,"File is null",Toast.LENGTH_LONG).show();
        }
    }


}


