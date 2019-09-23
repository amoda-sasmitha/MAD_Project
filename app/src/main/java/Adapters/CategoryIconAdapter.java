package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.myapplication.R;

public class CategoryIconAdapter  extends BaseAdapter {

    private String[] array;
    private Context  context;

    public CategoryIconAdapter( Context context) {
        array = context.getResources().getStringArray(R.array.category_Icons);
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.length;
    }

    @Override
    public String getItem(int i) {
        return array[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 =  LayoutInflater.from(context).inflate(R.layout.icon_layout , null);
        ImageView icon = view1.findViewById(R.id.icon);

        icon.setImageResource( context.getResources().getIdentifier( array[i] , "drawable", context.getPackageName()) );
        return view1;
    }
}
