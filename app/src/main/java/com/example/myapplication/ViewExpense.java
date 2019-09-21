package com.example.myapplication;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import Database.DBhelper;
import Models.Transaction;


public class ViewExpense extends Fragment {

    private TextView data ,CategoryName , date , amount , description , account;
    private ImageButton edit_btn ,delete_btn;
    ImageView categoryIcon;
    Transaction transaction;
    DBhelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_expense ,container , false);

        delete_btn = view.findViewById(R.id.delete_btn);
        edit_btn = view.findViewById(R.id.edit_btn);
        CategoryName = view.findViewById(R.id.category_name);
        date = view.findViewById(R.id.date_text);
        amount = view.findViewById(R.id.Amount_text );
        account  = view.findViewById(R.id.account);
        description = view.findViewById(R.id.description_text);
        categoryIcon = view.findViewById(R.id.category_icon);
        db  = new DBhelper(getContext() );

         Bundle dataBundle = getArguments();
         transaction = (Transaction) dataBundle.getSerializable("Transaction");
         setExpenseData();


        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditExpense editExpense = new EditExpense();
                Bundle dataBundle = new Bundle();
                dataBundle.putSerializable( "expenseedit" , transaction );
                editExpense.setArguments(dataBundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container , editExpense  ).commit();
            }
        });


        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity() );
                dialog.setContentView(R.layout.delete_message);
                Button accept = dialog.findViewById(R.id.accept_btn);
                TextView textView = dialog.findViewById(R.id.deleteText);
                ImageButton close = dialog.findViewById(R.id.close_btn);

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
                        getActivity().finish();
                        boolean result = db.deleteTransaction( transaction.getId() );

                        View layout = getLayoutInflater().inflate( R.layout.toast_message , (ViewGroup) view.findViewById(R.id.toastRoot) );
                        TextView text = layout.findViewById(R.id.textMsg);
                        CardView background = layout.findViewById(R.id.back);
                        Toast toast = new Toast( getContext() );
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER , 0 , 230 );

                        if( result == true ){
                            Intent intent = new Intent(getActivity() , MainActivity.class);
                            startActivity(intent);
                            text.setText("Transaction Delete Successfully");
                            toast.setView(layout);
                            toast.show();
                        }else{
                            text.setText("Something wrong happened, Transaction not deleted ! ");
                            toast.setView(layout);
                            toast.show();
                        }

                    }
                });
            }
        });

        return view;
    }

    public void setExpenseData(){
        CategoryName.setText( transaction.getCategoryModel().getName() );
        try {
            date.setText( new SimpleDateFormat("EEEE , dd MMMM yyyy").format( new SimpleDateFormat("dd-MM-yyyy").parse(transaction.getDate())  ));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //account.setText(transaction.getAccountId() );
        amount.setText( "Rs. "+ String.format("%.2f", transaction.getAmount() )  );
        description.setText( transaction.getDescription() );
        categoryIcon.setImageResource( getContext().getResources().getIdentifier( transaction.getCategoryModel().getIcon() , "drawable", getContext().getPackageName()));
        if( transaction.getCategoryModel().getType().equals("Income")) {
            amount.setTextColor( getContext().getResources().getColor(R.color.green));
        }
    }



    }
