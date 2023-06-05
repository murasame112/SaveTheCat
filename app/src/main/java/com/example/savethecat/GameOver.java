package com.example.savethecat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;
    TextView tvHighest;

    SharedPreferences sharedPreferences;
    ImageView ivNewHighest;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        tvPoints = findViewById(R.id.tvPoints);
        // TEST
        if(tvPoints == null){
            Toast.makeText(this, "Błąd wczytywania widoku punktów", Toast.LENGTH_SHORT).show();
        }
        tvHighest = findViewById(R.id.tvHighest);
        // TEST
        if(tvHighest == null){
            Toast.makeText(this, "Błąd wczytywania widoku rekordu", Toast.LENGTH_SHORT).show();
        }
        ivNewHighest = findViewById(R.id.ivNewHighest);
        int points = getIntent().getExtras().getInt("points");
        tvPoints.setText("" + points);
        sharedPreferences = getSharedPreferences("my_pref",0);
        int highest = sharedPreferences.getInt("Twój rekord: ",0);
        if(points> highest) {
            ivNewHighest.setVisibility(View.VISIBLE);
            highest = points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Twój rekord: ",highest);
            editor.commit();
        }
            tvHighest.setText("" + highest);


    }
    public void restart(View view){
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view){
        finish();
        System.exit(0);

    }
}
