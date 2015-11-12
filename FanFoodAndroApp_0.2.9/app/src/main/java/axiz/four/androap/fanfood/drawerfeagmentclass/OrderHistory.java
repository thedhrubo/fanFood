package axiz.four.androap.fanfood.drawerfeagmentclass;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import axiz.four.androap.fanfood.R;
import axiz.four.androap.fanfood.coustom_adapter.Adapter3str1_orderHistoryPic;
import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;
import axiz.four.androap.fanfood.serversite.DownJsonToArray;


public class OrderHistory extends Fragment {
	
	private ActionBar actionBar;
	private static Emran_a emrnAObj;
	private Activity mActivity;
	private Context mContext;

	private TextView frgmnt_ordrhstri_txtTopTitle, frgmnt_sltvnu_txtBottomTitle;
	private ListView frgmnt_ordrhstri_listView1;

    private DataAbstractEvent dataObj2;
    private ProgressDialog pDialog_1;
    public static ArrayList<DataAbstractEvent> listData2;
//    Adapter3str2Pic venue_adapter;
    public static Adapter3str1_orderHistoryPic venue_adapter;
    private Button btnOrderHistryItemView,btnOrderHistryItemDelete;

	public OrderHistory() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);

		// Get vew
		View rootView = inflater.inflate(R.layout.fragment_orderhistory,
				container, false);
		// ****************************************************************
		// ******************** Initialize components *********************
		mContext = rootView.getContext();
		mActivity = getActivity();
		actionBar = (ActionBar)mActivity.getActionBar();
		emrnAObj = new Emran_a(mContext, mActivity);

		// *** SetTitle and SetColor ***
//		actionBar.setTitle(Html.fromHtml("<font color='#"+mContext.getResources().getColor(R.color.actionbar_textcolor)+"'><b>" +
//				mContext.getResources().getString(R.string.lavel_getstart)
//				+ " </b></font>"));
		
		Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
				"font/impact.ttf");

		frgmnt_ordrhstri_txtTopTitle = (TextView) rootView
				.findViewById(R.id.frgmnt_ordrhstri_txtTopTitle);
		frgmnt_sltvnu_txtBottomTitle = (TextView) rootView
				.findViewById(R.id.frgmnt_sltvnu_txtBottomTitle);

		frgmnt_ordrhstri_txtTopTitle.setTypeface(tf);
//		frgmnt_sltvnu_txtBottomTitle.setTypeface(tf);
		
		frgmnt_ordrhstri_listView1 = (ListView)rootView
				.findViewById(R.id.frgmnt_ordrhstri_listView1);
		//################################################################
        try{
        if(!emrnAObj.getUseValue("User_ID").equals("0")){
            frgmnt_sltvnu_txtBottomTitle.setText("You are checked in");
            }
        }catch(Exception ex){}
		// ****************************************************************
		// ******************** ListView *********************
//		final ArrayList<DataAbstractEvent> listData = new ArrayList<DataAbstractEvent>();
//		listData.add(new DataAbstractEvent("GN-380", "Concession 10", "Delivered",""));
//		listData.add(new DataAbstractEvent("AI-673", "Concession 1", "Complite","http://www.worldstadiums.com/stadium_pictures/north_america/united_states/michigan/ann_arbor_michigan1.jpg"));
//		listData.add(new DataAbstractEvent("BJ-173", "Concession 1", "Processing","http://media.pennlive.com/patriotnewssports/photo/11374920-large.jpg"));
//		listData.add(new DataAbstractEvent("IS-256", "Concession 4", "Waiting", "http://www.texasbob.com/stadium/simages/1022.jpg"));
		
//		Adapter3str1_orderHistoryPic venue_adapter = new Adapter3str1_orderHistoryPic(mContext, listData);
//		frgmnt_ordrhstri_listView1.setAdapter(venue_adapter);

        if (emrnAObj.isWifiOnline(mActivity)
                || emrnAObj.GetNetWorkState(mActivity)) {
            try {
//                WORK_MODE = 0;
                new GetContacts().execute();
            } catch (Exception ex) {
                emrnAObj.alartBox("System exception",
                        "System not work!\n" + ex.toString());
            }
        } else {
            emrnAObj.alartBox(emrnAObj.INTERNET_Required, emrnAObj.INTERNET_Need);
        }
		
		frgmnt_ordrhstri_listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// TODO Auto-generated method stub
