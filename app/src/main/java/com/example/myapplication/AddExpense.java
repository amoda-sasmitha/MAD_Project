package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class AddExpense extends Fragment {

   private EditText select_date, category_select ,amount ,Description ,account;
   private ImageView categoryIcon;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense ,container , false);
        amount = view.findViewById(R.id.editText);
        category_select = view.findViewById(R.id.category_select_Text);
        Description = view.findViewById( R.id.edit_description);
        select_date = view.findViewById(R.id.select_date);
        account = view.findViewById(R.id.select_account);
        categoryIcon = view.findViewById(R.id.category_icon);
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

              Bundle dataBundle = getArguments();
              category_select.setText( dataBundle.getString( "CategoryName"  )  );
              amount.setText( dataBundle.getString("Amount"));
              Description.setText( dataBundle.getString("Description"));
              select_date.setText( dataBundle.getString("Date"));
              account.setText(dataBundle.getString("Account"));
              categoryIcon.setImageResource(0);
          }

        category_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Category category = new Category();
                Bundle dataBundle = new Bundle();
                dataBundle.putBoolean( "FromExpenses" , true);
                dataBundle.putString("CategoryName" , category_select.getText().toString() );
                dataBundle.putString( "Amount" , amount.getText().toString() );
                dataBundle.putString("Date" , select_date.getText().toString() );
                dataBundle.putString( "Account" , account.getText().toString() );
                dataBundle.putString("Description" , Description.getText().toString() );
                category.setArguments( dataBundle);

                getFragmentManager().beginTransaction().replace(R.id.fragment_container , category).commit();

            }
        });

    }
}
