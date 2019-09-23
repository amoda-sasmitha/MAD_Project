package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adapters.CategoryIconAdapter;
import Adapters.DailyExpensesAdapter;
import Adapters.TransactionAdapter;
import Database.DBhelper;
import Models.CategoryModel;
import Models.DailyTransaction;
import Models.Transaction;
import Util.Util;

public class Test extends AppCompatActivity  {


    private String[] icons;
    private GridView gridView;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        icons =  getResources().getStringArray(R.array.category_Icons);




    }


}
