package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Adapters.TransactionAdapter;
import Database.DBhelper;
import Models.AccountModel;
import Models.Transaction;
import Util.Util;

public class view_account_indetails extends AppCompatActivity {

    private ImageView editBtn;
    private ImageButton delete_btn;
    private TextView amount, type, name , accNumber , description,date ;
    private ImageView icon;
    private RecyclerView LatestT;
    int AccountID;
    DBhelper db;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_indetails);

        Intent intent = getIntent();
        final AccountModel account = (AccountModel) intent.getSerializableExtra("Account");

        icon = findViewById(R.id.category_icon);
        delete_btn = findViewById(R.id.delete_btn);
        editBtn = findViewById(R.id.edit_btn02);
        amount = findViewById(R.id.Amount_text);
        type = findViewById(R.id.description);
        accNumber = findViewById(R.id.account);
        description = findViewById(R.id.account2);
        name = findViewById(R.id.accountName);
        LatestT = findViewById(R.id.latestT);
        date = findViewById(R.id.category_date1);

        db = new DBhelper(this);
        date.setText(new SimpleDateFormat("dd MMMM yyyy").format(new Date()));

        name.setText( account.getAccountName() );
        description.setText( account.getAccountDescription() );
        accNumber.setText( account.getAccountNumber() );
        type.setText( account.getAccountType() );
        amount.setText(  "Rs. "+ String.format("%.2f", account.getAmount() ) );
        icon.setImageResource(Util.getAccountIcon( account.getAccountType() , this) );
        AccountID = account.getId();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( view_account_indetails.this , EditAccount.class);
               intent.putExtra( "Account" , account);
                startActivity(intent);
                finish();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog( view_account_indetails.this );
                dialog.setContentView(R.layout.delete_message);
                Button accept = dialog.findViewById(R.id.accept_btn);
                TextView textView = dialog.findViewById(R.id.deleteText);
                ImageButton close = dialog.findViewById(R.id.close_btn);
                textView.setText("Are you sure you want to delete this account ?");
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
 //detele btn call
                        db.deleteAllTransactionInAccount( AccountID ); //delete all transactions which are in want to delete  account
                        boolean lastResult = db.deleteAccount(AccountID);

                        View layout = getLayoutInflater().inflate( R.layout.toast_message , (ViewGroup) view.findViewById(R.id.toastRoot) );
                        TextView text = layout.findViewById(R.id.textMsg);
                        CardView background = layout.findViewById(R.id.back);
                        Toast toast = new Toast( view_account_indetails.this);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER , 0 , 230 );

                        if( lastResult == true ){
                            Intent intent = new Intent( view_account_indetails.this , MainActivity.class);
                            startActivity(intent);
                            text.setText("Account Delete Successfully");
                            toast.setView(layout);
                            finish();
                            toast.show();
                        }else{
                            text.setText("Something wrong happened, Account not deleted ! ");
                            toast.setView(layout);
                            toast.show();
                        }
                    }
                });
            }
        });

        ArrayList<Transaction> latest = db.AccountlatestTransactions(String.valueOf( AccountID ));
        TransactionAdapter Tadapter = new TransactionAdapter( latest , this , false , true);
        LatestT.setHasFixedSize(true);
        LatestT.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) );
        LatestT.setAdapter(Tadapter);
        LatestT.setNestedScrollingEnabled(false);

    }
}
