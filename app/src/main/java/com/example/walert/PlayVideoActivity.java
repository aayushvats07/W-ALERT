package com.example.walert;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class PlayVideoActivity extends AppCompatActivity {

    private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        mVideoView = (VideoView)findViewById(R.id.videoView);

        Uri videoUri = Uri.parse(getIntent().getExtras().getString("videoUri"));
        mVideoView.setVideoURI(videoUri);
        mVideoView.start();
    }
}