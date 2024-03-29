package com.bawp.deltatask2trial1;

import static com.bawp.deltatask2trial1.MainThread.canvas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private CharacterSprite characterSprite;
    public static int gapHeight = 500;
    public static int velocity = 10;
    public Obstaclesprite pipe1, pipe2, pipe3;
    public int highscore = 0;
    private SurfaceHolder holder;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private static final long START_TIME_IN_MILLIS = 600000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    public String text = "";
    public String text1 = "";
    public String text2 = "";
    private int score;
    public String text4 = "" ;




    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    private int highScore;
    private int state=1;


    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);


        setFocusable(true);

    }

    public void resetLevel() {
        Log.i("info"," im here");
        Log.i("info"," im here2");
        characterSprite.y = 100;
        pipe1.xX = 2000;
        pipe1.yY = 0;
        pipe2.xX = 4500;
        pipe2.yY = 200;
        pipe3.xX = 3200;
        pipe3.yY = 250;
        thread.start();
        Log.i("in", "I excetu");
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap =
                Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (state == 0 && (event.getX() > 700 && event.getX() < 1500) && (event.getY() > 700 && event.getY() < 850) ){
            Log.i("ola",String.valueOf(event.getX()));
            Log.i("olla",String.valueOf(event.getY()));
            Log.i("state", String.valueOf(state));
            resetLevel();
            Log.i("infome",String.valueOf(thread.isAlive()));

            thread.start();
            Log.i("infome",String.valueOf(thread.isAlive()));

        }
        characterSprite.y = characterSprite.y - (characterSprite.yVelocity * 10);
        return super.onTouchEvent(event);


    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        makeLevel();
        characterSprite = new CharacterSprite(getResizedBitmap
                (BitmapFactory.decodeResource(getResources(),R.drawable.ball), 300, 240));


        thread.setRunning(true);
        thread.start();

    }
    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
    }

    private void makeLevel() {
        startTimer();
        characterSprite = new CharacterSprite
                (getResizedBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ball), 300, 240));
        Bitmap bmp;
        Bitmap bmp2;
        int y;
        int x;
        bmp = getResizedBitmap(BitmapFactory.decodeResource
                (getResources(), R.drawable.spike), 500, Resources.getSystem().getDisplayMetrics().heightPixels / 2);
        bmp2 = getResizedBitmap
                (BitmapFactory.decodeResource(getResources(), R.drawable.spikedown), 500, Resources.getSystem().getDisplayMetrics().heightPixels / 2);

        pipe1 = new Obstaclesprite(bmp, bmp2, 2000, 100);
        pipe2 = new Obstaclesprite(bmp, bmp2, 4500, 100);
        pipe3 = new Obstaclesprite(bmp, bmp2, 3200, 100);

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        logic(null);
        characterSprite.update();
        pipe1.update();
        pipe2.update();
        pipe3.update();

    }

    @Override
    public void draw(Canvas canvas)
    {

        super.draw(canvas);
        if (state == 0) {
            canvas.drawColor(Color.WHITE);
            characterSprite.draw(canvas);
            pipe1.draw(canvas);
            pipe2.draw(canvas);
            pipe3.draw(canvas);



            Paint paint = new Paint();
            canvas.drawText(text, 100, 100, paint);
            paint.setTextSize(200);
            canvas.drawText(text1, 700, 750, paint);
            paint.setTextSize(100);
            canvas.drawText(text2, 1000, 920, paint);
            paint.setColor(Color.BLUE);

            paint.setColor(Color.BLACK);
            paint.setTextSize(100);
            canvas.drawText(text4, 2250, 100, paint);
        }
        else if(canvas!=null) {
            canvas.drawColor(Color.WHITE);
            characterSprite.draw(canvas);
            pipe1.draw(canvas);
            pipe2.draw(canvas);
            pipe3.draw(canvas);



            Paint paint = new Paint();
            canvas.drawText(text, 100, 100, paint);
            paint.setTextSize(200);
            canvas.drawText(text1, 700, 750, paint);
            paint.setTextSize(100);
            canvas.drawText(text2, 1000, 970, paint);
            paint.setTextSize(100);
            canvas.drawRect(700, 850, 1500, 700, paint);
            canvas.drawText(text4, 2250, 100, paint);
            text4 = "Score = " + String.valueOf(score);

        }

    }
    public void gameover(){
        Log.i("game over execution","s");
        state = 0;
        if (score > highScore){
            highScore = score;
        }
//        thread.setRunning(true);
        text = " High Score = " + String.valueOf(highScore);
        text1 = "GAME OVER";
        text2 = "Play Again";
        text4 = "Score = " + String.valueOf(score);
        draw(canvas);
//        Object event = null;
//        onTouchEvent1((MotionEvent) event);
    }
    public void logic(View view) {

        List<Obstaclesprite> pipes = new ArrayList<>();
        pipes.add(pipe1);
        pipes.add(pipe2);
        pipes.add(pipe3);

        for (int i = 0; i < pipes.size(); i++) {
            //Detect if the ball is touching one of the spikes
            if (characterSprite.y < pipes.get(i).yY + (screenHeight / 2) - (gapHeight / 2) && characterSprite.x + 300 > pipes.get(i).xX && characterSprite.x < pipes.get(i).xX + 500) {
               gameover();
               thread.interrupt();
               // resetLevel(null);
                

            }
            else if (characterSprite.y + 240 > (screenHeight / 2) + (gapHeight / 2) + pipes.get(i).yY && characterSprite.x + 300 > pipes.get(i).xX && characterSprite.x < pipes.get(i).xX + 500) {
               gameover();
               thread.interrupt();
                //resetLevel(null);
            }
            else{
                  score+=1;
                }


            //Detect if the spike has gone off the left of the screen and regenerate further ahead
            if (pipes.get(i).xX + 500 < 0) {
                Random r = new Random();
                int value1 = r.nextInt(500);
                int value2 = r.nextInt(500);
                pipes.get(i).xX = screenWidth + value1 + 1000;
                pipes.get(i).yY = value2 - 250;
            }
        }

        //Detect if the ball has gone off the bottom or top of the screen
        if (characterSprite.y + 240 < 0) {
            gameover();
            thread.interrupt(); }
        if (characterSprite.y > screenHeight) {
            gameover();
            thread.interrupt();}
    }
    public boolean onTouchEvent1(MotionEvent event) {
        Log.i("ola",String.valueOf(event.getX()));
        Log.i("olla",String.valueOf(event.getY()));
        if ((event.getX() > 700 && event.getX() < 1500) && (event.getY() > 700 && event.getY() < 850)) {
            resetLevel();
            thread.start();

        }
        return super.onTouchEvent(event);
    }
    }






