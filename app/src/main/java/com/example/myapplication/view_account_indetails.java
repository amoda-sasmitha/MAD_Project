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

public class view_account_indetails extends AppCompatActivity {

    private ImageView editBtn;
    private ImageButton delete_btn;
    private TextView amount, type, name , AccNumber ;
    private ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_indetails);

        delete_btn = findViewById(R.id.delete_btn);
        editBtn = findViewById(R.id.edit_btn02);
        //amount

        Intent intent = getIntent();
        AccountModel account = (AccountModel) intent.getSerializableExtra("Account");


//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent( view_account_indetails.this , EditAccount.class);
//                startActivity(intent);
//            }
//        });
//
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
