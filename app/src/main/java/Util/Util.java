package Util;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import Database.DBhelper;
import Models.AccountModel;
import Models.DailyTransaction;
import Models.Overview;
import Models.Transaction;

public class Util {

    public static ArrayList<DailyTransaction> sortTransaction( String from ,String to , ArrayList<Transaction> data ){

        ArrayList<DailyTransaction> arraylist = new ArrayList<>();

        int start = Integer.parseInt( from.substring(0 ,2 ));
        int end = Integer.parseInt( to.substring(0 ,2 ));
        String prefx = from.substring(2);
        String dateX;
        int count = 0;

        double inflow = 0;
        double outflow = 0;

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
                    if(item.getCategoryModel().getType().equals("Income"))
                        inflow += item.getAmount();
                    else
                        outflow += item.getAmount();
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



    public static String addDays(String date, int days)
    {
        Date myDate = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            myDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.DATE, days); //minus number would decrement the days


        return format.format(cal.getTime() ) ;
    }

    public static String addMonths(String month, int months)
    {
        Date myDate = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            myDate = format.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.MONTH, months ); //minus number would decrement the days
        return format.format(cal.getTime() ) ;
    }

    public static String DateFormatter(String date)
    {
        String formattedDate;
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        if ( format.format(today).equals( date) ){
            formattedDate = "  Today  ";
        }else if( addDays(format.format(today), -1 ).equals( date)   ){
            formattedDate = "Yesterday";
        }else{
            Date dd = null;
            try {
              dd  = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            formattedDate = new SimpleDateFormat("EEEE").format(dd);
        }

        return formattedDate ;
    }

    public static String MonthFormatter(String date)
    {
        String formattedDate;
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        if (  getFirsttDate(format.format(today)).equals( getFirsttDate(date))  ){
            formattedDate = "This Month";
        }else if( getFirsttDate( addMonths( format.format(today) , -1)).equals( getFirsttDate(date))   ){
            formattedDate = "Last Month";
        }else{
            Date dd = null;
            try {
                dd  = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            formattedDate = new SimpleDateFormat("MMMM").format(dd);
        }

        return formattedDate ;
    }

    public static String getFirsttDate(String date)
    {
        Date myDate = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            myDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return  dateFormat.format(cal.getTime());
    }

    public static String getLastDate(String date)
    {
        Date myDate = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            myDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        return  dateFormat.format(cal.getTime());
        //asd
    }

    public static double getTotalBalance(Context context){
        DBhelper db = new DBhelper(context);
        double total = 0;
        ArrayList<AccountModel> accounts = db.readAllAccountsWithBalance();
        for( AccountModel account : accounts){
            total += account.getBalance();
        }
        return total;
    }

    public static  Overview getOverview(ArrayList<Transaction> arrayList , String period , boolean ismonthly ){
        Overview overview = new Overview();
        for ( Transaction transaction : arrayList){
            if( transaction.getCategoryModel().getType().equals("Income"))
                overview.setInflow(  overview.getInflow()+transaction.getAmount()  );
            else
                overview.setOutflow(  overview.getOutflow()+transaction.getAmount()  );
        }

        if( ismonthly == true ){
            overview.setStartDate( getFirsttDate(period));
            overview.setEndDate( getLastDate(period) );
        }else{
            overview.setStartDate( period);
            overview.setEndDate( period );
        }

        return overview;
    }

}

