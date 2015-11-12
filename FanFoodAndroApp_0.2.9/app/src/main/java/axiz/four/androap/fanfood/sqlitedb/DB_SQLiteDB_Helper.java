package axiz.four.androap.fanfood.sqlitedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_SQLiteDB_Helper extends SQLiteOpenHelper {
	
	private static final int DB_version =1;
	private static final String DB_Name = "fanfood_db";

    public static final String T1_ORDER="item_order";

    public static final String T1F1_INDX="p_indx";
    public static final String T1F2_VENUENAME_ID="venue_name_id";
    public static final String T1F3_CONCESSIO_ID="concession_id";
    public static final String T1F4_currentItemID="current_item_id";
    public static final String T1F5_itemName="item_name";
    public static final String T1F6_itemPriceString="item_price_string";
    public static final String T1F7_itemPrice="item_price";
    public static final String T1F8_itemQantity="item_qantity";
    public static final String T1F9_totalPrice="total_price";
    public static final String T1F10_isCancel="is_cancel";
    public static final String T1F11_userComments="user_comments";
    public static final String T1F12_date="order_date";
    public static final String T1F13_order_time="order_time";
	
	private static final String SQL_T1creat_Order_tbl="CREATE TABLE "+T1_ORDER+ "(" +
	T1F1_INDX + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
	T1F2_VENUENAME_ID +" TEXT, " +
	T1F3_CONCESSIO_ID +" TEXT, " +
	T1F4_currentItemID +" TEXT, " +
	T1F5_itemName+" TEXT, " +
	T1F6_itemPriceString +" TEXT, " +
	T1F7_itemPrice+" TEXT, " +
	T1F8_itemQantity+" TEXT, " +
	T1F9_totalPrice +" TEXT, "	+
	T1F10_isCancel +" TEXT, "	+
	T1F11_userComments +" TEXT, "	+
	T1F12_date +" TEXT, " +
	T1F13_order_time +" TEXT);";
	

	
	Context ourContext;
    SQLiteDatabase db;

	public DB_SQLiteDB_Helper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_Name, null, DB_version);
		// TODO Auto-generated constructor stub
		ourContext = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*
		db.execSQL("DROP TABLE IF EXISTS "+SQL_T1creat_Profile_tbl);
		db.execSQL("DROP TABLE IF EXISTS "+SQL_T2creat_TESTNM_tbl);
		db.execSQL("DROP TABLE IF EXISTS "+SQL_T3creat_TEST_VAL_tbl);
		db.execSQL("DROP TABLE IF EXISTS "+SQL_T4creat_MEDICIN_tbl);
		db.execSQL("DROP TABLE IF EXISTS "+SQL_T5creat_MEDICIN_DOSE_tbl);
		db.execSQL("DROP TABLE IF EXISTS "+SQL_T6creat_ALARM_tbl);
		db.execSQL("DROP TABLE IF EXISTS "+SQL_T7creat_TUTORIAL_tbl);
		*/
      //  db.execSQL("DROP TABLE " + T1_ORDER);
//        db.execSQL("DROP TABLE IF EXISTS "+ T1_ORDER);
		db.execSQL(""+SQL_T1creat_Order_tbl);

        this.db = db;

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

    public void deleteAll(SQLiteDatabase db)
    {
        db.delete(T1_ORDER, null, null);
        db.close();
    }

}
