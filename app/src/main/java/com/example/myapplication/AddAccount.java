package com.example.myapplication;

import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerAccounts, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        accountName = findViewById(R.id.textAddAcountName);
        accountType = findViewById(R.id.spinnerAccounts);
        accountIniAmount =  findViewById(R.id.add_account_initial_account_txt);
        accountNumber =  findViewById(R.id.account_no_txt2);
        accountDescription =  findViewById(R.id.account_des);
        accountIniAmount.setInputType(InputType.TYPE_CLASS_NUMBER);

        accountName.hasFocus();

    db = new DBhelper(this);


    }
    public void addAccountDetails(View view){
        //validate user input
       if(accountName.length() == 0){
           accountName.setError("Enter Account Name");
       }
      else  if(accountType.getSelectedItem().toString().equals("Select Account Type")){
           ((TextView)accountType.getSelectedView()).setError("Select Account Type");
       }
      else   if(accountIniAmount.length() == 0){
           accountIniAmount.setError("Enter Initial Amount");
       }
      else   if(accountNumber.length() == 0){
           accountNumber.setError("Enter Account NUmber");
        }
        else if(accountDescription.length() == 0){
           accountDescription.setError("Enter Description");
        }

       else {

           AccountModel am = new AccountModel();  //get new account modal
           am.setAccountName(accountName.getText().toString().trim());
           am.setAccountType(accountType.getItemAtPosition(accountType.getSelectedItemPosition()).toString().trim());
           am.setAmount(Double.valueOf(accountIniAmount.getText().toString().trim()));
           am.setAccountNumber(accountNumber.getText().toString().trim());
           am.setAccountDescription(accountDescription.getText().toString().trim());
           //set booleadn result
           boolean result = db.addAccount(am);

           //inflate layout
           View layout = getLayoutInflater().inflate(R.layout.toast_message, (ViewGroup) view.findViewById(R.id.toastRoot));
           TextView text = layout.findViewById(R.id.textMsg);
           CardView background = layout.findViewById(R.id.back);

           //creat toast
           Toast toast = new Toast(this);
           toast.setDuration(Toast.LENGTH_LONG);
           toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 230);


           if (!result) {
               text.setText("Account Added Unsuccessfully");
               background.setCardBackgroundColor(getResources().getColor(R.color.red));
               text.setTextColor(getResources().getColor(R.color.white));
               toast.setView(layout);
               toast.show();

           } else {
               background.setCardBackgroundColor(getResources().getColor(R.color.green));
               text.setTextColor(getResources().getColor(R.color.white));
               text.setText("Account Added Successfully");
               toast.setView(layout);
               this.finish();
               toast.show();

           }
       }

    }
}
