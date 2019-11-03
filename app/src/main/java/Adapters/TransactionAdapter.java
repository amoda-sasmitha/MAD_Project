package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.ViewExpenseDetails;
import java.util.ArrayList;
import Interface.IItemClickListener;
import Models.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>  {

    private ArrayList<Transaction> arrayList;
    private Context context;
    private boolean fromCategory;
    private boolean fromAccount;

    public TransactionAdapter(ArrayList<Transaction> arrayList, Context context, boolean fromCategory, boolean fromAccount) {
        this.arrayList = arrayList;
        this.context = context;
        this.fromCategory = fromCategory;
        this.fromAccount = fromAccount;
    }

    public TransactionAdapter(ArrayList<Transaction> arrayList, Context context, boolean fromCategory) {
        this.arrayList = arrayList;
        this.context = context;
        this.fromCategory = fromCategory;
        this.fromAccount = false;
    }

    public TransactionAdapter(ArrayList<Transaction> arrayList , Context context  ) {
        this.context = context;
        this.arrayList = arrayList;
        this.fromCategory = false;
        this.fromAccount = false;

    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.expense_row , parent , false);
        return new TransactionViewHolder(view  );
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, final int position) {
        Transaction t = arrayList.get(position);
        int resID =    context.getResources().getIdentifier( t.getCategoryModel().getIcon() , "drawable", context.getPackageName());

        if( fromCategory ){
            holder.category.setText( t.getDate() );
        }else{
        holder.category.setText( t.getCategoryModel().getName() );
        }

        if( fromAccount){
            holder.date.setText( t.getDate() );
        }else{
            holder.date.setText( "" );
        }

        holder.description.setText( t.getDescription() );
        holder.amount.setText( "Rs. "+ String.format("%.2f", t.getAmount() )  );
        if( t.getCategoryModel().getType().equals("Income")) {
            holder.amount.setTextColor(context.getResources().getColor(R.color.green));
        }
        holder.icon.setImageResource(resID);

        holder.setItem(new IItemClickListener() {
            @Override
            public void onItemClickListener(View view, int posistion) {

                Intent intent = new Intent( context , ViewExpenseDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Transaction t = arrayList.get(position);
                intent.putExtra( "Transaction" , t );
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView category , description , amount ,date ;
        ImageView icon;
        IItemClickListener item;

        public void setItem(IItemClickListener item) {
            this.item = item;
        }



        public TransactionViewHolder(@NonNull View itemView ) {
            super(itemView);
            category = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.type);
            amount = itemView.findViewById(R.id.amount);
            icon = itemView.findViewById(R.id.icon);
            date = itemView.findViewById(R.id.date);
            itemView.setOnClickListener( this);
        }

        @Override
        public void onClick(View view) {
           item.onItemClickListener( view , getAdapterPosition() );

        }
    }


}
