package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.ArrayList;

import Models.Transaction;

public class PiechartLegendAdapter extends ArrayAdapter<Transaction> {

    Context context;
    ArrayList<Transaction> arrayList;
    public PiechartLegendAdapter(@NonNull Context context, ArrayList<Transaction> arrayList) {
        super(context, R.layout.piechart_legend  , arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView= LayoutInflater.from(context).inflate(R.layout.piechart_legend , parent,false);
        Transaction transaction = arrayList.get(position);
        TextView text = rowView.findViewById(R.id.text);
        ImageView icon = rowView.findViewById(R.id.icon);
        text.setText( transaction.getCategoryModel().getName() + "  -  "+  "Rs. "+ String.format("%.2f", transaction.getAmount() ) );
        icon.setImageResource( context.getResources().getIdentifier( transaction.getCategoryModel().getIcon() , "drawable", context.getPackageName()) );


        return rowView;
    }
}
