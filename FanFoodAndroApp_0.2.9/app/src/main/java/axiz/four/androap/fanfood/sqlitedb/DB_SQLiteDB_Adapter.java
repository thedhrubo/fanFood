package axiz.four.androap.fanfood.sqlitedb;

import java.util.ArrayList;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;

public class DB_SQLiteDB_Adapter {
	private Emran_a emrAObj;
	private TableNames tblNmObj;
	private SQLiteDatabase G_dgital_diabetes_db;
	private DB_SQLiteDB_Helper G_SQLiteDB_Helper;
	private Context G_context;
	private Activity G_activity;

    public DB_SQLiteDB_Adapter(Context g_Context) {
        super();
//        emrAObj = new Emran_a(g_Context, g_Activity);
        tblNmObj = new TableNames();
        G_SQLiteDB_Helper = new DB_SQLiteDB_Helper(g_Context, "", null, 1);
        G_dgital_diabetes_db = G_SQLiteDB_Helper.getWritableDatabase();
        G_context = g_Context;
//        G_activity = g_Activity;
    }

	public DB_SQLiteDB_Adapter(Context g_Context, Activity g_Activity) {
		super();
		emrAObj = new Emran_a(g_Context, g_Activity);
		tblNmObj = new TableNames();
		G_SQLiteDB_Helper = new DB_SQLiteDB_Helper(g_Context, "", null, 1);
        G_dgital_diabetes_db = G_SQLiteDB_Helper.getWritableDatabase();
		G_context = g_Context;
		G_activity = g_Activity;
	}

	// For open database method
	public void open() {
		G_dgital_diabetes_db = G_SQLiteDB_Helper.getWritableDatabase();
	}

	// Close database Method
	public void close() {
		G_dgital_diabetes_db.close();
	}

    //Drop data table
    public void DeleteTable(){
        open();
        G_SQLiteDB_Helper.deleteAll(G_dgital_diabetes_db);
        close();
    }

	public long Insert_TB1_Order(DataAbstractEvent profile_data) {
		ContentValues values = new ContentValues();
		values.put(tblNmObj.T1F2_VENUENAME_ID, profile_data.getTxt1());
		values.put(tblNmObj.T1F3_CONCESSIO_ID, profile_data.getTxt2());
		values.put(tblNmObj.T1F4_currentItemID, profile_data.getTxt3());
		values.put(tblNmObj.T1F5_itemName, profile_data.getTxt4());
		values.put(tblNmObj.T1F6_itemPriceString, profile_data.getTxt5());
		values.put(tblNmObj.T1F7_itemPrice, profile_data.getTxt6());
		values.put(tblNmObj.T1F8_itemQantity, profile_data.getTxt7());
		values.put(tblNmObj.T1F9_totalPrice, profile_data.getTxt8());
		values.put(tblNmObj.T1F10_isCancel, profile_data.getTxt9());
		values.put(tblNmObj.T1F11_userComments, profile_data.getTxt10());
		values.put(tblNmObj.T1F12_date, profile_data.getTxt11());
        values.put(tblNmObj.T1F13_order_time, profile_data.getTxt12());
		open();
		long lon = G_dgital_diabetes_db.insert(tblNmObj.T1_ORDER, null,
				values);
		close();
		return lon;
	}


