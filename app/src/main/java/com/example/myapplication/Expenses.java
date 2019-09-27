package com.example.myapplication;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Adapters.DailyExpensesAdapter;
import Database.DBhelper;
import Models.DailyTransaction;
import Models.Overview;
import Models.Transaction;
import Util.Util;
public class Expenses extends Fragment {
    private TextView AccountBtn, accountbtn , PeriodText , DateIn , TotalAmount , inflow , outflow;
    private FloatingActionButton plusBtn;
    private TextView categoryText , amount, date ;
    private RecyclerView dailyrv;
    private ImageButton prev , next;
    private Button monthly , daily;
    private RelativeLayout overviewbtn;
    boolean ismonthly;
    DBhelper db;
    String period;
    Overview overview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        monthly = view.findViewById(R.id.button);
        daily = view.findViewById(R.id.button3);
        AccountBtn = view.findViewById(R.id.Account);
        accountbtn = view.findViewById(R.id.textView3);
        plusBtn = view.findViewById(R.id.add_expenses_btn);
        prev = view.findViewById(R.id.imageButton);
        next = view.findViewById(R.id.imageButton2);
        dailyrv = view.findViewById( R.id.dailyRV );
        PeriodText = view.findViewById( R.id.textView);
        DateIn = view.findViewById(R.id.textView2);
        TotalAmount = view.findViewById(R.id.Account);
        inflow = view.findViewById(R.id.inflow);
        outflow = view.findViewById(R.id.outflow);
        overviewbtn = view.findViewById(R.id.cardViewAccount3);
        if( getArguments() != null ){
            period = getArguments().getString("period");
            ismonthly = getArguments().getBoolean("ismonthly");
        }else{
            period =  new SimpleDateFormat("dd-MM-yyyy").format( new Date() );
            ismonthly = false;
        }
        if(ismonthly){
            buttonClick("Monthly");
        }else{
            buttonClick("Daily");
        }
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , AddEditExpenses.class );
                startActivity(intent);
            }
        });
        setAccountClick();
        db = new DBhelper(getContext());
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                if(ismonthly){
                bundle.putString( "period" , Util.addMonths( period , -1 ) );
                }else{
                bundle.putString( "period" , Util.addDays( period, -1) );
                }
                bundle.putBoolean( "ismonthly" , ismonthly );
                Expenses.this.setArguments( bundle );
                ft.detach( Expenses.this).attach( Expenses.this ).commit();

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                if(ismonthly){

                    bundle.putString( "period" , Util.addMonths( period , 1 ) );
                }else{
                    bundle.putString( "period" , Util.addDays( period, 1) );
                }
                bundle.putBoolean( "ismonthly" , ismonthly );
                Expenses.this.setArguments( bundle );
                ft.detach( Expenses.this).attach( Expenses.this ).commit();

            }
        });
        monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("Monthly");
                ismonthly = true;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString( "period" ,  period  );
                bundle.putBoolean( "ismonthly" , ismonthly );
                Expenses.this.setArguments( bundle );
                ft.detach( Expenses.this).attach( Expenses.this ).commit();

            }
        });
        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick("Daily");
                ismonthly = false;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString( "period" ,  period  );
                bundle.putBoolean( "ismonthly" , ismonthly );
                Expenses.this.setArguments( bundle );
                ft.detach( Expenses.this).attach( Expenses.this ).commit();

            }
        });

        return view;
    }
    public void setAccountClick(){
        accountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Accounts()).commit();
            }
        });
        AccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Accounts()).commit();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        TotalAmount.setText( "Rs. "+ String.format("%.2f", Util.getTotalBalance(getContext()) )  );
        ArrayList<Transaction> dbdata = null;
        ArrayList<DailyTransaction> dbx = null;
        if(ismonthly){
            PeriodText.setText( Util.MonthFormatter( period) );
            DateIn.setText( "      "+period.substring(6 ,10 )+"      " );
            dbdata = db.readAllTransactions( Util.getFirsttDate(period) , Util.getLastDate(period) );
            dbx = Util.sortTransaction( Util.getFirsttDate(period) , Util.getLastDate(period) , dbdata );
        }else{
            try {
                DateIn.setText( new SimpleDateFormat("dd-MMMM-yyyy").format(new SimpleDateFormat("dd-MM-yyyy").parse(period) ) );
            } catch (ParseException e) {
                e.printStackTrace();
            }
            PeriodText.setText( Util.DateFormatter(period));
            dbdata = db.readAllTransactions( period ,period);
            dbx = Util.sortTransaction( period , period , dbdata );
        }
        dailyrv.setLayoutManager( new LinearLayoutManager( getActivity().getApplicationContext() ));
        dailyrv.setNestedScrollingEnabled(false);
        DailyExpensesAdapter adapter = new DailyExpensesAdapter( dbx ,getActivity().getApplicationContext()  );
        dailyrv.setAdapter(adapter);
        overview = Util.getOverview( dbdata , period , ismonthly);
        inflow.setText( "Rs. "+ String.format("%.2f", overview.getInflow() ) );
        outflow.setText( "Rs. "+ String.format("%.2f", overview.getOutflow() ) );
        overviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( overview.getInflow() > 0 || overview.getOutflow() > 0  ) {
                    OverviewX overviewX = new OverviewX();
                    Bundle data = new Bundle();
                    data.putSerializable("Overview", overview);
                    overviewX.setArguments(data);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, overviewX).commit();
                }
            }
        });

    }
    public void buttonClick(String btn){
        if(btn.equals("Monthly")){
            monthly.setTextColor( getResources().getColor(R.color.black) );
            monthly.setBackgroundTintList( ColorStateList.valueOf(getResources().getColor(R.color.white)));
            daily.setTextColor( getResources().getColor(R.color.btnGray) );
            daily.setBackgroundTintList( ColorStateList.valueOf( getResources().getColor(R.color.lightGray )));
        }else if( btn.equals("Daily")){
            daily.setTextColor( getResources().getColor(R.color.black) );
            daily.setBackgroundTintList( ColorStateList.valueOf(getResources().getColor(R.color.white)));
            monthly.setTextColor( getResources().getColor(R.color.btnGray) );
            monthly.setBackgroundTintList( ColorStateList.valueOf( getResources().getColor(R.color.lightGray )));
        }
    }
}
