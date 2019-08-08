package com.example.myapplication;


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
    private ImageButton edit_btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_expense ,container , false);

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
                dataBundle.putString( "Account" , account.getText().toString() );
                dataBundle.putString("Description" , description.getText().toString() );

                editExpense.setArguments(dataBundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container , editExpense  ).commit();
            }
        });

        return view;
    }



    }
