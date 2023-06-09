package com.example.savethecat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameView extends View {
    Bitmap background, ground, cat;
    Rect rectBackground, rectGround;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    float TEXT_SIZE = 120;
    int points = 0;
    int life = 3;
    static int dWidth, dHeight;
    Random random;
    float catX, catY;
    float oldX;
    float oldCatX;
    ArrayList<Spike> spikes;
    ArrayList<Explosion> explosions;
    private int time_alive;
    private String mode;
    private String died_by;

    public boolean isEasyMode;

    private Timer t;




    public GameView(Context context, boolean isEasyMode) {
        super(context);
        this.context = context;

        this.isEasyMode = isEasyMode;

        time_alive = 0;
        t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                time_alive++;
            }
        }, 0, 1000);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        // TEST
        if(background == null){
            Toast.makeText(context, "Błąd wczytywania grafik", Toast.LENGTH_SHORT).show();
        }
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        // TEST
        if(ground == null){
            Toast.makeText(context, "Błąd wczytywania grafik", Toast.LENGTH_SHORT).show();
        }
        cat = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        // TEST
        if(cat == null){
            Toast.makeText(context, "Błąd wczytywania grafik", Toast.LENGTH_SHORT).show();
        }
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rectBackground = new Rect(0,0, dWidth, dHeight);
        rectGround = new Rect(0, dHeight- ground.getHeight(), dWidth, dHeight);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        textPaint.setColor(Color.rgb(255, 165, 0));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.kenney_blocks));
        healthPaint.setColor(Color.GREEN);
        random = new Random();
        catX = dWidth / 2 - cat.getWidth() / 2;
        catY = dHeight - ground.getHeight() - cat.getHeight();
        spikes = new ArrayList<>();
        explosions = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            Spike spike = new Spike(context,isEasyMode);
            spikes.add(spike);
        }
        if(spikes.isEmpty()){
            // TEST
            Toast.makeText(context, "Błąd wczytywania przeszkód", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rectBackground, null);
        canvas.drawBitmap(ground, null, rectGround, null);
        canvas.drawBitmap(cat, catX, catY, null);
        if(spikes.size() == 0){
            // TEST

            Toast.makeText(context, "Błąd wczytywania przeszkód", Toast.LENGTH_SHORT).show();

        }
        for(int i = 0; i < spikes.size(); i++){
            canvas.drawBitmap(spikes.get(i).getSpike(spikes.get(i).spikeFrame), spikes.get(i).spikeX, spikes.get(i).spikeY, null);
            spikes.get(i).spikeFrame++;
            if(spikes.get(i).spikeFrame > 2){
                spikes.get(i).spikeFrame = 0;
            }
            spikes.get(i).spikeY += spikes.get(i).spikeVelocity;
            if(spikes.get(i).spikeY + spikes.get(i).getSpikeHeight() >= dHeight - ground.getHeight()){
                points += 10;
                Explosion explosion = new Explosion(context);
                explosion.explosionX = spikes.get(i).spikeX;
                explosion.explosionY = spikes.get(i).spikeY;
                explosions.add(explosion);
                spikes.get(i).resetPosition();

            }
        }

        for(int i = 0; i < spikes.size(); i++) {
            if (spikes.get(i).spikeX + spikes.get(i).getSpikeWidth() >= catX &&
                    spikes.get(i).spikeX <= catX + cat.getWidth() &&
                    spikes.get(i).spikeY + spikes.get(i).getSpikeWidth() >= catY &&
                    spikes.get(i).spikeY + spikes.get(i).getSpikeWidth() <= catY + cat.getHeight()) {
                    life--;
                    spikes.get(i).resetPosition();
                    if(life == 0){

                        t.cancel();
                        t.purge();
                        if(isEasyMode == true){
                            mode = "easy";
                            died_by = "barrel";
                        }else{
                            mode = "hard";
                            died_by = "anvil";
                        }

                        MyDatabaseHelper myDB = new MyDatabaseHelper(context);
                        myDB.addRun(mode, points, time_alive, died_by);

                        Intent intent = new Intent(context, GameOver.class);
                        intent.putExtra("points",points);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
            }
        }
        for(int i = 0; i<explosions.size();i++) {
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).explosionX,
                    explosions.get(i).explosionY, null);
            explosions.get(i).explosionFrame++;
            if(explosions.get(i).explosionFrame> 3){
                explosions.remove(i);
            }
        }

        if(life == 2){
            healthPaint.setColor(Color.YELLOW);
        }else if(life == 1){
            healthPaint.setColor(Color.RED);
        }
        canvas.drawRect(dWidth-200,30,dWidth-200+60*life,80,healthPaint);
        canvas.drawText(""+points,20, TEXT_SIZE, textPaint);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if(touchY >= catY){
            int action = event.getAction();
            if(action == MotionEvent.ACTION_DOWN){
                oldX = event.getX();
                oldCatX= catX;
            }
            if(action == MotionEvent.ACTION_MOVE){
                float shift = oldX- touchX;
                float newCatX = oldCatX - shift;
                if(newCatX <= 0) {
                    catX=0;}
                else if(newCatX >= dWidth - cat.getWidth()){
                    catX = dWidth - cat.getWidth();
                }
                else
                    catX = newCatX;
            }
        }
        return true;
    }

}

           