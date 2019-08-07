package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ViewExpenseDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense_details);

        Intent intent = getIntent();
        ViewExpense viewExpense = new ViewExpense();

        if( intent.getExtras() != null ){
            Bundle dataset = intent.getExtras();
            viewExpense.setArguments(dataset);
        }
        getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container , viewExpense ).commit();

    }
}
