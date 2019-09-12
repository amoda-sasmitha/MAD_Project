package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.ResourceBundle;

import Models.CategoryModel;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<CategoryModel> arrayList;
    private OnCategoryClickListener onCategory;
    private Context context;

    public CategoryAdapter(ArrayList<CategoryModel> arrayList  ,Context context ,  OnCategoryClickListener onCategory) {
        this.arrayList = arrayList;
        this.context = context;
        this.onCategory = onCategory;


    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate(R.layout.category_row , parent , false );
        return new CategoryViewHolder(view , this.context , this.onCategory );
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
        OnCategoryClickListener onCategory;

        public CategoryViewHolder(@NonNull View itemView  , Context context , OnCategoryClickListener onCategory) {
            super(itemView);
          this.onCategory = onCategory;
            itemView.setOnClickListener( this );
            this.context=context;
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            icon = itemView.findViewById( R.id.icon);
        }


        @Override
        public void onClick(View view) {
           onCategory.onCategoryClick(getAdapterPosition() );
        }
    }

    public interface OnCategoryClickListener{
        void onCategoryClick(int position);
    }




}
