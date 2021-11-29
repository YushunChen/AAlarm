package com.example.aalarm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameMath extends AppCompatActivity {

    static int answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_math);

        TextView content = (TextView) findViewById(R.id.hintField);
        //String contentStr = content.getText().toString();

        final int min = 90;
        final int max = 900;
        final int random1 = new Random().nextInt((max - min) + 1) + min;
        final int random2 = new Random().nextInt((max - min) + 1) + min;
        final int random3 = new Random().nextInt((max - min) + 1) + min;

        String number1 = String.valueOf(random1);
        String number2 = String.valueOf(random2);
        String number3 = String.valueOf(random3);

        answer = random1 + random2 + random3;
        content.setText(number1 + "+" + number2 + "+" + number3);

    }
    public void clickFunction(View view){
        //get rewrite content
        EditText rewriteContent = (EditText) findViewById(R.id.answerField);

        //compair content
        if(rewriteContent.getText().toString().length() <= 0 ||
                !rewriteContent.getText().toString().matches("^[0-9]+$") ||
                Integer.parseInt(rewriteContent.getText().toString()) != answer){
            Toast.makeText(GameMath.this, "Please check the text you entered", Toast.LENGTH_LONG).show();
        }else{
            // start activity
            goToActivityMain();
        }
    }

    public void goToActivityMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
