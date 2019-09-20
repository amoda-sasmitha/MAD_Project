package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import Adapters.Account_SpinnerAdapter;
import Database.DBhelper;
import Models.AccountModel;
import Models.Transaction;


public class AddExpense extends Fragment {
    private Button save_btn;
   private EditText select_date, category_select ,amount ,Description ;
   private ImageView categoryIcon;
   private Spinner spinner;
    ArrayList<AccountModel> Acc_arrayList;
   DBhelper db;

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense ,container , false);

        save_btn = view.findViewById(R.id.save_btn);
        amount = view.findViewById(R.id.editText);
        category_select = view.findViewById(R.id.category_select_Text);
        Description = view.findViewById( R.id.edit_description);
        select_date = view.findViewById(R.id.select_date);
        categoryIcon = view.findViewById(R.id.category_icon);
         spinner = view.findViewById(R.id.select_account);

         db = new DBhelper(getContext());
        Acc_arrayList =  db.readAllAccounts();
        Account_SpinnerAdapter Spinner_adapter  = new Account_SpinnerAdapter( getContext() , Acc_arrayList );
        spinner.setAdapter(Spinner_adapter);

        updateDate(view);
        UpdateCategory();

        return view;
    }


    public void updateDate( View view){
        select_date = view.findViewById(R.id.select_date);
        select_date.setText(  new SimpleDateFormat("EEEE , dd MMMM yyyy").format( new Date())  );
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
            Transaction current = (Transaction) bundle.getSerializable( "expenseData");
            amount.setText( String.valueOf( current.getAmount() ) );
            category_select.setText( current.getCategoryModel().getName() );
            Description.setText( current.getDescription() );
            select_date.setText(current.getDate() );
            categoryIcon.setImageResource(  getContext().getResources().getIdentifier( current.getCategoryModel().getIcon() ,
                    "drawable",
                     getContext().getPackageName()));

            for (int position = 0; position < Acc_arrayList.size() ; position++) {
                if (Acc_arrayList.get(position).getId() == current.getAccountId()) {
                    spinner.setSelection(position);
                    break;
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

                current.setAmount(amountx );
                current.setDescription( Description.getText().toString().trim() );
                current.setDate( select_date.getText().toString().trim() );
                current.setAccountId( Acc_arrayList.get( spinner.getSelectedItemPosition() ).getId() );
                dataBundle.putSerializable( "expenseData" , current );

                category.setArguments( dataBundle );
                getFragmentManager().beginTransaction().replace(R.id.fragment_container , category).commit();

            }
        });

    }
}
