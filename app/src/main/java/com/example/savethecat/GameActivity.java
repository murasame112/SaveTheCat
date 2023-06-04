package com.example.savethecat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isEasyMode = getIntent().getBooleanExtra("isEasyMode", false);


        gameView = new GameView(this, isEasyMode);
        setContentView(gameView);
    }

    // ...
}

