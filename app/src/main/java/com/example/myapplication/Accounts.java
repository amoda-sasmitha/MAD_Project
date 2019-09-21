package com.example.myapplication;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

//import Adapters.AccountsAdapter;
import Adapters.AccountAdapter;
import Database.DBhelper;
import Models.AccountModel;
import Models.Transaction;


public class Accounts extends Fragment  {

    private RelativeLayout viewBtn;
    private FloatingActionButton addbtn;
    private RecyclerView ARV;

    DBhelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts ,container , false);

        addbtn = view.findViewById(R.id.add_category_btn2);
        ARV = view.findViewById(R.id.recycleviewAccounts);
        ARV.setLayoutManager( new LinearLayoutManager( getContext() , LinearLayoutManager.VERTICAL , false));
        db = new DBhelper( getContext() );

        ArrayList<AccountModel> data = db.readAllAccountsWithBalance();
        AccountAdapter adapter = new AccountAdapter( getActivity().getApplicationContext() , data );
        ARV.setAdapter( adapter);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity() , AddAccount.class);
                startActivity(intent);

            }
        });
////
//        viewBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity() , view_account_indetails.class);
//                startActivity(intent);
//            }
//        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<AccountModel> data = db.readAllAccountsWithBalance();
        AccountAdapter adapter = new AccountAdapter( getActivity().getApplicationContext() , data );
        ARV.setAdapter( adapter);
    }
}
