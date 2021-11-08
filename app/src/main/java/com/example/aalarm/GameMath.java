package com.example.aalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameMath extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_math);

    }
    public void clickFunction(View view){
        //get rewrite content
        //demo problem: 1+2+3+4 = 10
        TextView content = (TextView) findViewById(R.id.textField);
        EditText rewriteContent = (EditText) findViewById(R.id.answerField);
        String contentStr = content.getText().toString();

        //compair content
        if(rewriteContent.getText().toString().length() <= 0 ||
                !rewriteContent.getText().toString().matches("^[0-9]+$") ||
                Integer.parseInt(rewriteContent.getText().toString()) != 10){
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
