package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Adapters.SavingAdapter;
import Database.DBhelper;
import Models.SavingModel;
import Util.Util;
public class Savings extends Fragment {
    private FloatingActionButton plusBtn;
    private RecyclerView savingList;
    Button del;
    DBhelper db;
    private TextView date, amount;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_savings, container, false);
         plusBtn = view.findViewById(R.id.add_saving_btn);
         del =  view.findViewById(R.id.delete_btn);
         savingList = view.findViewById(R.id.savingRE);
         db = new DBhelper(getContext() );
         date = view.findViewById(R.id.saving_date1);
         amount = view.findViewById(R.id.textView4);
         amount.setText( "Rs. "+ String.format("%.2f", Util.getTotalBalance(getContext()) )  );
         date.setText(new SimpleDateFormat("dd MMMM yyyy").format(new Date()));
         plusBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent( getActivity() , Add_Saving.class );
                 startActivity(intent);
             }
         });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        savingList.setLayoutManager( new LinearLayoutManager( getActivity().getApplicationContext()  ));
        savingList.setNestedScrollingEnabled(false);
        ArrayList<SavingModel> arrayList =  db.readAllSavingsWithAmount();
        SavingAdapter adapter = new SavingAdapter(arrayList , getContext() , null);
        savingList.setAdapter( adapter );
        db.readAllSavingsWithAmount();

    }
}
