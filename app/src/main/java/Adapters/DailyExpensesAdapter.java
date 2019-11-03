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
import Interface.IItemClickListener;
import Models.DailyTransaction;
public class DailyExpensesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private ArrayList<DailyTransaction> transactions;
    private Context context;
    public DailyExpensesAdapter(ArrayList<DailyTransaction> transactions , Context context) {
        this.transactions = transactions;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.daily_row , parent, false);
        return new DailyExpensesViewHolder( view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            DailyTransaction d = transactions.get(position);
            SimpleDateFormat datef = new SimpleDateFormat("dd MMM yyyy");
            SimpleDateFormat dayf = new SimpleDateFormat("EEEE");
            Date date = null;
            try {
                date = new SimpleDateFormat("dd-MM-yyyy").parse(d.getDate());

            } catch (ParseException e) {
                e.printStackTrace();
            }
            ((DailyExpensesViewHolder) holder).dateNum.setText(datef.format(date).substring(0, 2));
            ((DailyExpensesViewHolder) holder).day.setText(dayf.format(date));
            ((DailyExpensesViewHolder) holder).date.setText(datef.format(date));
            TransactionAdapter adapter = new TransactionAdapter(d.getTransactions(), context  );
            ((DailyExpensesViewHolder) holder).rv.setHasFixedSize(true);
            ((DailyExpensesViewHolder) holder).rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            ((DailyExpensesViewHolder) holder).rv.setAdapter(adapter);
            ((DailyExpensesViewHolder) holder).rv.setNestedScrollingEnabled(false);
            ((DailyExpensesViewHolder) holder).setI(new IItemClickListener() {
                @Override
                public void onItemClickListener(View view, int posistion) {
//                    Toast.makeText(context, "Cliked", Toast.LENGTH_SHORT).show();
                }
            });

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
    public class OverviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView inflow , outflow;
        public OverviewViewHolder(@NonNull View itemView) {
            super(itemView);
            inflow = itemView.findViewById(R.id.inflow);
            outflow = itemView.findViewById(R.id.outflow);
        }
        @Override
        public void onClick(View view) {

        }
    }
    public class DailyExpensesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dateNum , date , day;
        RecyclerView rv;
        IItemClickListener i;
        public void setI(IItemClickListener i) {
            this.i = i;
        }
        public DailyExpensesViewHolder(@NonNull View itemView) {
            super(itemView);
            dateNum = itemView.findViewById(R.id.dateNum);
            date = itemView.findViewById(R.id.date);
            day =  itemView.findViewById(R.id.day);
            rv = itemView.findViewById(R.id.transactionRV);
            itemView.setOnClickListener( this);
        }

        @Override
        public void onClick(View view) {
            i.onItemClickListener( view , getAdapterPosition() );
        }
    }
}
