package com.example.aalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameChoice extends AppCompatActivity {
    private String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_choice);

        answer = "Paris";

    }

    public void clickFunction(View view){
        Button b = (Button)view;
        String text = b.getText().toString();

        if(text.equals(answer)){
            // start activity
            goToActivityMain();
        }else{
            Toast.makeText(GameChoice.this, "Answer Incorrect", Toast.LENGTH_LONG).show();

        }
    }

    public void goToActivityMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
