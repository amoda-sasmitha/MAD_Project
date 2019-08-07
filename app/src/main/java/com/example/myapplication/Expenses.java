package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Expenses extends Fragment {

    private FloatingActionButton plusBtn;
    private TextView categoryText , amount, date ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses ,container , false);

        plusBtn = view.findViewById(R.id.add_expenses_btn);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , AddEditExpenses.class );
                startActivity(intent);
            }
        });

        categoryText = view.findViewById(R.id.category_text);
        amount = view.findViewById( R.id.category_cost);
        date = view.findViewById(R.id.date);

        categoryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity() , ViewExpenseDetails.class );

                Bundle bundle = new Bundle();
                bundle.putString("CategoryType", categoryText.getText().toString()  );
                bundle.putString( "ExpenseDate" , date.getText().toString() );
                bundle.putString( "Amount" , amount.getText().toString() );

                intent.putExtras( bundle);
                startActivity(intent);
            }
        });


        return view;
    }
}
