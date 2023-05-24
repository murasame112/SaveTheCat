package com.example.savethecat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;


public class GameView extends View {
    Bitmap background, ground, cat;
    Rect rectBackground, rectGround;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healtPaint = new Paint();
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

    public GameView(Context context) {
        super(context);
        this.context = context;
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        cat = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
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
        healtPaint.setColor(Color.GREEN);
        random = new Random();
        catX = dWidth / 2 - cat.getWidth() / 2;
        catY = dHeight - ground.getHeight() - cat.getHeight();
        spikes = new ArrayList<>();
        explosions = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            Spike spike = new Spike(context);
            spikes.add(spike);
        }


    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rectBackground, null);
        canvas.drawBitmap(ground, null, rectGround, null);
        canvas.drawBitmap(cat, catX, catY, null);
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

        for(int i = 0; i < spikes.size(); i++){
            if(spikes.get(i).spikeX + spikes.get(i).getSpikeWidth() >= catX &&
                    spikes.get(i).spikeX <= catX + cat.getWidth() &&
                    spikes.get(i).spikeY + spikes.get(i).getSpikeWidth() >= catY &&
                    spikes.get(i).spikeY + spikes.get(i).getSpikeWidth() <= catY + cat.getHeight()){


            }


        }
    }
}
