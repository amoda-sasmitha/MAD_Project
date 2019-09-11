package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Models.DailyTransaction;
import Models.Transaction;
import Util.Util;

public class DailyExpensesAdapter extends RecyclerView.Adapter<DailyExpensesAdapter.DailyExpensesViewHolder> {

    private ArrayList<DailyTransaction> transactions;
    private Context context;

    public DailyExpensesAdapter(ArrayList<DailyTransaction> transactions , Context context) {
        this.transactions = transactions;
        this.context = context;
    }

    @NonNull
    @Override
    public DailyExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.daily_row , parent, false);
        return new DailyExpensesViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyExpensesViewHolder holder, int position) {

        DailyTransaction d = transactions.get(position);
        SimpleDateFormat datef = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat dayf = new SimpleDateFormat("EEEE");
        Date date = null ;
        try {
             date = new SimpleDateFormat("dd-MM-yyyy").parse( d.getDate());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.dateNum.setText( datef.format(date).substring(0 ,2) );
        holder.day.setText( dayf.format(date) );
        holder.date.setText( datef.format(date) );
        holder.rv.setLayoutManager( new LinearLayoutManager(context));

        TransactionAdapter adapter = new TransactionAdapter( d.getTransactions() , context );
        holder.rv.setNestedScrollingEnabled(false);
        holder.rv.setAdapter(adapter);


    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class DailyExpensesViewHolder extends RecyclerView.ViewHolder {

        TextView dateNum , date , day;
        RecyclerView rv;
        public DailyExpensesViewHolder(@NonNull View itemView) {
            super(itemView);
            dateNum = itemView.findViewById(R.id.dateNum);
            date = itemView.findViewById(R.id.date);
            day =  itemView.findViewById(R.id.day);
            rv = itemView.findViewById(R.id.transactionRV);
        }
    }
}
