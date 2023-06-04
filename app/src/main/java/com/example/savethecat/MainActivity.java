package com.example.savethecat;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private boolean isEasyMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    public void start(View view) {
//        GameView gameView = new GameView(this);
//        setContentView(gameView);
        setContentView(R.layout.choose_mode);

    }

    public void stats(View view) {
     setContentView(R.layout.stats);
    }

    public void backToMenu(View view){
        setContentView(R.layout.activity_main);
    }

    public void easyMode(View view) {
        isEasyMode = true;
        startGame();
    }

    public void hardMode(View view) {
        isEasyMode = false;
        startGame();
    }

    private void startGame() {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("isEasyMode", isEasyMode);
        startActivity(intent);
    }



}