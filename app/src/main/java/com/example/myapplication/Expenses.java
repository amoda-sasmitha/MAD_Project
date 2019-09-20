package com.example.myapplication;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adapters.DailyExpensesAdapter;
import Database.DBhelper;
import Models.CategoryModel;
import Models.DailyTransaction;
import Models.Transaction;
import Util.Util;


public class Expenses extends Fragment {

    private TextView AccountBtn, accountbtn;
    private FloatingActionButton plusBtn;
    private TextView categoryText , amount, date ;
    private RecyclerView dailyrv;
    DBhelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        AccountBtn = view.findViewById(R.id.Account);
        accountbtn = view.findViewById(R.id.textView3);
        plusBtn = view.findViewById(R.id.add_expenses_btn);


        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , AddEditExpenses.class );
                startActivity(intent);
            }
        });

        setAccountClick();
        db = new DBhelper(getContext());
        ArrayList<Transaction> dbdata = db.readAllTransactions( "14-09-2019" ,"21-09-2019");

        ArrayList<DailyTransaction> db = Util.sortTransaction( "01-09-2019" , "30-09-2019" , dbdata );
        dailyrv = view.findViewById( R.id.dailyRV );
        dailyrv.setLayoutManager( new LinearLayoutManager( getActivity().getApplicationContext() ));


        dailyrv.setNestedScrollingEnabled(false);
        DailyExpensesAdapter adapter = new DailyExpensesAdapter( db ,getActivity().getApplicationContext()  );
        dailyrv.setAdapter(adapter);





        return view;
    }

    public void setAccountClick(){
        accountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Accounts()).commit();
            }
        });
        AccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Accounts()).commit();
            }
        });
    }


}
