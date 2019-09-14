package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ViewExpenseDetails;

import java.util.ArrayList;

import Interface.IItemClickListener;
import Models.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>  {

    private ArrayList<Transaction> arrayList;
    private Context context;



    public TransactionAdapter(ArrayList<Transaction> arrayList , Context context  ) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.expense_row , parent , false);
        return new TransactionViewHolder(view  );
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction t = arrayList.get(position);
        int resID =    context.getResources().getIdentifier( t.getCategoryModel().getIcon() , "drawable", context.getPackageName());
        holder.category.setText( t.getCategoryModel().getName() );
        holder.description.setText( t.getDescription() );
        holder.amount.setText( "Rs. "+ String.format("%.2f", t.getAmount() )  );
        holder.icon.setImageResource(resID);

        holder.setItem(new IItemClickListener() {
            @Override
            public void onItemClickListener(View view, int posistion) {

                Intent intent = new Intent( context , ViewExpenseDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView category , description , amount ;
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
            itemView.setOnClickListener( this);
        }

        @Override
        public void onClick(View view) {
           item.onItemClickListener( view , getAdapterPosition() );

        }
    }


}
