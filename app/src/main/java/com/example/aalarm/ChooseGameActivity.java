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
        Intent intent = new Intent(this, AddAlarmActivity.class);
        String gameChosen = "shake phone";
        intent.putExtra("game", gameChosen);
        startActivity(intent);
    }

    public void onChooseCode(View v) {
        Intent intent = new Intent(this, AddAlarmActivity.class);
        String gameChosen = "scan code";
        intent.putExtra("game", gameChosen);
        startActivity(intent);
    }

    public void onChooseCalculator(View v) {
        Intent intent = new Intent(this, AddAlarmActivity.class);
        String gameChosen = "calculation";
        intent.putExtra("game", gameChosen);
        startActivity(intent);
    }

    public void onChooseQuestion(View v) {
        Intent intent = new Intent(this, AddAlarmActivity.class);
        String gameChosen = "question";
        intent.putExtra("game", gameChosen);
        startActivity(intent);
    }

    public void onChooseRewriteTask(View v) {
        Intent intent = new Intent(this, AddAlarmActivity.class);
        String gameChosen = "rewrite";
        intent.putExtra("game", gameChosen);
        startActivity(intent);
    }

    public void onChooseUnknown(View v) {
        Intent intent = new Intent(this, AddAlarmActivity.class);
        String gameChosen = "no game";
        intent.putExtra("game", gameChosen);
        startActivity(intent);
    }
}