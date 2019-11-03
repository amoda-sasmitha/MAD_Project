package Adapters;
import android.content.Context;
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
import Models.AccountModel;
import Util.Util;
public class Account_SpinnerAdapter extends ArrayAdapter<AccountModel> {
    public Account_SpinnerAdapter(@NonNull Context context, ArrayList<AccountModel> arrayList){
        super(context, 0 , arrayList );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(  position , convertView, parent);
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return  initView(  position , convertView, parent);
    }
    private View initView( int position , View convertView, ViewGroup parent){
        if( convertView == null ){
            convertView = LayoutInflater.from(getContext() ).inflate(R.layout.account_spinner , parent , false );
        }
        AccountModel currentItem = getItem(position);
        ImageView icon = convertView.findViewById(R.id.icon);
        TextView name = convertView.findViewById(R.id.name);
        icon.setImageResource( Util.getAccountIcon( currentItem.getAccountType(), getContext() ) );
        name.setText( currentItem.getAccountName() );
        name.setTag(  currentItem.getId() );
        return convertView;
    }
}
