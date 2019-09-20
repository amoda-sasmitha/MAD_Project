package Util;

import android.content.Context;

import java.util.ArrayList;

import Models.DailyTransaction;
import Models.Transaction;

public class Util {

    public static ArrayList<DailyTransaction> sortTransaction( String from ,String to , ArrayList<Transaction> data ){

        ArrayList<DailyTransaction> arraylist = new ArrayList<DailyTransaction>();

        int start = Integer.parseInt( from.substring(0 ,2 ));
        int end = Integer.parseInt( to.substring(0 ,2 ));
        String prefx = from.substring(2);
        String dateX;
        int count = 0;
        while( start <= end  ) {

            if( start < 10 )
                dateX = "0"+start+prefx;
            else
                dateX = start+prefx;

            DailyTransaction dt = new DailyTransaction( dateX );
            ArrayList<Transaction> tr = new ArrayList<Transaction>();
            count = 0;

            for( Transaction item : data ) {

                if( start ==  getDatefromString( item.getDate() )   ) {
                    tr.add(item);
                    count++;
                }
            }

            if( count > 0 ) {
                dt.setTransactions(tr);
                arraylist.add(dt);
            }

            start++;

        }



        return arraylist;
    }


    public static int getDatefromString( String date) {
        return  Integer.parseInt( date.substring(0 ,2 ));
    }

    public static int getAccountIcon(String iconname , Context context){
        int ID = 0 ;
       if( iconname.equals("Card") ) {
            ID = context.getResources().getIdentifier("creditcard", "drawable", context.getPackageName());
       }else if( iconname.equals("Bank Account") ){
            ID = context.getResources().getIdentifier("bank_account", "drawable", context.getPackageName());
       }else if( iconname.equals("Online Wallet") ){
            ID = context.getResources().getIdentifier("online_wallet", "drawable", context.getPackageName());
       }else if( iconname.equals("Wallet") ){
            ID = context.getResources().getIdentifier("money", "drawable", context.getPackageName());
       }
        return ID;
    }

}

