package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adapters.CategoryAdapter;
import Adapters.DailyExpensesAdapter;
import Database.DBhelper;
import Models.CategoryModel;
import Models.DailyTransaction;
import Models.Transaction;
import Util.Util;

public class Test extends AppCompatActivity  {

    private TextView AccountBtn ;
    private FloatingActionButton plusBtn;
    private TextView categoryText , amount, date ;
    private RecyclerView dailyrv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


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


        ArrayList<DailyTransaction> db = Util.sortTransaction( "01-09-2019" , "14-09-2019" , dbdata );
        dailyrv = findViewById( R.id.dailyRV );
        dailyrv.setLayoutManager( new LinearLayoutManager( this , LinearLayoutManager.VERTICAL , false  ));


        dailyrv.setHasFixedSize(true);
        DailyExpensesAdapter adapter = new DailyExpensesAdapter( db ,this   );
        dailyrv.setAdapter(adapter);
        dailyrv.setNestedScrollingEnabled(false);

    }


}
