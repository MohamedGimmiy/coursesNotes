package com.example.coursesnotes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.coursesnotes.MainActivity;
import com.example.coursesnotes.R;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HidingTitleBar();
        setContentView(R.layout.activity_splash_screen);
        SplashScreenNavigation();

    }


    private void SplashScreenNavigation() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(
                        SplashScreen.this,
                        MainActivity.class));
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void HidingTitleBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
    }
}