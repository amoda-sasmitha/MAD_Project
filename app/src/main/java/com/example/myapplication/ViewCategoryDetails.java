package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Adapters.DailyExpensesAdapter;
import Adapters.TransactionAdapter;
import Database.DBhelper;
import Models.CategoryModel;
import Models.Transaction;
import Util.Util;

public class ViewCategoryDetails extends AppCompatActivity {

    private ImageButton edit_categorybtn, delete_btn;
    private TextView categoryName , description, category_type , category_t, date, totamount;
    private ImageView icon;
    private String ID;
    private RecyclerView latestTransactions;
    private CategoryModel category;
    private DBhelper db;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category_details);

        db = new DBhelper(this);
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");

        categoryName = findViewById(R.id.category_text);
        description = findViewById(R.id.category_view_description);
        category_type = findViewById(R.id.categoryType);
        icon = findViewById(R.id.categoryDetailIcon1);
        category_t = findViewById( R.id.main_sub_type_view);
        latestTransactions = findViewById(R.id.latestT);
        date = findViewById(R.id.category_date1);
        category = db.readSingleCategory( ID);
        totamount = findViewById(R.id.textView12);
        categoryName.setText( category.getName() );
        category_type.setText(category.getType());
        description.setText( category.getDescription() );
        int resID = getResources().getIdentifier( category.getIcon() , "drawable", getPackageName());
        icon.setImageResource(resID );

        if( category.getID() > 15 ){
            category_t.setText("User Category");
        }else {
            category_t.setText("Main Category");
        }

        date.setText(new SimpleDateFormat("dd MMMM yyyy").format(new Date()));
        totamount.setText( "Rs. "+ String.format("%.2f", Util.getTotalBalance(ViewCategoryDetails.this ))  );
        delete_btn = findViewById(R.id.delete_btn);
        edit_categorybtn = (ImageButton) findViewById(R.id.edit_btn);

        edit_categorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edit_categorybtn= findViewById(R.id.edit_btn);

                Intent intent = new Intent(ViewCategoryDetails.this , Edit_Category.class);

                Bundle bundle = new Bundle();
               bundle.putSerializable("Category" , category);


                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog( ViewCategoryDetails.this );
                dialog.setContentView(R.layout.delete_message);
                Button accept = dialog.findViewById(R.id.accept_btn);
                TextView textView = dialog.findViewById(R.id.deleteText);
                ImageButton close = dialog.findViewById(R.id.close_btn);
                textView.setText("Are you sure , you want to delete this category ?");
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        db.deleteAllTransactionInCategory( ID );
                        boolean lastResult = db.deleteCategory(ID);

                        View layout = getLayoutInflater().inflate( R.layout.toast_message , (ViewGroup) view.findViewById(R.id.toastRoot) );
                        TextView text = layout.findViewById(R.id.textMsg);
                        CardView background = layout.findViewById(R.id.back);
                        Toast toast = new Toast( ViewCategoryDetails.this);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER , 0 , 230 );

                        if( lastResult == false ){
                            text.setText("Category Deleted Unsuccessfully");
                            toast.setView(layout);
                            toast.show();
                        }else{
                            Intent intent = new Intent( ViewCategoryDetails.this , MainActivity.class);
                            startActivity(intent);
                            text.setText("Category Deleted Successfully");
                            toast.setView(layout);
                            finish();
                            toast.show();
                        }

                    }
                });
            }
        });

        ArrayList<Transaction> latest = db.latestTransactions(ID);
        TransactionAdapter Tadapter = new TransactionAdapter( latest , this , true);
        latestTransactions.setHasFixedSize(true);
        latestTransactions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) );
        latestTransactions.setAdapter(Tadapter);
        latestTransactions.setNestedScrollingEnabled(false);


    }




}