	/* Update */
	public long Update_TB1_Profile(DataAbstractEvent profile_data) {
		System.out.println("\n\t---------------------------------");
		ContentValues values = new ContentValues();
		 values.put(tblNmObj.T1F2_VENUENAME_ID, profile_data.getTxt1());
        //System.out.println("\t\t--------------- " + profile_data.getTxt1());
		// + " ------------------");
		values.put(tblNmObj.T1F3_CONCESSIO_ID, profile_data.getTxt2());
		// System.out.println("\t\t--------------- " + profile_data.getST2()
		// + " ------------------");
		values.put(tblNmObj.T1F4_currentItemID, profile_data.getTxt3());
		// System.out.println("\t\t--------------- " + profile_data.getST3()
		// + " ------------------");
		values.put(tblNmObj.T1F5_itemName, profile_data.getTxt4());
		// System.out.println("\t\t--------------- " + profile_data.getST4()
		// + " ------------------");
		values.put(tblNmObj.T1F6_itemPriceString, profile_data.getTxt5());
		// System.out.println("\t\t--------------- " + profile_data.getST5()
		// + " ------------------");

		values.put(tblNmObj.T1F7_itemPrice, profile_data.getTxt6());
		// System.out.println("\t\t--------------- " + profile_data.getST5()
		// + " ------------------");

		// + " ------------------");
		values.put(tblNmObj.T1F8_itemQantity, profile_data.getTxt8());
		// System.out.println("\t\t--------------- " + profile_data.getST7()
		// + " ------------------");
		values.put(tblNmObj.T1F9_totalPrice, profile_data.getTxt9());
		// System.out.println("\t\t--------------- " + profile_data.getST8()
		// + " ------------------");
		values.put(tblNmObj.T1F10_isCancel, profile_data.getTxt10());
		// System.out.println("\t\t--------------- " + profile_data.getST9()
		// + " ------------------");
		values.put(tblNmObj.T1F11_userComments, profile_data.getTxt11());
		// System.out.println("\t\t--------------- " + profile_data.getST10()
		// + " ------------------");
		values.put(tblNmObj.T1F12_date, profile_data.getTxt12());
		// System.out.println("\t\t--------------- " + profile_data.getST11()
		// + " ------------------");

		open();
		// long lon = (long) G_dgital_diabetes_db.update(tblNmObj.T1_PROFILE,
		// values, "p_ida = ? ", new String[] { profile_data.getST1()} );
		long lon = G_dgital_diabetes_db
				.update(tblNmObj.T1_ORDER, values, tblNmObj.T1F3_CONCESSIO_ID+ " = '"
						+ profile_data.getTxt1() + "' ", null);
		close();
		// System.out.println("\n\t--------------Long val: " + lon
		// + "-------------------");
		return lon;
	}


	/** Get all information */
	public Cursor GetAll_info(int TblName) {

		String[] columnOf1_PROFILE = { tblNmObj.T1F2_VENUENAME_ID, tblNmObj.T1F3_CONCESSIO_ID,
				tblNmObj.T1F4_currentItemID, tblNmObj.T1F5_itemName, tblNmObj.T1F6_itemPriceString,
				tblNmObj.T1F7_itemPrice, tblNmObj.T1F8_itemQantity, tblNmObj.T1F9_totalPrice,
				tblNmObj.T1F10_isCancel, tblNmObj.T1F11_userComments,
				tblNmObj.T1F12_date, tblNmObj.T1F13_order_time };

		Cursor cs = null;
		open();
		switch (TblName) {
		case 1: {
			cs = G_dgital_diabetes_db.query(tblNmObj.T1_ORDER,
					columnOf1_PROFILE, null, null, null, null, null);
			close();
			break;
		}
		case 2: {

			close();
			break;
		}
		case 3: {

			close();
			break;
		}

		case 4: {

			close();
			break;
		}

		case 5: {

			close();
			break;
		}
		case 6: {

			close();
			break;
		}
		case 7: {

			close();
			break;
		}

		default: {
			cs = null;
			close();
			break;
		}
		}
		return cs;
	}

