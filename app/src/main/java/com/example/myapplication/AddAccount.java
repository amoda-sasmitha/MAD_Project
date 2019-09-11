package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import Database.DBhelper;
import Models.AccountModel;

public class AddAccount extends AppCompatActivity {

    DBhelper db;
    EditText accountName,  accountIniAmount, accountNumber, accountDescription;
    Spinner accountType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerAccounts);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerAccounts, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        accountName = (EditText) findViewById(R.id.textAddAcountName);
        accountType = (Spinner) findViewById(R.id.spinnerAccounts);
        accountIniAmount = (EditText) findViewById(R.id.add_account_initial_account_txt);
        accountNumber = (EditText) findViewById(R.id.account_no_txt2);
        accountDescription = (EditText) findViewById(R.id.account_des);

    db = new DBhelper(this);


    }
    public void addAccountDetails(View view){
        AccountModel am = new AccountModel();

        am.setAccountName(accountName.getText().toString().trim());
//        am.setAccountType(accountType.getV);



    }
}
