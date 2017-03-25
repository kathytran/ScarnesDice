package com.example.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static int user_overall_score = 0;
    public static int user_turn_score = 0;
    public static int comp_overall_score = 0;
    public static int comp_turn_score = 0;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            TextView text = (TextView)findViewById(R.id.compScore);
            if(computerTurn() == true) {
                if (comp_turn_score < 20) {
                    timerHandler.postDelayed(this, 500);
                }
                else{
                    comp_overall_score += comp_turn_score;
                    enableButtons(true);
                    comp_turn_score = 0;
                }
            }
            else{
                comp_turn_score = 0;
                enableButtons(true);
            }
            text.setText("Computer Score: " + comp_overall_score);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void rollDie(View view) {
        int diceValue = randomValue();
        setDiceFace(diceValue);
        if (diceValue != 1) {
            user_turn_score += diceValue;

        } else {
            enableButtons(false);
            user_turn_score = 0;
            timerHandler.postDelayed(timerRunnable,1000);
        }
    }

    public int randomValue() {    // randomly generates dice number from 1-6
        Random rand = new Random();
        int value;
        value = rand.nextInt(6) + 1; //1 minimum 6 maximum
        return value;
    }


    public void enableButtons(boolean value) {
        Button roll = (Button)findViewById(R.id.rollButton);
        Button hold = (Button)findViewById(R.id.holdButton);
        Button reset = (Button)findViewById(R.id.resetButton);
        roll.setEnabled(value);
        hold.setEnabled(value);
        reset.setEnabled(value);
    }

    public void setDiceFace(int diceValue) {
        ImageView image = (ImageView)findViewById(R.id.imageView);
        switch (diceValue) {
            case 1:
                image.setImageResource(R.drawable.dice1);
                break;
            case 2:
                image.setImageResource(R.drawable.dice2);
                break;
            case 3:
                image.setImageResource(R.drawable.dice3);
                break;
            case 4:
                image.setImageResource(R.drawable.dice4);
                break;
            case 5:
                image.setImageResource(R.drawable.dice5);
                break;
            case 6:
                image.setImageResource(R.drawable.dice6);
                break;
        }

    }

    public boolean computerTurn(){
        int diceValue = randomValue();
        setDiceFace(diceValue);
        if(diceValue != 1) {
            comp_turn_score += diceValue;
            return true;
        }
        else {
            comp_turn_score = 0;
            return false;
        }
    }

    public void resetButton(View view) {
        TextView userText = (TextView)findViewById(R.id.yourScore);
        TextView compText = (TextView)findViewById(R.id.compScore);
        user_overall_score = 0;
        comp_overall_score = 0;
        user_turn_score = 0;
        comp_overall_score = 0;
        userText.setText("Your score: " + user_overall_score);
        compText.setText("Computer score: " + comp_overall_score);
    }

    public void holdButton(View view) {
        enableButtons(false);
        TextView turnText = (TextView)findViewById(R.id.turnScore);
        user_overall_score += user_turn_score;
        user_turn_score = 0;
        turnText.setText("Your score: " + user_overall_score);
        timerHandler.postDelayed(timerRunnable,0);
    }

}
