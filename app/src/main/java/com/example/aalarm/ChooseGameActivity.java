package com.example.aalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooseGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);
    }

    public void onChooseShakePhone(View v) {

    }

    public void onChooseCode(View v) {

    }

    public void onChooseCalculator(View v) {
        Intent intent = new Intent(this, GameMath.class);
        startActivity(intent);
    }

    public void onChooseQuestion(View v) {
        Intent intent = new Intent(this, GameChoice.class);
        startActivity(intent);
    }

    public void onChooseUnknown(View v) {

    }

    public void onChooseRewriteTask(View v) {
        Intent intent = new Intent(this, GameRewrite.class);
        startActivity(intent);
    }
}