package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import Models.AccountModel;
import Util.Util;

public class view_account_indetails extends AppCompatActivity {

    private ImageView editBtn;
    private ImageButton delete_btn;
    private TextView amount, type, name , accNumber , description ;
    private ImageView icon;
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

        name.setText( account.getAccountName() );
        description.setText( account.getAccountDescription() );
        accNumber.setText( account.getAccountNumber() );
        type.setText( account.getAccountType() );
        amount.setText(  "Rs. "+ String.format("%.2f", account.getAmount() ) );
        icon.setImageResource(Util.getAccountIcon( account.getAccountType() , this) );

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( view_account_indetails.this , EditAccount.class);
               intent.putExtra( "Account" , account);
                startActivity(intent);
                finish();
            }
        });

//        delete_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Dialog dialog = new Dialog( view_account_indetails.this );
//                dialog.setContentView(R.layout.delete_message);
//                Button accept = dialog.findViewById(R.id.accept_btn);
//                TextView textView = dialog.findViewById(R.id.deleteText);
//                ImageButton close = dialog.findViewById(R.id.close_btn);
//                textView.setText("Are you sure you want to delete this account ?");
//                dialog.show();
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//
//                accept.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent( view_account_indetails.this , MainActivity.class);
//                        startActivity(intent);
//                    }
//                });
//            }
//        });
    }
}
