package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddEditExpenses extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText select_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_expenses);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable( "Saving" , intent.getSerializableExtra("Saving"));
        AddExpense expense = new AddExpense();
        expense.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container , expense ).commit();


    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
