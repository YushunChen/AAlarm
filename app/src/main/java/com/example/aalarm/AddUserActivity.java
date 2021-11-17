package com.example.aalarm;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_activity);
    }

    public void clickSave(View view){
        //get edittextview
        EditText editTextName = (EditText) findViewById(R.id.nameField);
        String contentName = editTextName.getText().toString();

        EditText editTextImportance = (EditText) findViewById(R.id.importanceField);
        String contentImportance = editTextImportance.getText().toString();

        // 2.get sql instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("alarms", Context.MODE_PRIVATE,null);
        // 3. init dbhelper
        DBHelper db = new DBHelper(sqLiteDatabase);

        if(contentName.length() <= 0 && contentImportance.length() <= 0){
            Toast.makeText(AddUserActivity.this, "Please Enter valid information", Toast.LENGTH_LONG).show();
            return;
        }

        boolean successful = db.saveUserActivity(contentName, contentImportance, false, false,
                false, false, false,false,false);

        if(!successful){
            Toast.makeText(AddUserActivity.this, "activity already exists", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


    }
}
