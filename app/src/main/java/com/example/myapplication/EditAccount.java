package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import Database.DBhelper;
import Models.AccountModel;
public class EditAccount extends AppCompatActivity {
    EditText accNumber, name , amount, description;
    Button submitBtn;
    Spinner spinner;
    DBhelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account  );
        Intent intent = getIntent();
        final AccountModel account = (AccountModel) intent.getSerializableExtra("Account");
        db = new DBhelper(this);
        submitBtn = findViewById(R.id.save_btn);
        spinner = (Spinner) findViewById(R.id.editText3);
        accNumber = findViewById(R.id.account_no_txt);
        name = findViewById(R.id.txt_account_name);
        amount = findViewById(R.id.txt_add_acc_amount);
        description = findViewById( R.id.txt_add_acc_edit_des );
        accNumber.setText( account.getAccountNumber() );
        name.setText( account.getAccountName() );
        amount.setText( account.getBalance()+"0");

        description.setText( account.getAccountDescription() );
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.spinnerAccounts, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
                spinner.setAdapter(adapter);
                for (int i = 0; i < spinner.getCount(); i++) {
                    if (spinner.getItemAtPosition(i).equals( account.getAccountType() )) {
                        spinner.setSelection(i);
                        break;
                    }
                 }
        submitBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //validate user input
            if (name.length() == 0) {
                name.setError("Enter Account Name");
            } else if (spinner.getSelectedItem().toString().equals("Select Account Type")) {
                ((TextView) spinner.getSelectedView()).setError("Select Account Type");
            } else if (amount.length() == 0) {
                amount.setError("Enter Initial Amount");
            } else if (accNumber.length() == 0) {
                accNumber.setError("Enter Account NUmber");
            } else if (description.length() == 0) {
                description.setError("Enter Description");
            } else {
                AccountModel newAccount = new AccountModel();
                newAccount.setId(account.getId());
                newAccount.setAccountName(name.getText().toString().trim());
                newAccount.setAccountType(spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString().trim());
                newAccount.setAccountDescription(description.getText().toString().trim());
                boolean result = db.updateAccount(newAccount);
                //inflate layout
                View layout = getLayoutInflater().inflate(R.layout.toast_message, (ViewGroup) view.findViewById(R.id.toastRoot));
                TextView text = layout.findViewById(R.id.textMsg);
                CardView background = layout.findViewById(R.id.back);
                //creat toast
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 230);
                //set text for
                if (result == false) {
                    text.setText("Account Update Unsuccessfully");
                    background.setCardBackgroundColor(getResources().getColor(R.color.red));
                    text.setTextColor(getResources().getColor(R.color.white));
                    toast.setView(layout);
                    toast.show();

                } else {
                    background.setCardBackgroundColor(getResources().getColor(R.color.green));
                    text.setTextColor(getResources().getColor(R.color.white));
                    text.setText("Account Update Successfully");
                    toast.setView(layout);
                    toast.show();
                    Intent intent = new Intent(EditAccount.this, MainActivity.class);
                    intent.putExtra("Account", newAccount);
                    startActivity(intent);
                    finish();

                }
            }
        }
     });

    }

}
