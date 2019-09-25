package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.savvi.rangedatepicker.CalendarPickerView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Database.DBhelper;
import Models.Overview;
import Models.Transaction;
import Util.Util;


public class OverviewX extends Fragment {

    Overview overview;
    TextView daterange;
    PieChart pieChartExpense;
    DBhelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview , container, false);

        db = new DBhelper(getContext());
        pieChartExpense = view.findViewById(R.id.piechartExpense);
        overview = (Overview) getArguments().getSerializable("Overview");


        daterange = view.findViewById(R.id.daterange);
        daterange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog datepicker = new Dialog(getActivity());
                try {
                datepicker.setContentView(R.layout.daterange_select_dialog);
                final CalendarPickerView calendar = datepicker.findViewById(R.id.calendar_view);
                TextView okbtn = datepicker.findViewById(R.id.okbtn);
                TextView cancel = datepicker.findViewById(R.id.cancelbtn);


                Date from  = new SimpleDateFormat("dd-MM-yyyy").parse(overview.getStartDate() );
                Date to  = new SimpleDateFormat("dd-MM-yyyy").parse(overview.getEndDate() );
                ArrayList<Date> dates = new ArrayList<>();
                dates.add(from);
                dates.add(to);

                Log.i("DB", Util.getLastDate( overview.getEndDate() ));
                Calendar firstdate = Calendar.getInstance();
                firstdate.setTime(new SimpleDateFormat("dd-MM-yyyy").parse( Util.getFirsttDate( overview.getStartDate() ) ) );
                firstdate.add(Calendar.MONTH , -6);

                Calendar lastdate = Calendar.getInstance();
                lastdate.setTime(new SimpleDateFormat("dd-MM-yyyy").parse( Util.getLastDate( overview.getEndDate() ) ) );
                lastdate.add( Calendar.DATE , 1);


                calendar.init( firstdate.getTime() , lastdate.getTime() ) //
                        .inMode(CalendarPickerView.SelectionMode.RANGE)
                        .withSelectedDates(dates);

                okbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                             ArrayList<Date> dates = (ArrayList<Date>) calendar.getSelectedDates();
                            setOverviewDaterange( dates.get(0) , dates.get( dates.size() -1 ) );
                            Log.i("DB",  dates.get(0).toString() );
                            Log.i("DB",  dates.get( dates.size() -1 ).toString() );

                            datepicker.dismiss();

                    }
                });

                datepicker.show();
                datepicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            setOverviewDaterange( new SimpleDateFormat("dd-MM-yyyy").parse(overview.getStartDate()) , new SimpleDateFormat("dd-MM-yyyy").parse(overview.getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void setOverviewDaterange( Date firstdate , Date lastdate){
        SimpleDateFormat normaldate = new SimpleDateFormat("dd-MM-yyyy");
        String datetextString;
        double totalIncome = 0;
        double totalexpense = 0;

        if( firstdate.equals(lastdate) ){
            datetextString = new SimpleDateFormat("EEE, dd MMMM yyyy").format(firstdate);
        }else{
            datetextString = new SimpleDateFormat("dd MMM yyyy").format(firstdate);
            datetextString += "  -  ";
            datetextString += new SimpleDateFormat("dd MMM yyyy").format(lastdate);
        }
        daterange.setText(datetextString);
        ArrayList<Transaction> categoricalData = db.transactionGroupByCategories(  normaldate.format(firstdate), normaldate.format(lastdate) );

        pieChartExpense.setUsePercentValues(true);
        pieChartExpense.getDescription().setEnabled(false);
        pieChartExpense.setExtraOffsets(0,5,0,0);

        pieChartExpense.setDrawHoleEnabled(true);
        pieChartExpense.setHoleColor(Color.WHITE);
        pieChartExpense.setTransparentCircleRadius(60f);

        ArrayList<PieEntry> yvaluesE = new ArrayList<>();
        ArrayList<PieEntry> yvaluesI = new ArrayList<>();

        for ( Transaction item : categoricalData ) {
            if( item.getCategoryModel().getType().equals("Expense") ){
                totalexpense  += item.getAmount();
            }else{
                totalIncome += item.getAmount();
            }
        }

        for ( Transaction item : categoricalData ) {
            if( item.getCategoryModel().getType().equals("Expense") ){

                int resID =  getContext().getResources().getIdentifier( item.getCategoryModel().getIcon() , "drawable", getContext().getPackageName());
                Drawable dr = getContext().getDrawable(resID);

                Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
                // Scale it to 50 x 50
                Drawable user_icon = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 25, 25, true));

                    float value = (float) (item.getAmount() / totalexpense * 100);
               
                            yvaluesE.add( new PieEntry( value , user_icon ) );
            }else{
                float value = (float) (item.getAmount() / totalIncome * 100);
                yvaluesI.add( new PieEntry( value , item.getCategoryModel().getName()  ) );
            }
        }

        PieDataSet Expensedataset = new PieDataSet( yvaluesE , "Expenses");
        Expensedataset.setSliceSpace(3f);
        Expensedataset.setSelectionShift(5f);
        Expensedataset.setColors( new int[] { R.color.pie1 , R.color.pie2 , R.color.pie3 , R.color.pie4 ,R.color.pie5, R.color.pie6 , R.color.pie7 , R.color.pie8 } , getContext() );
        Expensedataset.setValueLineColor(Color.GRAY);
        Expensedataset.setDrawIcons(true);
        Expensedataset.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);



        PieData ExpenseData = new PieData( Expensedataset );
        ExpenseData.setValueTextSize(12f);
        ExpenseData.setDrawValues(true);
        ExpenseData.setValueTextColor(R.color.green);

        pieChartExpense.setData(ExpenseData);
        pieChartExpense.animateY(800, Easing.EaseInOutCubic );
        pieChartExpense.invalidate();




    }





}
