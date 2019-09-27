package Adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.view_account_indetails;
import java.util.ArrayList;
import java.util.Collection;
import Interface.IItemClickListener;
import Models.AccountModel;
import Util.Util;
public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountAdapterViewHolder> implements Filterable {
    Context context;
    ArrayList<AccountModel> arrayList;
    ArrayList<AccountModel> arrayListfull;
    public AccountAdapter(Context context, ArrayList<AccountModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayListfull = new ArrayList<>(arrayList);
    }
    @NonNull
    @Override
    public AccountAdapter.AccountAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext() ).inflate( R.layout.account_row , parent , false);//get predefine design in recycle view
        return new AccountAdapterViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.AccountAdapterViewHolder holder, int position) {
        AccountModel account = arrayList.get(position);
        holder.name.setText( account.getAccountName() );
        holder.amount.setText( "Rs. " + String.format("%.2f", account.getBalance() ) );
        holder.type.setText( account.getAccountType() );
        holder.icon.setImageResource(Util.getAccountIcon( account.getAccountType(), context ) );
        holder.setItem(new IItemClickListener() {
            @Override
            public void onItemClickListener(View view, int posistion) {
                Intent intent = new Intent(context , view_account_indetails.class);
                AccountModel account = arrayList.get(posistion);
                intent.putExtra( "Account" ,  account);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class AccountAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, type, amount;
        ImageView icon;
        IItemClickListener item;
        public void setItem(IItemClickListener item) {
            this.item = item;
        }
        public AccountAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.AccountName);
            type = itemView.findViewById(R.id.AccountType);
            amount = itemView.findViewById( R.id.AccountAmount);
            icon = itemView.findViewById(R.id.AccountIcon);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            item.onItemClickListener( view, getAdapterPosition() );
        }
    }
    @Override
    public Filter getFilter() {
        return accountFilter;
    }
    private Filter accountFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence ch) {
            ArrayList<AccountModel> FilteredList = new ArrayList<>();

            if( ch == null || ch.length() == 0 ){
                FilteredList.addAll(arrayListfull);
            }else{
                String pattern = ch.toString().toLowerCase().trim();
                for (AccountModel model : arrayListfull) {
                    if( model.getAccountName().toLowerCase().contains(pattern) ){
                        FilteredList.add(model);

                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = FilteredList;
            return  results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            arrayList.clear();
            arrayList.addAll((Collection<? extends AccountModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
