package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adapters.CategoryAdapter;
import Database.DBhelper;
import Models.CategoryModel;


public class Category extends Fragment {

    private FloatingActionButton plusBtn;
    private EditText search;
    private RelativeLayout selectbtn;
    private TextView categoryText;
    private Bundle bundle;
    CategoryAdapter adapterExpense , adapterIncome;
    ArrayList<CategoryModel>  arrayListExpense = null, arrayListIncome = null ;
    private RecyclerView Erv, Irv;
    DBhelper db;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category ,container , false);

        search = view.findViewById(R.id.search);
        plusBtn = view.findViewById(R.id.add_category_btn);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , AddEditCategory.class );
                startActivity(intent);
            }
        });

        if( getArguments() != null ) {
            bundle = getArguments();
        }

        db = new DBhelper(getContext());
        arrayListExpense = db.readAllCategories("Expense");
        arrayListIncome = db.readAllCategories("Income");

        Erv = view.findViewById(R.id.expensesRV); //expense recycle view
        Irv = view.findViewById(R.id.incomeRV ); //income recycle view

        Erv.setLayoutManager( new LinearLayoutManager( getActivity().getApplicationContext()  ));
        Irv.setLayoutManager( new LinearLayoutManager(  getActivity().getApplicationContext()  ));

        Erv.setNestedScrollingEnabled(false);
        Irv.setNestedScrollingEnabled(false);

         adapterExpense = new CategoryAdapter(arrayListExpense,   getActivity().getApplicationContext()  , bundle ); //passing th4 expense categories in a bundle
         adapterIncome = new CategoryAdapter(arrayListIncome,  getActivity().getApplicationContext() , bundle ); //passing th4 expense categories in a bundle


        Erv.setAdapter( adapterExpense );
        Irv.setAdapter( adapterIncome );


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
         arrayListExpense = db.readAllCategories("Expense");
          arrayListIncome = db.readAllCategories("Income");
         adapterExpense = new CategoryAdapter(arrayListExpense,   getActivity() , bundle  );
         adapterIncome = new CategoryAdapter(arrayListIncome, getContext()  , bundle );
        Erv.setAdapter( adapterExpense );
        Irv.setAdapter( adapterIncome );

        //Search function
        search.addTextChangedListener(new TextWatcher() {
            @Override
            //No action is  getting done in this method
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            //no action is done
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            // Live search function happens as a letter types
            @Override
            public void afterTextChanged(Editable editable) {

                adapterExpense.getFilter().filter(editable.toString() ); //in expense recycle view
                adapterIncome.getFilter().filter(editable.toString()); //in income recycle view


            }
        });
    }



}
