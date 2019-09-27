package com.example.myapplication;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    private EditText search;
    private AccountAdapter adapter;
    DBhelper db;

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts ,container , false);

        search = view.findViewById(R.id.search);
        addbtn = view.findViewById(R.id.add_category_btn2);
        ARV = view.findViewById(R.id.recycleviewAccounts);
        ARV.setLayoutManager( new LinearLayoutManager( getContext() , LinearLayoutManager.VERTICAL , false));
        db = new DBhelper( getContext() );

        ArrayList<AccountModel> data = db.readAllAccountsWithBalance();  //get all acounts details in open page from using array list
        AccountAdapter adapter = new AccountAdapter( getActivity().getApplicationContext() , data );
        ARV.setAdapter( adapter);

//    call add btn
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity() , AddAccount.class);
                startActivity(intent);

            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new Expenses()).commit();
                    return true;
                }
                return false;
            }
        } );
        return view;
    }

    @Override
    public void onStart() { //get all acounts details in open page from using array list
        super.onStart();  //in onstart get all account details from db
        ArrayList<AccountModel> data = db.readAllAccountsWithBalance();
        adapter = new AccountAdapter( getActivity().getApplicationContext() , data );
        ARV.setAdapter( adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(editable.toString() );
            }
        });



    }


}
