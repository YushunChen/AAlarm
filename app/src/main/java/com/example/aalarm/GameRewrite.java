package com.example.aalarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameRewrite extends AppCompatActivity {

    static String contentStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_rewrite);

        TextView content = (TextView) findViewById(R.id.hintField);
        //generate random string
        contentStr = getRandomString(15);
        content.setText(contentStr);


    }

    public void clickFunction(View view){
        //get rewrite content
        EditText rewriteContent = (EditText) findViewById(R.id.rewriteField);

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

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

}
