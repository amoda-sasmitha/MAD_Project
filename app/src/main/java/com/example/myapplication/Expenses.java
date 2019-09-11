package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adapters.DailyExpensesAdapter;
import Models.CategoryModel;
import Models.DailyTransaction;
import Models.Transaction;
import Util.Util;


public class Expenses extends Fragment {

    private TextView AccountBtn;
    private FloatingActionButton plusBtn;
    private TextView categoryText , amount, date ;
    private RecyclerView dailyrv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses ,container , false);
        AccountBtn = view.findViewById(R.id.Account);
        plusBtn = view.findViewById(R.id.add_expenses_btn);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , AddEditExpenses.class );
                startActivity(intent);
            }
        });

        ArrayList<Transaction> dbdata = new ArrayList<Transaction>();

        dbdata.add( new Transaction(10 , 434,  new CategoryModel("Food and Bevarages" , "food") , "foo", "01-09-2019" , 1 )  );
        dbdata.add( new Transaction(10 , 534, new CategoryModel("Food and Bevarages" , "food")  , "foo", "01-09-2019" , 1 )  );
        dbdata.add( new Transaction(10 , 1434, new CategoryModel("Transportation" , "bus")  , "foo", "02-09-2019" , 1 )  );
        dbdata.add( new Transaction(10 , 2434, new CategoryModel("Food and Bevarages" , "food")  , "foo", "02-09-2019" , 1 )  );
        dbdata.add( new Transaction(10 , 3434, new CategoryModel("Food and Bevarages" , "food")  , "foo", "01-09-2019" , 1 )  );
        dbdata.add( new Transaction(10 , 434, new CategoryModel("Transportation" , "bus")  , "foo", "12-09-2019" , 1 )  );
        dbdata.add( new Transaction(10 , 654, new CategoryModel("Food and Bevarages" , "food")  , "foo", "13-09-2019" , 1 )  );
        dbdata.add( new Transaction(10 , 784, new CategoryModel("Food and Bevarages" , "food")  , "foo", "14-09-2019" , 1 )  );
        dbdata.add( new Transaction(10 , 324, new CategoryModel("Food and Bevarages" , "food")  , "foo", "14-09-2019" , 1 )  );
        dbdata.add( new Transaction(10 , 654, new CategoryModel("Food and Bevarages" , "food")  , "foo", "30-09-2019" , 1 )  );


        ArrayList<DailyTransaction> db = Util.sortTransaction( "01-09-2019" , "30-09-2019" , dbdata );
        dailyrv = view.findViewById( R.id.dailyRV );
        dailyrv.setLayoutManager( new LinearLayoutManager(getContext() ));

        DailyExpensesAdapter adapter = new DailyExpensesAdapter( db , getContext() );
        //dailyrv.setNestedScrollingEnabled(false);
        dailyrv.setAdapter(adapter);





        return view;
    }
}
