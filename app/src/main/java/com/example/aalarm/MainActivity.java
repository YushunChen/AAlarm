package com.example.aalarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnItemSelectedListener(bottomnavFunction);

        //intent from other activity, to load previous fragment
        if(getIntent().getExtras() != null) {
            int intentFragment = getIntent().getExtras().getInt("frgToLoad");
            BottomNavigationView bottomNavigationView;
            Fragment fragment = null;
            switch (intentFragment) {
                case R.id.alarm:
                    fragment = new AlarmFragment();
                    bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
                    bottomNavigationView.setSelectedItemId(R.id.alarm);
                    break;
                case R.id.plan:
                    fragment = new PlanFragment();
                    bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
                    bottomNavigationView.setSelectedItemId(R.id.plan);
                    break;
                case R.id.personal:
                    fragment = new PersonFragment();
                    bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
                    bottomNavigationView.setSelectedItemId(R.id.personal);
                    break;
                default:
                    fragment = new PersonFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new AlarmFragment()).commit();
        }



    }

    public void onAddAlarm(View view) {
        goToAddAlarmActivity();
    }

    public void goToAddAlarmActivity() {
        Intent intent = new Intent(this, AddAlarmActivity.class);
        startActivity(intent);
    }

    private NavigationBarView.OnItemSelectedListener bottomnavFunction = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.alarm:
                    fragment = new AlarmFragment();
                    break;
                case R.id.plan:
                    fragment = new PlanFragment();
                    break;
                case R.id.personal:
                    fragment = new PersonFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };

    public void clickAddRecord(View view){
        Intent intent = new Intent(this, AddRecord.class);
        startActivity(intent);
    }
}