//				emrnAObj.setUseValue("VENUENAME", listData.get(position).getTxt1());
                   //log.arg0.getChildAt(3);
                //Log.e("Click on",arg0.getChildAt(3).toString());
//                String OrderNumber = listData2.get(0).getTxt1();
//                String OrderTime = listData2.get(0).getTxt2();
//                String OrderMessage = listData2.get(0).getTxt1();
//                String OrderTotalPrice = listData2.get(0).getTxt1();
//                emrnAObj.setUseValue("OrderNumber",OrderNumber);
//                emrnAObj.setUseValue("OrderTime ",OrderTime);
//                emrnAObj.setUseValue("OrderMessage",OrderMessage);
//                emrnAObj.setUseValue("OrderTotalPrice",OrderTotalPrice);
//


                /*if(setOdreInfoDtl(position)){
                    startActivity(emrnAObj.Start_Activity(mContext, Receipt.class));
                }
                else{
                    emrnAObj.alartBox("","Order information corrupted");
                }*/

			}
		});

//		frgmnt_sltmnu_listView1.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//startActivity(emrnAObj.Start_Activity2(mContext, ConcessionS.class));
//			}
//		});
		return rootView;
	}

    public  static void setOdreInfoDtl(int position){
        Log.e("setOdreInfoDtl"," "+position);
        //String data=listData2.get(2).getTxt1();
       // Log.e("setOdreInfoDtl"," "+data);
      try {

          emrnAObj.setUseValue("OrderNumber", listData2.get(position).getTxt1());

          if(listData2.get(position).getTxt4().equals("0"))

            emrnAObj.setUseValue("OrderTime", "Waiting");
          else if(listData2.get(position).getTxt4().equals("1"))
              emrnAObj.setUseValue("OrderTime", "Processing");
          else if(listData2.get(position).getTxt4().equals("2"))
              emrnAObj.setUseValue("OrderTime", "Complite");
          else if(listData2.get(position).getTxt4().equals("3"))
              emrnAObj.setUseValue("OrderTime", "Delivered");
          else
              emrnAObj.setUseValue("OrderTime", "Delivered");

          emrnAObj.setUseValue("OrderMessage", listData2.get(position).getTxt1());
          emrnAObj.setUseValue("OrderTotalPrice", listData2.get(position).getTxt2());
          //return true;
      }catch(Exception ex){
          //return false;
      }
    }
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        boolean b = false;
        int n = 0;
        String serverMessage = "E";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

            try {
                mActivity.setProgressBarIndeterminateVisibility(true);
            } catch (Exception ex) {
            }
            pDialog_1 = new ProgressDialog(mContext);
            pDialog_1.setMessage("Please wait...");
            pDialog_1.setCancelable(false);
            pDialog_1.show();
        }

        // boolean resukt = false;

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                DownJsonToArray dataObj = new DownJsonToArray();
                dataObj2 = new DataAbstractEvent(emrnAObj.getUseValue("User_ID"));
                listData2 = dataObj.getOrderHistory(dataObj2);
                if (listData2 != null) {
                n = listData2.size();
//                serverMessage+="\n"+n+"\n";
                    serverMessage += dataObj.getServerResponce();
                  venue_adapter = new Adapter3str1_orderHistoryPic(mContext, listData2);
                } else {
                    serverMessage = dataObj.getServerResponce();
                }
            } catch (Exception ex) {
                serverMessage = ex.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog_1.isShowing())
                pDialog_1.dismiss();


            if(venue_adapter!=null){
                frgmnt_ordrhstri_listView1.setAdapter(venue_adapter);
            }else
                emrnAObj.alartBox("",serverMessage);
            // Toast.makeText(mContext,""+n+"\n"+serverMessage, Toast.LENGTH_LONG).show();

        }
    }
}
