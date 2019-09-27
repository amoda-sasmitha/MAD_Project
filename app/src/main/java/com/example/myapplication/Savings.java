package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adapters.CategoryAdapter;
import Adapters.SavingAdapter;
import Database.DBhelper;
import Models.CategoryModel;
import Models.SavingModel;


public class Savings extends Fragment {

    private FloatingActionButton plusBtn;
    private RecyclerView savingList;
    Button del;
    DBhelper db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_savings, container, false);

         plusBtn = view.findViewById(R.id.add_saving_btn);
         del =  view.findViewById(R.id.delete_btn);
         savingList = view.findViewById(R.id.savingRE);
         db = new DBhelper(getContext() );

         plusBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent( getActivity() , Add_Saving.class );
                 startActivity(intent);
             }
         });


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        savingList.setLayoutManager( new LinearLayoutManager( getActivity().getApplicationContext()  ));
        savingList.setNestedScrollingEnabled(false);
        ArrayList<SavingModel> arrayList =  db.readAllSavingsWithAmount();

        SavingAdapter adapter = new SavingAdapter(arrayList , getContext() , null);
        savingList.setAdapter( adapter );

        db.readAllSavingsWithAmount();

    }
}
