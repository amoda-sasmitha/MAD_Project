package com.example.myapplication;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import Database.DBhelper;
import Models.SavingModel;
import Util.Util;
public class ViewSaving extends Fragment {
    private SavingModel saving;
    private Button addTransaction;
    private ImageButton delete_btn , edit_btn;
    private  TextView name , total, current , start , description;
    private ProgressBar progress;
    private TextView today, totamount;
    DBhelper db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_view_saving, container, false);
        Bundle bundle = getArguments();
        saving = (SavingModel) bundle.getSerializable("Saving");
        name = view.findViewById(R.id.saving_text);
        total = view.findViewById(R.id.full_amount_text);
        current = view.findViewById(R.id.current_amount_text);
        start = view.findViewById(R.id.account);
        description = view.findViewById(R.id.description_text);
        progress = view.findViewById(R.id.progressBar);
        today = view.findViewById(R.id.text02);
        totamount = view.findViewById(R.id.textView12);
        db = new DBhelper(getContext() );
        name.setText( saving.getSavingName() );
        total.setText( "Rs. "+ String.format("%.2f", saving.getTargetAmount() ));
        current.setText( "Rs. "+ String.format("%.2f", ( saving.getCurrentAmount()  +  saving.getStartAmount() ) ));
        start.setText( "Rs. "+ String.format("%.2f", saving.getStartAmount() ));
        description.setText( saving.getSavingDescription());
        totamount.setText( "Rs. "+ String.format("%.2f", Util.getTotalBalance(getContext()) )  );
        today.setText(new SimpleDateFormat("dd MMMM yyyy").format(new Date()));
        double progressvalue = ( saving.getCurrentAmount()  +  saving.getStartAmount() )  /  saving.getTargetAmount()  * 100;
        progress.setProgress((int) progressvalue );
        edit_btn = view.findViewById(R.id.edit_btn);
        delete_btn = view.findViewById(R.id.delete_btn);
        addTransaction = view.findViewById(R.id.add_btn);
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , AddEditExpenses.class );
                intent.putExtra( "Saving" , saving);
                startActivity(intent);
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog( getContext() );
                dialog.setContentView(R.layout.delete_message);
                Button accept = dialog.findViewById(R.id.accept_btn);
                TextView textView = dialog.findViewById(R.id.deleteText);
                ImageButton close = dialog.findViewById(R.id.close_btn);
                textView.setText("Are you sure , you want to delete this saving ?");
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         boolean lastResult = db. deleteSaving( saving.getID() );
                        View layout = getLayoutInflater().inflate( R.layout.toast_message , (ViewGroup) view.findViewById(R.id.toastRoot) );
                        TextView text = layout.findViewById(R.id.textMsg);
                        CardView background = layout.findViewById(R.id.back);
                        Toast toast = new Toast( getContext() );
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER , 0 , 230 );
                        if( lastResult == false ){
                            text.setText("Saving Deleted Unsuccessfully");
                            toast.setView(layout);
                            toast.show();
                        }else{
                            text.setText("Saving Deleted Successfully");
                            toast.setView(layout);
                            getActivity().finish();
                            toast.show();
                        }
                    }
                });
            }
        });
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity() , EditSaving.class );
                intent.putExtra( "Saving" , saving);
                startActivity(intent);
            }
        });

        return view;
    }

}
