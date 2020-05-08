package com.myproject.dicee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SoundPool mSoundPool;
    private int mLeftSoundId;
    private int mRightSoundId;
    private int buzzer;
    private final float LEFT_VOLUME = 1.0f;
    private final float RIGHT_VOLUME = 1.0f;
    private final int NO_LOOP = 0;
    private final int PRIORITY = 0;
    private final float NORMAL_PLAY_RATE = 1.0f;
    int number1=0,number2=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes aa = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build();

            // TODO: Create a new SoundPool
            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(aa)
                    .build();
            mLeftSoundId = mSoundPool.load(this, R.raw.click, 1);
            mRightSoundId = mSoundPool.load(this, R.raw.click, 1);
            buzzer = mSoundPool.load(this, R.raw.buzzer, 1);
        }
        else {
            mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            mLeftSoundId = mSoundPool.load(this, R.raw.click, 1);
            mRightSoundId = mSoundPool.load(this, R.raw.click, 1);
            buzzer = mSoundPool.load(this, R.raw.buzzer, 1);
        }
        Button rollButton;
        Button button4;
        rollButton = (Button) findViewById(R.id.rollButton);
        button4 = (Button) findViewById(R.id.button4);


        final ImageView leftDice = (ImageView) findViewById(R.id.image_leftDice);

        final ImageView rightDice = (ImageView) findViewById(R.id.image_rightDice);

        final int[]  diceArray = {
                R.drawable.dice1,
                R.drawable.dice2,
                R.drawable.dice3,
                R.drawable.dice4,
                R.drawable.dice5,
                R.drawable.dice6
        };

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("Dicee", "BUTTON PRESSED");

                Random randomNumberGenerator = new Random();

                number1 = randomNumberGenerator.nextInt(6);

                Log.d("Dicee","the random no. is"+number1);

                leftDice.setImageResource(diceArray[number1]);

                mLeftSoundId=mSoundPool.play(mLeftSoundId,LEFT_VOLUME,RIGHT_VOLUME,PRIORITY,NO_LOOP,NORMAL_PLAY_RATE);

                confirmation();

            }
        });
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Dicee", "BUTTON PRESSED");

                Random randomNumberGenerator = new Random();

                number2 = randomNumberGenerator.nextInt(6);

                Log.d("Dicee","the random no. is"+number2);

                rightDice.setImageResource(diceArray[number2]);

                mRightSoundId=mSoundPool.play(mRightSoundId,LEFT_VOLUME,RIGHT_VOLUME,PRIORITY,NO_LOOP,NORMAL_PLAY_RATE);

                confirmation();

            }
        });
        }
    private void confirmation() {

        if (number1 == number2) {
            buzzer=mSoundPool.play(buzzer,LEFT_VOLUME,RIGHT_VOLUME,PRIORITY,NO_LOOP,NORMAL_PLAY_RATE);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("GAME OVER");
            alert.setCancelable(false);
            alert.setMessage("YOU LOST");
            alert.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    finish();
                }
            });
            alert.setNegativeButton("CONTINUE",  null);
            alert.show();
        }
    }
    public void playleft(View view) {mSoundPool.play(mLeftSoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
    }

    public void playright(View view) {mSoundPool.play(mRightSoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
    }
}


