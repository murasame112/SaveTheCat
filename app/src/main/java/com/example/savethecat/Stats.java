package com.example.savethecat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class Stats extends AppCompatActivity {

    MyDatabaseHelper myDB;
    ArrayList<String> run_id, run_points, run_mode, run_died_by, run_time_alive;

    private TextView tvHighest, tvHighestEasy, tvHighestHard, tvlongestTimeAlive, tvlongestTimeAliveEasy, tvlongestTimeAliveHard, tvtimeInGame, tvtimeInGameEasy, tvtimeInGameHard, tvdeathCount, tvdeathByBarrel, tvdeathByAnvil;
    private int highest, highestEasy, highestHard, longestTimeAlive, longestTimeAliveEasy, longestTimeAliveHard, timeInGame, timeInGameEasy, timeInGameHard, deathCount, deathByBarrel, deathByAnvil;

    private int points, time_alive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //recyclerView = findViewById(R.id.recyclerView);
        myDB = new MyDatabaseHelper(Stats.this);
        run_id = new ArrayList<>();
        run_points = new ArrayList<>();
        run_mode = new ArrayList<>();
        run_died_by = new ArrayList<>();
        run_time_alive = new ArrayList<>();

        highest = 0;
        highestEasy = 0;
        highestHard = 0;
        longestTimeAlive = 0;
        longestTimeAliveEasy = 0;
        longestTimeAliveHard = 0;
        timeInGame = 0;
        timeInGameEasy = 0;
        timeInGameHard = 0;
        deathCount = 0;
        deathByBarrel = 0;
        deathByAnvil = 0;

        tvHighest = findViewById(R.id.tvHighest);
        tvHighestEasy = findViewById(R.id.tvHighestEasy);
        tvHighestHard = findViewById(R.id.tvHighestHard);
        tvlongestTimeAlive = findViewById(R.id.longestTimeAlive);
        tvlongestTimeAliveEasy = findViewById(R.id.longestTimeAliveEasy);
        tvlongestTimeAliveHard = findViewById(R.id.longestTimeAliveHard);
        tvtimeInGame = findViewById(R.id.timeInGame);
        tvtimeInGameEasy = findViewById(R.id.timeInGameEasy);
        tvtimeInGameHard = findViewById(R.id.timeInGameHard);
        tvdeathCount = findViewById(R.id.deathCount);
        tvdeathByBarrel = findViewById(R.id.deathByBarrel);
        tvdeathByAnvil = findViewById(R.id.deathByAnvil);
        Cursor cursor = myDB.readAllData();
        // TEST
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){

                points = Integer.parseInt(cursor.getString(1));
                time_alive = Integer.parseInt(cursor.getString(4));
                if(highest < points ){
                    highest = points;
                }

                if(longestTimeAlive < time_alive){
                    longestTimeAlive = time_alive;
                }

                timeInGame += time_alive;

                deathCount++;

                if(Objects.equals(cursor.getString(2), "easy")){
                    if(highestEasy < points){
                        highestEasy = points;
                    }

                    if(longestTimeAliveEasy < time_alive){
                        longestTimeAliveEasy = time_alive;
                    }

                    timeInGameEasy += time_alive;

                    deathByBarrel++;

                }else if(Objects.equals(cursor.getString(2), "hard")){
                    if(highestHard < points){
                        highestHard = points;
                    }

                    if(longestTimeAliveHard < time_alive){
                        longestTimeAliveHard = time_alive;
                    }

                    timeInGameHard += time_alive;

                    deathByAnvil++;
                }
            }

        }

        tvHighest.setText(Integer.toString(highest));
        tvHighestEasy.setText(Integer.toString(highestEasy));
        tvHighestHard.setText(Integer.toString(highestHard));
        tvlongestTimeAlive.setText(Integer.toString(longestTimeAlive));
        tvlongestTimeAliveEasy.setText(Integer.toString(longestTimeAliveEasy));
        tvlongestTimeAliveHard.setText(Integer.toString(longestTimeAliveHard));
        tvtimeInGame.setText(Integer.toString(timeInGame));
        tvtimeInGameEasy.setText(Integer.toString(timeInGameEasy));
        tvtimeInGameHard.setText(Integer.toString(timeInGameHard));
        tvdeathCount.setText(Integer.toString(deathCount));
        tvdeathByBarrel.setText(Integer.toString(deathByBarrel));
        tvdeathByAnvil.setText(Integer.toString(deathByAnvil));

    }

}
