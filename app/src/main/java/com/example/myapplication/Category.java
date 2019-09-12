package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adapters.CategoryAdapter;
import Database.DBhelper;
import Models.CategoryModel;


public class Category extends Fragment implements CategoryAdapter.OnCategoryClickListener {

    private FloatingActionButton plusBtn;
    private RelativeLayout selectbtn;
    private TextView categoryText;
    private Bundle bundle;
    private RecyclerView Erv, Irv;
    DBhelper db;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category ,container , false);
       // addNewCategory(view);

       // selectbtn = view.findViewById(R.id.category01);


        db = new DBhelper(getContext());
        ArrayList<CategoryModel>  arrayListExpense = db.readAllCategories("Expense");
        ArrayList<CategoryModel>  arrayListIncome = db.readAllCategories("Income");

        Erv = view.findViewById(R.id.expensesRV);
        Irv = view.findViewById(R.id.incomeRV );

        Erv.setLayoutManager( new LinearLayoutManager( getActivity().getApplicationContext()  ));
        Irv.setLayoutManager( new LinearLayoutManager(  getActivity().getApplicationContext()  ));

        CategoryAdapter adapterExpense = new CategoryAdapter(arrayListExpense,   getActivity().getApplicationContext()  , this );
       // CategoryAdapter adapterIncome = new CategoryAdapter(arrayListIncome, getContext() , this  );


        Erv.setAdapter( adapterExpense );
        //Irv.setAdapter( adapterIncome );



//
//        for( CategoryModel item : arrayList ){
//            Log.i( "DB" , "Name : "+ item.getName() + " ID " + item.getID() );
//        }




//         bundle = getArguments();
//        boolean fromExpenses = bundle.getBoolean("FromExpenses");
//
//        if( fromExpenses == true  ){
//           selectbtn.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View view) {
//                   categoryText = view.findViewById(R.id.category_text);
//
//                   String category = categoryText.getText().toString();
//
//
//                   bundle.putString("CategoryName" , category);
//                   AddExpense expense = new AddExpense();
//                   expense.setArguments(bundle);
//                   getFragmentManager().beginTransaction().replace(R.id.fragment_container , expense).commit();
//
//
//               }
//           });
//
//
//        }else{
//
//            selectbtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intend = new Intent(getActivity(), ViewCategoryDetails.class);
//                    startActivity(intend);
//                }
//            });
//
//        }

        return view;
    }



    @Override
    public void onCategoryClick(int position) {
        Toast.makeText( getActivity().getApplicationContext()  , "Position : "+ position , Toast.LENGTH_SHORT ).show();
    }


//    public void addNewCategory(View view){
//        plusBtn = view.findViewById(R.id.add_category_btn);
//        plusBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity() , AddEditCategory.class );
//                startActivity(intent);
//            }
//        });
//
//
//    }
}