	/** Get all information */
	public ArrayList<DataAbstractEvent> GetAll_Data(int TblName) {

		ArrayList<DataAbstractEvent> dataAry = new ArrayList<DataAbstractEvent>();

		String[] columnOf1_PROFILE = {
                tblNmObj.T1F2_VENUENAME_ID, tblNmObj.T1F3_CONCESSIO_ID,
				tblNmObj.T1F4_currentItemID, tblNmObj.T1F5_itemName,
                tblNmObj.T1F6_itemPriceString, tblNmObj.T1F7_itemPrice,
				tblNmObj.T1F8_itemQantity, tblNmObj.T1F9_totalPrice,
				tblNmObj.T1F10_isCancel, tblNmObj.T1F11_userComments,
				tblNmObj.T1F12_date, tblNmObj.T1F13_order_time,"itmprs", "countsmitm"};

		Cursor cs = null;

		open();

		switch (TblName) {
		case 1: {
			// System.out.println("#### Start Case 1:");
            //cs = G_dgital_diabetes_db.query(tblNmObj.T1_ORDER,columnOf1_PROFILE, null, null, null, null, null);
            try {
                cs = G_dgital_diabetes_db.rawQuery("SELECT *, SUM(" + tblNmObj.T1F9_totalPrice + ")itmprs, SUM("+tblNmObj.T1F8_itemQantity+")countsmitm FROM " + tblNmObj.T1_ORDER + " GROUP BY " + tblNmObj.T1F4_currentItemID + "", null);
            }catch(Exception ex){
                System.out.println("### SQL: "+ex.toString());
            }
//			cs = G_dgital_diabetes_db.query(tblNmObj.T1_ORDER,
//					columnOf1_PROFILE, null, null, null, null, null);
			// System.out.println("#### \tStart Case :" + cs.getCount());
			if (cs != null && cs.getCount() > 0) {
				while (cs.moveToNext()) {
					// String
					// a=""+cs.getString(cs.getColumnIndex(columnOf1_PROFILE[0])),
					// b=""+cs.getString(cs.getColumnIndex(columnOf1_PROFILE[1]));
					// System.out.println("#### Start forloop: FName:"+a+" LName "+b);
					// System.out.println("#### Start forloop: FName:"+" LName");

                    DataAbstractEvent obj = new DataAbstractEvent(cs.getString(cs
							.getColumnIndex(columnOf1_PROFILE[0])),
							cs.getString(cs
									.getColumnIndex(columnOf1_PROFILE[1])),
							cs.getString(cs
									.getColumnIndex(columnOf1_PROFILE[2])),
							cs.getString(cs
									.getColumnIndex(columnOf1_PROFILE[3])),
							cs.getString(cs
									.getColumnIndex(columnOf1_PROFILE[4])),
							cs.getString(cs
									.getColumnIndex(columnOf1_PROFILE[5])),
							cs.getString(cs
									.getColumnIndex(columnOf1_PROFILE[6])),
							cs.getString(cs
									.getColumnIndex(columnOf1_PROFILE[7])),
							cs.getString(cs
									.getColumnIndex(columnOf1_PROFILE[8])),
							cs.getString(cs
									.getColumnIndex(columnOf1_PROFILE[9])),
							cs.getString(cs
									.getColumnIndex(columnOf1_PROFILE[10])),
							cs.getString(cs
									.getColumnIndex(columnOf1_PROFILE[11])),
                            cs.getString(cs
                                    .getColumnIndex(columnOf1_PROFILE[12])),
                            cs.getString(cs
                                    .getColumnIndex(columnOf1_PROFILE[13])));

                    System.out.println("### SQL: "+cs.getString(cs
                                    .getColumnIndex(columnOf1_PROFILE[12]))+" - "+cs.getString(cs
                            .getColumnIndex(columnOf1_PROFILE[13])));
					/*
					 * System.out.println("\t#### 15:"+cs.getString(cs
					 * .getColumnIndex
					 * (columnOf1_PROFILE[15]))+columnOf1_PROFILE[
					 * 16]+" 16:"+cs.getString(cs
					 * .getColumnIndex(columnOf1_PROFILE[16])));
					 */
					dataAry.add(obj);
				}
			}
			close();
			break;
		}
		case 2: {

			close();
			break;
		}
		case 3: {

			close();
			break;
		}

		case 4: {

			close();
			break;
		}

		case 5: {

			close();
			break;
		}
		case 6: {

			close();
			cs.close();
			break;
		}
		case 7: {

			close();
			break;
		}
		default: {
			cs = null;
			close();
			break;
		}
		}

		return dataAry;
	}

//	public DataAbstractEvent GetLastTest_Data(int selecter) {
//		ArrayList<DataAbstractEvent> dataAry2 = GetAll_Data(3);// 1 For TestVal table
//		int sz = dataAry2.size();
//        DataAbstractEvent data = null;
//		for (int i = 0; i < sz; i++) {
//            DataAbstractEvent data2 = dataAry2.get(i);
//			String testType = data2.getTxt6();
//
//			if (selecter == 1 && testType.endsWith("H")) {// h =HB1C
//				// HB1C
//				data = data2;
//			}
//			if (selecter == 2 && testType.endsWith("BG")) {// BG = Blood G
//				data = data2;
//			}
//		}
//		return data;
//	}
    //---------------------------- Get SQL Concession ID ----------------------------
    public String getSqlConcessionID(){
        String conID =null;
        try {
            String[] col_ml = new String[]{DB_SQLiteDB_Helper.T1F3_CONCESSIO_ID};

            Cursor c_ml = G_dgital_diabetes_db.query(DB_SQLiteDB_Helper.T1_ORDER, col_ml,
                    null, null, null, null, null);

            int m_Name = c_ml.getColumnIndex(DB_SQLiteDB_Helper.T1F3_CONCESSIO_ID);
//        int size = c_ml.getCount();
            for (c_ml.moveToFirst(); !c_ml.isAfterLast(); c_ml.moveToNext()) {
                conID = c_ml.getString(m_Name);
                break;
            }
        }catch(Exception ex){
            conID =null;
        }
        return conID;
    }

