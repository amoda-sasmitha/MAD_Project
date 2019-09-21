package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import Adapters.Account_SpinnerAdapter;
import Database.DBhelper;
import Models.AccountModel;
import Models.CategoryModel;
import Models.Transaction;


public class EditExpense extends Fragment {

    private EditText amount , category_select , Description , select_date , account;
    ImageView categoryIcon;
    ArrayList<AccountModel> Acc_arrayList;
    CategoryModel currentCategory;
    private Spinner spinner;
    private Button saveBtn;
    DBhelper db;
    int transactionID;
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_expense, container, false);

        amount = view.findViewById(R.id.editText);
        category_select = view.findViewById(R.id.category_select_Text);
        Description = view.findViewById( R.id.edit_description);
        select_date = view.findViewById(R.id.select_date);
        categoryIcon = view.findViewById(R.id.category_icon);
        spinner = view.findViewById(R.id.select_account);
        saveBtn = view.findViewById(R.id.save_btn);

        db = new DBhelper(getContext());
        Acc_arrayList =  db.readAllAccounts();
        Account_SpinnerAdapter Spinner_adapter  = new Account_SpinnerAdapter( getContext() , Acc_arrayList );
        spinner.setAdapter(Spinner_adapter);

        updateDate(view);
        UpdateCategory();

        if( getArguments() != null && getArguments().getSerializable( "expenseedit") != null ){
            Bundle ReceivedData = getArguments();
            Transaction transaction = (Transaction) ReceivedData.getSerializable("expenseedit");
            currentCategory = transaction.getCategoryModel();
            amount.setText(  String.format("%.2f", transaction.getAmount() ) );
            category_select.setText(transaction .getCategoryModel().getName());
            Description.setText(transaction .getDescription());
            transactionID = transaction.getId();

            try {
                select_date.setText( new SimpleDateFormat("EEEE , dd MMMM yyyy").format( new SimpleDateFormat("dd-MM-yyyy").parse(transaction.getDate())) );
            } catch (ParseException e) {
                e.printStackTrace();
            }
            currentCategory = transaction .getCategoryModel();
            categoryIcon.setImageResource(getContext().getResources().getIdentifier(transaction .getCategoryModel().getIcon(),
                    "drawable",
                    getContext().getPackageName()));

            for (int position = 0; position < Acc_arrayList.size(); position++) {
                if (Acc_arrayList.get(position).getId() == transaction .getAccountId()) {
                    spinner.setSelection(position);
                    break;
                }
            }
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amounttemp =   amount.getText().toString().trim();
                double amountx  = (amounttemp.length() > 0 ) ? Double.valueOf(amounttemp) : 0;

                Transaction current = new Transaction();
                current.setId(transactionID);
                current.setAmount(amountx );
                current.setCategoryModel(currentCategory );
                current.setDescription( Description.getText().toString().trim() );
                current.setDate( select_date.getText().toString().trim() );
                current.setAccountId( Acc_arrayList.get( spinner.getSelectedItemPosition() ).getId() );

                //inflate layout
                View layout = getLayoutInflater().inflate( R.layout.toast_message , (ViewGroup) view.findViewById(R.id.toastRoot) );
                TextView text = layout.findViewById(R.id.textMsg);
                CardView background = layout.findViewById(R.id.back);
                Toast toast = new Toast( getContext() );
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER , 0 , 230 );

                boolean result = db.updateTransaction( current);
                if( result == false ){
                    text.setText("Transaction Update Unsuccessfully");
                    background.setCardBackgroundColor( getResources().getColor(R.color.red));
                    text.setTextColor( getResources().getColor( R.color.white ));
                    toast.setView(layout);
                    toast.show();
                }else{
                    background.setCardBackgroundColor( getResources().getColor(R.color.green));
                    text.setTextColor( getResources().getColor( R.color.white ));
                    text.setText("Transaction Update Successfully");
                    toast.setView(layout);
                    getActivity().finish();
                    Intent intent = new Intent( getContext() , MainActivity.class);
                    startActivity(intent);
                    toast.show();
                }
            }
        });

        return view;
    }

    public void updateDate( View view){
        select_date = view.findViewById(R.id.select_date);

        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                DatePickerDialog dialog = new DatePickerDialog( getActivity() , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        try {
                            mMonth++;
                            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(   mYear +"-"+ mMonth +"-"+ mDay);

                            select_date.setText( new SimpleDateFormat("EEEE , dd MMMM yyyy").format(date) );
                        } catch (ParseException e) {
                            select_date.setText( e+"");
                        }
                    }
                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH) );
                dialog.show();
            }
        });
    }

    public void UpdateCategory( ){


        if( getArguments() != null ){
            Bundle bundle = getArguments();

            Transaction current = (Transaction) bundle.getSerializable( "expensedatareturn");
            if(current != null ) {
                transactionID = current.getId();
                amount.setText(String.valueOf(current.getAmount()));
                category_select.setText(current.getCategoryModel().getName());
                Description.setText(current.getDescription());
                select_date.setText(current.getDate());
                currentCategory = current.getCategoryModel();
                categoryIcon.setImageResource(getContext().getResources().getIdentifier(current.getCategoryModel().getIcon(),
                        "drawable",
                        getContext().getPackageName()));

                for (int position = 0; position < Acc_arrayList.size(); position++) {
                    if (Acc_arrayList.get(position).getId() == current.getAccountId()) {
                        spinner.setSelection(position);
                        break;
                    }
                }
            }

        }
        category_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amounttemp =   amount.getText().toString().trim();
                double amountx  = (amounttemp.length() > 0 ) ? Double.valueOf(amounttemp) : 0;

                Category category = new Category();
                Bundle dataBundle = new Bundle();
                Transaction current = new Transaction();

                current.setId(transactionID);
                current.setAmount(amountx );
                current.setDescription( Description.getText().toString().trim() );
                current.setDate( select_date.getText().toString().trim() );
                current.setAccountId( Acc_arrayList.get( spinner.getSelectedItemPosition() ).getId() );
                dataBundle.putSerializable( "expenseDataUpdate" , current );

                category.setArguments( dataBundle );
                getFragmentManager().beginTransaction().replace(R.id.fragment_container , category).commit();

            }
        });

    }

    }

