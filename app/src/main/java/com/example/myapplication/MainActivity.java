package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navi_categories:
                    Category category = new Category();
                    Bundle dataBundle = new Bundle();
                    dataBundle.putBoolean( "FromExpenses" , false);
                    category.setArguments( dataBundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, category).commit();
                    return true;
                case R.id.nav_expenses:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new Expenses()).commit();
                    return true;
                case R.id.nav_savings:
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container , new Savings() ).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.nav_expenses);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new Expenses()).commit();
    }

}
