package Database;

import android.provider.BaseColumns;

public final class DBConfig {
//tables
    protected static class Transactions implements BaseColumns {
        protected static final String TABLE_NAME = "transactions";
        protected static final String COLUMN_NAME_ID = "TID";
        protected static final String Column_NAME_AMOUNT = "Amount";
        protected static final String Column_NAME_CATEGORY_ID = "CID";
        protected static final String Column_NAME_DESCRIPTION = "TDescription";
        protected static final String Column_NAME_DATE = "Date";
        protected static final String Column_NAME_ACCOUNT_ID = "AID";

    }

    protected static class Categories implements BaseColumns {
        protected static final String TABLE_NAME = "categories";
        protected static final String COLUMN_NAME_ID = "CID";
        protected static final String COLUMN_NAME_CNAME = "name";
        protected static final String COLUMN_NAME_DESCRIPTION = "Description";
        protected static final String COLUMN_NAME_TYPE = "type";
        protected static final String COLUMN_NAME_ICON = "icon";


    }

    protected static class Accounts implements BaseColumns {
        protected static final String TABLE_NAME = "accounts";
        protected static final String COLUMN_NAME_ID = "AID";
        protected static final String COLUMN_NAME_ANAME = "name";
        protected static final String COLUMN_NAME_AMOUNT = "amount";
        protected static final String COLUMN_NAME_TYPE = "type";
        protected static final String COLUMN_NAME_NUMBER = "number";
        protected static final String COLUMN_NAME_DESCRIPTION = "description";



    }

    protected static class Savings implements BaseColumns{
        protected static final String TABLE_NAME = "savings";
        protected static final String COLUMN_NAME_ID = "SID";
        protected static final String COLUMN_NAME_SAVINGNAME = "name";
        protected static final String COLUMN_NAME_SAVINGDISCRIPTION = "description";
        protected static final String COLUMN_NAME_TARGETAMOUNT = "tAmount";
        protected static final String COLUMN_NAME_STARTAMOUNT = "sAmount";
    }
}
