package com.example.myapplication;
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
import Adapters.AccountAdapter;
import Database.DBhelper;
import Models.AccountModel;
import Util.Util;

public class Accounts extends Fragment  {

    private RelativeLayout viewBtn;
    private FloatingActionButton addbtn;
    private RecyclerView ARV;
    private TextView totAmount;
    DBhelper db;

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts ,container , false);

        addbtn = view.findViewById(R.id.add_category_btn2);
        ARV = view.findViewById(R.id.recycleviewAccounts);
        ARV.setLayoutManager( new LinearLayoutManager( getContext() , LinearLayoutManager.VERTICAL , false));
        db = new DBhelper( getContext() );
        totAmount = view.findViewById(R.id.AccountAmount);
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
        return view;
    }
    @Override
    public void onStart() { //get all acounts details in open page from using array list
        super.onStart();  //in onstart get all account details from db
        ArrayList<AccountModel> data = db.readAllAccountsWithBalance();
        AccountAdapter adapter = new AccountAdapter( getActivity().getApplicationContext() , data );
        ARV.setAdapter( adapter);
        totAmount.setText( "Rs. "+ String.format("%.2f", Util.getTotalBalance(getContext()) )  );

    }
}
