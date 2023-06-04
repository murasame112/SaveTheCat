package com.example.savethecat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Spike {
    Bitmap spike[] = new Bitmap[3];
    int spikeFrame = 0;
    int spikeX, spikeY, spikeVelocity;
    Random random;

    private boolean isEasyMode;



    public Spike(Context context,  boolean isEasyMode){

        this.isEasyMode = isEasyMode;

            if(isEasyMode == true) {
                spike[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spike0);
                spike[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spike0);
                spike[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spike0);
                random = new Random();
                resetPosition();
            }else
            {
                spike[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spike1);
                spike[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spike1);
                spike[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spike1);
                random = new Random();
                resetPosition();
            }
    }


    public Bitmap getSpike(int spikeFrame){
        return spike[spikeFrame];
    }

    public int getSpikeWidth(){
        return spike[0].getWidth();
    }

    public int getSpikeHeight(){
        return spike[0].getHeight();
    }

    public void resetPosition() {
        spikeX = random.nextInt(GameView.dWidth - getSpikeWidth());
        spikeY =  -200 + random.nextInt(600) * -1;
//        spikeVelocity = 15 + random.nextInt(16);
        spikeVelocity =(isEasyMode ? 15 : 30) + random.nextInt(16) * 2;
    }
}
