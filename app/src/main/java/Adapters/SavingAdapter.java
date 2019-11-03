package Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.ViewSavingDetails;
import java.util.ArrayList;
import Models.SavingModel;
public class SavingAdapter extends RecyclerView.Adapter<SavingAdapter.SavingViewHolder> {

    private ArrayList<SavingModel> arrayList;
    private OnSavingClickListener onSaving;
    private Context context;
    private Bundle bundle;

    public SavingAdapter(ArrayList<SavingModel> arrayList  , Context context , Bundle bundle ) {
        this.arrayList = arrayList;
        this.context = context;
        this.bundle = bundle;

    }

    @NonNull
    @Override
    public SavingAdapter.SavingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate(R.layout.saving_row , parent , false );
        return new SavingViewHolder(view , this.context );
    }

    @Override
    public void onBindViewHolder(@NonNull SavingAdapter.SavingViewHolder holder, int position) {
        SavingModel saving = arrayList.get(position);
        holder.name.setText( saving.getSavingName() );
        holder.total.setText( "Rs. "+ String.format("%.2f", saving.getTargetAmount()  ));
        holder.current.setText( "Rs. "+ String.format("%.2f", ( saving.getCurrentAmount()  +  saving.getStartAmount() )  ));

        double progressvalue = ( saving.getCurrentAmount()  +  saving.getStartAmount() )  /  saving.getTargetAmount()  * 100;
        holder.progress.setProgress( (int) progressvalue );


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class SavingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name , total , current;
        ProgressBar progress;
        Context context;


        public SavingViewHolder(@NonNull View itemView  , Context context ) {
            super(itemView);
            itemView.setOnClickListener( this );
            this.context=context;
            name = itemView.findViewById(R.id.name);
            total = itemView.findViewById(R.id.total);
            current = itemView.findViewById(R.id.current);

            progress = itemView.findViewById(R.id.progress);
        }


        @Override
        public void onClick(View view) {
         SavingModel saving = arrayList.get(getAdapterPosition());
         Intent intent = new Intent( context , ViewSavingDetails.class );
         intent.putExtra( "Saving" , saving);
         context.startActivity(intent);




        }
    }

    public interface OnSavingClickListener{
        void onSavingClick(int position);
    }




}
