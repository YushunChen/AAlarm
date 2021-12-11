package com.example.aalarm;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddUserActivity extends AppCompatActivity {
    boolean monday = false;
    boolean tuesday = false;
    boolean wednesday = false;
    boolean thursday = false;
    boolean friday = false;
    boolean saturday = false;
    boolean sunday = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_activity);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner_frequency);
        //create a list of items for the spinner.
//        String[] items = new String[]{"SELECT IMPORTANCE", "LOW", "MEDIUM", "HIGH"};
        String[] items = new String[]{"SELECT FREQUENCY", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        int spinnerPosition = adapter.getPosition(items[0]);
        dropdown.setSelection(spinnerPosition);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    public void clickSave(View view){
        //get edittextview
        EditText editTextName = (EditText) findViewById(R.id.nameField);
        String contentName = editTextName.getText().toString();

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner_frequency);
        String spFrequency = mySpinner.getSelectedItem().toString();

        // 2.get sql instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("alarms", Context.MODE_PRIVATE,null);
        // 3. init dbhelper
        DBHelper db = new DBHelper(sqLiteDatabase);

        if(contentName.length() <= 0){
            Toast.makeText(AddUserActivity.this, "Please Enter valid information", Toast.LENGTH_LONG).show();
            return;
        }

        if(!(monday || tuesday || wednesday || thursday || friday || saturday || sunday)){
            Toast.makeText(AddUserActivity.this, "Please select at least one day", Toast.LENGTH_LONG).show();
            return;
        }

        if(mySpinner.getSelectedItem().toString().equals("SELECT FREQUENCY")){
            Toast.makeText(AddUserActivity.this, "Please select frequency", Toast.LENGTH_LONG).show();
            return;
        }
        int frequency = Integer.parseInt(mySpinner.getSelectedItem().toString());

        boolean successful = db.saveUserActivity(contentName, frequency, monday, tuesday,
                wednesday, thursday, friday, saturday, sunday);

        if(!successful){
            Toast.makeText(AddUserActivity.this, "activity already exists", Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("frgToLoad", R.id.plan);
            startActivity(intent);
        }

    }

    public void clickMonday(View view) {
        monday = !monday;
//        Log.v("", "" + monday);
    }

    public void clickTuesday(View view) {
        tuesday = !tuesday;
    }

    public void clickWednesday(View view) {
        wednesday = !wednesday;
    }

    public void clickThursday(View view) {
        thursday = !thursday;
    }

    public void clickFriday(View view) {
        friday = !friday;
    }

    public void clickSaturday(View view) {
        saturday = !saturday;
    }

    public void clickSunday(View view) {
        sunday = !sunday;
    }

    public void clickCancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("frgToLoad", R.id.plan);
        startActivity(intent);
    }
}
