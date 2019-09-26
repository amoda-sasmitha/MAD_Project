package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;

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

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;

import com.github.mikephil.charting.charts.PieChart;


import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import com.savvi.rangedatepicker.CalendarPickerView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import Adapters.PiechartLegendAdapter;
import Database.DBhelper;
import Models.Overview;
import Models.Transaction;
import Util.Util;


public class OverviewX extends Fragment {

    private Overview overview;
    private TextView daterange;
    private PieChart pieChartExpense;
    private PieChart pieChartIncome;
    private ArrayList<Date> dates;
    private RelativeLayout r1 , r2;
    private TextView inflow , outflow;
    private ListView expenseLegend , incomeLegend;
    DBhelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview , container, false);

        db = new DBhelper(getContext());
        pieChartExpense = view.findViewById(R.id.piechartExpense);
        pieChartIncome = view.findViewById(R.id.piechartIncome);
        r1 = view.findViewById( R.id.cardViewAccount4);
        r2 = view.findViewById( R.id.cardViewAccount5);
        inflow = view.findViewById( R.id.inflow );
        outflow = view.findViewById( R.id.outflow );
        expenseLegend = view.findViewById(R.id.piechartLegend1);
        incomeLegend = view.findViewById(R.id.piechartLegend2);

        overview = (Overview) getArguments().getSerializable("Overview");


        daterange = view.findViewById(R.id.daterange);
        daterange.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View view) {
                final Dialog datepicker = new Dialog(getActivity());
                try {
                datepicker.setContentView(R.layout.daterange_select_dialog);
                final CalendarPickerView calendar = datepicker.findViewById(R.id.calendar_view);
                TextView okbtn = datepicker.findViewById(R.id.okbtn);
                TextView cancel = datepicker.findViewById(R.id.cancelbtn);


                @SuppressLint("SimpleDateFormat") Date from  = new SimpleDateFormat("dd-MM-yyyy").parse(overview.getStartDate() );
                @SuppressLint("SimpleDateFormat") Date to  = new SimpleDateFormat("dd-MM-yyyy").parse(overview.getEndDate() );
                dates = new ArrayList<>();
                dates.add(from);
                dates.add(to);

                Log.i("DB", Util.getLastDate( overview.getEndDate() ));
                Calendar firstdate = Calendar.getInstance();
                firstdate.setTime(Objects.requireNonNull(new SimpleDateFormat("dd-MM-yyyy").parse(Util.getFirsttDate(overview.getStartDate()))));
                firstdate.add(Calendar.MONTH , -6);

                Calendar lastdate = Calendar.getInstance();
                lastdate.setTime(Objects.requireNonNull(new SimpleDateFormat("dd-MM-yyyy").parse(Util.getLastDate(overview.getEndDate()))));
                lastdate.add( Calendar.DATE , 1);


                calendar.init( firstdate.getTime() , lastdate.getTime() ) //
                        .inMode(CalendarPickerView.SelectionMode.RANGE)
                        .withSelectedDates(dates);

                okbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            dates = new ArrayList<>();
                            dates = (ArrayList<Date>) calendar.getSelectedDates();
                            setOverviewDaterange( dates.get(0) , dates.get( dates.size() -1 ) );
                            Log.i("DB",  dates.get(0).toString() );
                            Log.i("DB",  dates.get( dates.size() -1 ).toString() );
                            calendar.clearSelectedDates();
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

    @SuppressLint("DefaultLocale")
    private void setOverviewDaterange(Date firstdate, Date lastdate){

        ArrayList<Transaction> incomeL = new ArrayList<>();
        ArrayList<Transaction> expenseL = new ArrayList<>();


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

        //      expense pie chart values set
        pieChartExpense.setUsePercentValues(true);
        pieChartExpense.getDescription().setEnabled(false);
        pieChartExpense.setExtraOffsets(0,5,0,0);
        pieChartExpense.setDrawHoleEnabled(true);
        pieChartExpense.setHoleColor(Color.WHITE);
        pieChartExpense.setTransparentCircleRadius(60f);

        //      Income pie chart values set
        pieChartIncome.setUsePercentValues(true);
        pieChartIncome.getDescription().setEnabled(false);
        pieChartIncome.setExtraOffsets(0,5,0,0);
        pieChartIncome.setDrawHoleEnabled(true);
        pieChartIncome.setHoleColor(Color.WHITE);
        pieChartIncome.setTransparentCircleRadius(60f);



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

            int resID =  getContext().getResources().getIdentifier( item.getCategoryModel().getIcon() , "drawable", getContext().getPackageName());
            Drawable dr = getContext().getDrawable(resID);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable user_icon = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 25, 25, true));

            if( item.getCategoryModel().getType().equals("Expense") ){
                    expenseL.add(item);
                    float value = (float) (item.getAmount() / totalexpense * 100);
                    yvaluesE.add( new PieEntry( value , user_icon ) );
            }else{
                incomeL.add(item);
                float value = (float) (item.getAmount() / totalIncome * 100);
                yvaluesI.add( new PieEntry( value , user_icon  ) );
            }
        }

        PieDataSet Expensedataset = new PieDataSet( yvaluesE , "Expenses");
        Expensedataset.setSliceSpace(3f);
        Expensedataset.setSelectionShift(5f);
        Expensedataset.setColors( new int[] { R.color.pie1 , R.color.pie2 , R.color.pie3 , R.color.pie4 ,R.color.pie5, R.color.pie6 , R.color.pie7 , R.color.pie8 } , getContext() );
        Expensedataset.setValueLineColor(Color.GRAY);
        Expensedataset.setDrawIcons(true);
        Expensedataset.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieDataSet Incomedataset = new PieDataSet( yvaluesI , "Incomes");
        Incomedataset.setSliceSpace(3f);
        Incomedataset.setSelectionShift(5f);
        Incomedataset.setColors( new int[] { R.color.pie8 , R.color.pie7 , R.color.pie6 , R.color.pie4 ,R.color.pie5, R.color.pie3 , R.color.pie2 , R.color.pie1 } , getContext() );
        Incomedataset.setValueLineColor(Color.GRAY);
        Incomedataset.setDrawIcons(true);
        Incomedataset.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData ExpenseData = new PieData(  Expensedataset );
        ExpenseData.setValueTextSize(12f);
        ExpenseData.setDrawValues(true);
        ExpenseData.setValueTextColor(R.color.green);

        PieData  IncomeData = new PieData(  Incomedataset );
        IncomeData.setValueTextSize(12f);
        IncomeData.setDrawValues(true);
        IncomeData.setValueTextColor(R.color.green);

        if( totalexpense == 0 ){
            r1.setVisibility( View.GONE);
        }else if( totalIncome == 0 ){
            r2.setVisibility( View.GONE);
        }else{
            r1.setVisibility( View.VISIBLE);
            r2.setVisibility( View.VISIBLE);

        }

        pieChartExpense.setData(ExpenseData);
        pieChartExpense.animateY(800, Easing.EaseInOutCubic );
        pieChartExpense.invalidate();
        pieChartExpense.getLegend().setEnabled(false);

        pieChartIncome.getLegend().setEnabled(false);
        pieChartIncome.setData(IncomeData);
        pieChartIncome.animateY(800, Easing.EaseInOutCubic );
        pieChartIncome.invalidate();

        inflow.setText( "Rs. "+ String.format("%.2f",  totalIncome  ) );
        outflow.setText( "Rs. "+ String.format("%.2f",  totalexpense  ) );

        PiechartLegendAdapter adapterX = new PiechartLegendAdapter( getContext() , expenseL );
        expenseLegend.setAdapter(adapterX);
        expenseLegend.setNestedScrollingEnabled(false);
        setListViewHeightBasedOnChildren(expenseLegend);
        expenseLegend.setDivider(null);

        PiechartLegendAdapter adapterY = new PiechartLegendAdapter( getContext() , incomeL );
        incomeLegend.setAdapter(adapterY);
        incomeLegend.setNestedScrollingEnabled(false);
        setListViewHeightBasedOnChildren(incomeLegend);
        incomeLegend.setDivider(null);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }



}
