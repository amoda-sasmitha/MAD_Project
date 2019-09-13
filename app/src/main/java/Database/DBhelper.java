package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import Models.AccountModel;
import Models.CategoryModel;
import Models.Transaction;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "monefy.db";

    public DBhelper(Context context) {
        super(context, DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_ct_transaction = "CREATE TABLE "+ DBConfig.Transactions.TABLE_NAME  + " ( " +
                DBConfig.Transactions.COLUMN_NAME_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "+
                DBConfig.Transactions.Column_NAME_AMOUNT + " DOUBLE,"+
                DBConfig.Transactions.Column_NAME_CATEGORY_ID + " INT , "+
                DBConfig.Transactions.Column_NAME_DESCRIPTION + " TEXT , "+
                DBConfig.Transactions.Column_NAME_DATE + " DATE , "+
                DBConfig.Transactions.Column_NAME_ACCOUNT_ID + " INT , "
                + "FOREIGN KEY(" + DBConfig.Transactions.Column_NAME_CATEGORY_ID + ") REFERENCES "
                + DBConfig.Categories.TABLE_NAME + "(CID) , "
                + "FOREIGN KEY(" + DBConfig.Transactions.Column_NAME_ACCOUNT_ID  + ") REFERENCES "
                + DBConfig.Accounts.TABLE_NAME  + "(AID) "  + ");";

        String sql_ct_categories = "CREATE TABLE "+ DBConfig.Categories.TABLE_NAME  + " ( " +
                DBConfig.Categories.COLUMN_NAME_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "+
                DBConfig.Categories.COLUMN_NAME_CNAME + " TEXT , "+
                DBConfig.Categories.COLUMN_NAME_DESCRIPTION + " TEXT , " +
                DBConfig.Categories.COLUMN_NAME_TYPE + " TEXT , " +
                DBConfig.Categories.COLUMN_NAME_ICON + " TEXT " + " );";

        String sql_ct_accounts = "CREATE TABLE "+ DBConfig.Accounts.TABLE_NAME  + " ( " +
                DBConfig.Accounts.COLUMN_NAME_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "+
                DBConfig.Accounts.COLUMN_NAME_ANAME + " TEXT , "+
                DBConfig.Accounts.COLUMN_NAME_AMOUNT + " DOUBLE , " +
                DBConfig.Accounts.COLUMN_NAME_TYPE + " TEXT , " +
                DBConfig.Accounts.COLUMN_NAME_NUMBER + "TEXT, " +  DBConfig.Accounts.COLUMN_NAME_DESCRIPTION +"TEXT " +");";


        db.execSQL(sql_ct_categories);
        db.execSQL(sql_ct_accounts);
        db.execSQL(sql_ct_transaction);
        setDefaultCategories(db);
        setDefaultAccount(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    /*
     ---------------------------Vishvi Wathasha---------------------------------------------
     */

    public boolean insertCategory(CategoryModel categoryModel){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put( DBConfig.Categories.COLUMN_NAME_CNAME , categoryModel.getName() );
        contentValues.put( DBConfig.Categories.COLUMN_NAME_DESCRIPTION , categoryModel.getDescription() );
        contentValues.put( DBConfig.Categories.COLUMN_NAME_TYPE , categoryModel.getType() );
        contentValues.put( DBConfig.Categories.COLUMN_NAME_ICON , categoryModel.getIcon() );

        long result = db.insert( DBConfig.Categories.TABLE_NAME ,null, contentValues );

        if (result > 0){
            //succes msg
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<CategoryModel> readAllCategories(String type) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = { DBConfig.Categories.COLUMN_NAME_ID , DBConfig.Categories.COLUMN_NAME_CNAME , DBConfig.Categories.COLUMN_NAME_DESCRIPTION ,
        DBConfig.Categories.COLUMN_NAME_TYPE , DBConfig.Categories.COLUMN_NAME_ICON};

        String Selection = DBConfig.Categories.COLUMN_NAME_TYPE + " = ? ";
        String selectionArgs[]  = { type };

        Cursor values = db.query(DBConfig.Categories.TABLE_NAME ,projection,Selection ,selectionArgs ,null,null, null);

        ArrayList<CategoryModel> categoryModels = new ArrayList<CategoryModel>();

        while (values.moveToNext()){
            String name = values.getString(values.getColumnIndexOrThrow(DBConfig.Categories.COLUMN_NAME_CNAME));
            int id = values.getInt( values.getColumnIndexOrThrow( DBConfig.Categories.COLUMN_NAME_ID ));
            CategoryModel c = new CategoryModel();
            c.setID(id);
            c.setName(name);
            c.setDescription( values.getString( values.getColumnIndexOrThrow( DBConfig.Categories.COLUMN_NAME_DESCRIPTION)));
            c.setType( values.getString( values.getColumnIndexOrThrow( DBConfig.Categories.COLUMN_NAME_TYPE)));
            c.setIcon( values.getString( values.getColumnIndexOrThrow( DBConfig.Categories.COLUMN_NAME_ICON )));
            categoryModels.add(c);

        }
        return categoryModels;
    }

    //-----------------------------------------------Amoda Sasmitha-------------------------------------------------------

    public ArrayList<Transaction> readAllTransactions( String from , String to , String accout ){


        return null;
    }

    public void setDefaultCategories(SQLiteDatabase db){
        ArrayList<CategoryModel> categories = new ArrayList<>();
        categories.add( new CategoryModel( "Transportation" , "description" , "Expense" , "bus"    ));
        categories.add( new CategoryModel( "Bills and Utilities" , "description" , "Expense" , "bill"    ));
        categories.add( new CategoryModel( "Investments" , "description" , "Expense" , "invest"    ));
        categories.add( new CategoryModel( "Food and Beverages" , "description" , "Expense" , "food"    ));
        categories.add( new CategoryModel( "Health and Fitness" , "description" , "Expense" , "health"    ));
        categories.add( new CategoryModel( "Family" , "description" , "Expense" , "family"    ));
        categories.add( new CategoryModel( "Entertainment" , "description" , "Expense" , "entertainment"    ));
        categories.add( new CategoryModel( "Gifts and Donations" , "description" , "Expense" , "gift"    ));
        categories.add( new CategoryModel( "Education" , "description" , "Expense" , "education"    ));
        categories.add( new CategoryModel( "Savings" , "description" , "Expense" , "savings"    ));

        categories.add( new CategoryModel( "Awards" , "description" , "Income" , "award"    ));
        categories.add( new CategoryModel( "Selling" , "description" , "Income" , "sell"    ));
        categories.add( new CategoryModel( "Interest Money" , "description" , "Income" , "interest"    ));
        categories.add( new CategoryModel( "Gifts" , "description" , "Income" , "get"    ));
        categories.add( new CategoryModel( "Salary" , "description" , "Income" , "salary"    ));

        db.beginTransaction();
        try {
            for (CategoryModel item : categories) {
                ContentValues contentValues = new ContentValues();
                contentValues.put( DBConfig.Categories.COLUMN_NAME_CNAME , item.getName() );
                contentValues.put( DBConfig.Categories.COLUMN_NAME_DESCRIPTION , item.getDescription() );
                contentValues.put( DBConfig.Categories.COLUMN_NAME_TYPE , item.getType() );
                contentValues.put( DBConfig.Categories.COLUMN_NAME_ICON , item.getIcon() );
                db.insert( DBConfig.Categories.TABLE_NAME ,null, contentValues );
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        Log.i( "DB" , "Default Categories Created" );
    }

    public void setDefaultAccount( SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put( DBConfig.Accounts.COLUMN_NAME_ANAME , "Wallet"  );
        contentValues.put( DBConfig.Accounts.COLUMN_NAME_TYPE , "Wallet" );
        contentValues.put( DBConfig.Accounts.COLUMN_NAME_AMOUNT , 0 );

        db.insert( DBConfig.Accounts.TABLE_NAME ,null, contentValues );
    }

    //----------------------------------------------Padula Guruge ------------------------------------------------------

    public boolean addAccount(AccountModel accountModel){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put( DBConfig.Accounts.COLUMN_NAME_ANAME, accountModel.getAccountName());
        contentValues.put( DBConfig.Accounts.COLUMN_NAME_TYPE, accountModel.getAccountType());
        contentValues.put( DBConfig.Accounts.COLUMN_NAME_AMOUNT, accountModel.getAmount());
        contentValues.put( DBConfig.Accounts.COLUMN_NAME_NUMBER, accountModel.getAccountNumber());
        contentValues.put( DBConfig.Accounts.COLUMN_NAME_DESCRIPTION, accountModel.getAccountDescription());

        long result = db.insert( DBConfig.Accounts.TABLE_NAME ,null, contentValues );

        if (result > 0){

            return true;
        }
        else{
            return false;
        }
    }


}
