package Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AddExpense;
import com.example.myapplication.EditExpense;
import com.example.myapplication.Expenses;
import com.example.myapplication.R;
import com.example.myapplication.ViewCategoryDetails;

import java.util.ArrayList;
import java.util.ResourceBundle;

import Models.CategoryModel;
import Models.Transaction;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<CategoryModel> arrayList;
    private OnCategoryClickListener onCategory;
    private Context context;
    private Bundle bundle;

    public CategoryAdapter(ArrayList<CategoryModel> arrayList  ,Context context , Bundle bundle ) {
        this.arrayList = arrayList;
        this.context = context;
        this.bundle = bundle;


    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate(R.layout.category_row , parent , false );
        return new CategoryViewHolder(view , this.context );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        CategoryModel c = arrayList.get(position);
        int resID =    context.getResources().getIdentifier( c.getIcon() , "drawable", context.getPackageName());
        holder.name.setText( c.getName());
        holder.type.setText( c.getType() );
        holder.icon.setImageResource(resID);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView icon;
        TextView name , type;
        Context context;


        public CategoryViewHolder(@NonNull View itemView  , Context context ) {
            super(itemView);
            itemView.setOnClickListener( this );
            this.context=context;
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            icon = itemView.findViewById( R.id.icon);
        }


        @Override
        public void onClick(View view) {

              CategoryModel c = arrayList.get( getAdapterPosition() );
              if( bundle.getSerializable( "expenseData") != null ){

                  FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                  Transaction current = (Transaction) bundle.getSerializable("expenseData");
                  current.setCategoryModel(c);
                  bundle.putSerializable( "expenseData" , current );
                  AddExpense expense = new AddExpense();
                  expense.setArguments(bundle);
                  manager.beginTransaction().replace(R.id.fragment_container,expense).commit();

              }else if( bundle.getSerializable( "expenseDataUpdate") != null ) {

                  FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                  Transaction current = (Transaction) bundle.getSerializable("expenseDataUpdate");
                  current.setCategoryModel(c);
                  bundle.putSerializable( "expensedatareturn" , current );
                  EditExpense expense = new  EditExpense();
                  expense.setArguments(bundle);
                  manager.beginTransaction().replace(R.id.fragment_container,expense).commit();

              }else{
                  Intent intent = new Intent( context , ViewCategoryDetails.class );
                  intent.putExtra("ID" ,  String.valueOf( c.getID() ) );
                  context.startActivity(intent);
              }



        }
    }

    public interface OnCategoryClickListener{
        void onCategoryClick(int position);
    }




}
