package com.example.myapplication;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ViewExpense extends Fragment {

    private TextView data ,CategoryName , date , amount , description , account;
    private ImageButton edit_btn ,delete_btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_expense ,container , false);
        delete_btn = view.findViewById(R.id.delete_btn);
        edit_btn = view.findViewById(R.id.edit_btn);
        CategoryName = view.findViewById(R.id.category_name);
        date = view.findViewById(R.id.date_text);
        amount = view.findViewById(R.id.Amount_text );
        account  = view.findViewById(R.id.account);
        description = view.findViewById(R.id.description_text);

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditExpense editExpense = new EditExpense();
                Bundle dataBundle = new Bundle();

                dataBundle.putString("CategoryName" , CategoryName.getText().toString() );
                dataBundle.putString( "Amount" , amount.getText().toString() );
                dataBundle.putString("Date" , date.getText().toString() );
                dataBundle.putString( "Accounts" , account.getText().toString() );
                dataBundle.putString("Description" , description.getText().toString() );

                editExpense.setArguments(dataBundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container , editExpense  ).commit();
            }
        });


        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity() );
                dialog.setContentView(R.layout.delete_message);
                Button accept = dialog.findViewById(R.id.accept_btn);
                TextView textView = dialog.findViewById(R.id.deleteText);
                ImageButton close = dialog.findViewById(R.id.close_btn);

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
                        Intent intent = new Intent(getActivity() , MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        return view;
    }



    }
