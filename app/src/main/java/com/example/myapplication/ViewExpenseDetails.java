package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ViewExpenseDetails extends AppCompatActivity {

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
        setContentView(R.layout.activity_view_expense_details);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.nav_expenses);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();
        ViewExpense viewExpense = new ViewExpense();

        if( intent.getExtras() != null ){
            Bundle dataset = intent.getExtras();
            viewExpense.setArguments(dataset);
        }
        getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container , viewExpense ).commit();

    }
}
