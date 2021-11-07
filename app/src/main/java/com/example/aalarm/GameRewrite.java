package com.example.aalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameRewrite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_rewrite);

    }

    public void clickFunction(View view){
        //get rewrite content
        TextView content = (TextView) findViewById(R.id.textField);
        EditText rewriteContent = (EditText) findViewById(R.id.rewriteField);
        String contentStr = content.getText().toString();

        //compair content
        if(rewriteContent.getText().toString().length() <= 0 || !rewriteContent.getText().toString().equals(contentStr)){
            Toast.makeText(GameRewrite.this, "Please check the text you entered", Toast.LENGTH_LONG).show();
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