    public ArrayList<String> getSqlOrderItemList(){
//        String conID =null;
        ArrayList<String> orderItemList= new ArrayList<String>();
        try {
            String[] col_ml = new String[]{DB_SQLiteDB_Helper.T1F4_currentItemID};

            Cursor c_ml = G_dgital_diabetes_db.query(DB_SQLiteDB_Helper.T1_ORDER, col_ml, null, null, null, null, null);

            int m_itmID = c_ml.getColumnIndex(DB_SQLiteDB_Helper.T1F4_currentItemID);
//        int size = c_ml.getCount();
            for (c_ml.moveToFirst(); !c_ml.isAfterLast(); c_ml.moveToNext()) {
//                System.out.println("##################### "+" #####################");
                boolean b = orderItemList.add(c_ml.getString(m_itmID));
//                System.out.println("##################### "+c_ml.getString(m_itmID)+" #####################");
            }
        }catch(Exception ex){
            orderItemList =null;
        }
        return orderItemList;
    }

    public int getCountist(){
//        String conID =null;
        ArrayList<String> orderItemList= new ArrayList<String>();
        int m_itmID = 0;
        try {
            String[] col_ml = new String[]{DB_SQLiteDB_Helper.T1F4_currentItemID};
            Cursor c_ml = G_dgital_diabetes_db.rawQuery("SELECT SUM("+tblNmObj.T1F8_itemQantity+")countsmitm FROM " + tblNmObj.T1_ORDER + "", null);
            while (c_ml.moveToNext()) {
//                c_ml.getString(c_ml.getColumnIndex("countsmitm"));
                 String itmID =c_ml.getString(c_ml.getColumnIndex("countsmitm"));
                 m_itmID = Integer.parseInt(itmID);
                System.out.println("###"+" SQLite no of quantity: "+m_itmID);
            }
//          // int m_itmID = c_ml.getColumnIndex(DB_SQLiteDB_Helper.T1F4_currentItemID);
////        int size = c_ml.getCount();
//            for (c_ml.moveToFirst(); !c_ml.isAfterLast(); c_ml.moveToNext()) {
////                System.out.println("##################### "+" #####################");
//                boolean b = orderItemList.add(c_ml.getString(m_itmID));
////                System.out.println("##################### "+c_ml.getString(m_itmID)+" #####################");
//            }
        }catch(Exception ex){
            orderItemList =null;
        }
        return m_itmID;
    }


    public int deleteItemByID(String id) {
        int a=-1;
        try {
            open();
            a = G_dgital_diabetes_db.delete(DB_SQLiteDB_Helper.T1_ORDER, DB_SQLiteDB_Helper.T1F4_currentItemID + "=" + id, null);
//      int a = G_dgital_diabetes_db.execSQL("delete from "+DB_SQLiteDB_Helper.T1_ORDER+" where "+DB_SQLiteDB_Helper.T1F4_currentItemID+"='"+id+"'");
            close();
        }catch(Exception ex){
            a=-2;
        }
        return a;
    }


}