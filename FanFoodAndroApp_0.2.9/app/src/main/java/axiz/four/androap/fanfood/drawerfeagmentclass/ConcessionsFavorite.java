package axiz.four.androap.fanfood.drawerfeagmentclass;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import axiz.four.androap.fanfood.Communicator;
import axiz.four.androap.fanfood.R;
import axiz.four.androap.fanfood.coustom_adapter.AdapterConcession3str2Pic;
import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;
import axiz.four.androap.fanfood.serversite.DownJsonToArray;
import axiz.four.androap.fanfood.sqlitedb.DB_SQLiteDB_Adapter;

/**
 * Created by emran on 4/9/15.
 */
public class ConcessionsFavorite extends Fragment{
    private ActionBar actionBar;
    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    private TextView concession_txtBottomTitle, concession_sltvnu_txtTopTitle ;
    private ListView concession_listView1;


    private ProgressDialog pDialog_1;
    AdapterConcession3str2Pic venue_adapter;
    ArrayList<DataAbstractEvent> listData2;
    Communicator TransferCom;
    private int VENUENAME_ID=-1;
    private String User_ID="0";

    public ConcessionsFavorite(){

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        TransferCom = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_concession_favorite, container, false);

        mContext = rootView.getContext();
        mActivity = getActivity();
        actionBar = (ActionBar)mActivity.getActionBar();
        emrnAObj = new Emran_a(mContext, mActivity);

        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
                "font/impact.ttf");
        Typeface tf2 = Typeface.createFromAsset(mContext.getAssets(),
                "font/open_sans_regular.ttf");
        concession_sltvnu_txtTopTitle = (TextView) mActivity.findViewById(R.id.consFvrt_sltvnu_txtTopTitle );
        concession_sltvnu_txtTopTitle.setTypeface(tf);

                concession_txtBottomTitle = (TextView) mActivity.findViewById(R.id.consFvrt_txtBottomTitle);
        concession_txtBottomTitle.setTypeface(tf);
        concession_listView1 = (ListView) mActivity.findViewById(R.id.consFvrt_listView1);

        // ********************* Set Venue name on footer text********
        try {
//            if(!emrnAObj.getUseValue("User_ID").equals("0")){
//                concession_txtBottomTitle.setText("You are checked in");
//            }
            concession_txtBottomTitle
                    .setText(emrnAObj.getUseValue("VENUENAME"));
                VENUENAME_ID = Integer.parseInt(emrnAObj.getUseValue("VENUENAME_ID"));
            User_ID = emrnAObj.getUseValue("User_ID");
        } catch (Exception ex) {
            concession_txtBottomTitle.setText("");
        }

        concession_sltvnu_txtTopTitle.setText("SELECT CONCESSION");
        // ################################################################

        // ****************************************************************
        // ******************** ListView *********************

        if(VENUENAME_ID>0){
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
        }

        concession_listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long id) {
//				emrnAObj.setUseValue("VENUENAME", listData.get(position).getTxt1());
                emrnAObj.setUseValue("CONCESSIONnAME", listData2.get(position).getTxt1());
                emrnAObj.setUseValue("CONCESSIO_ID", listData2.get(position).getTxt5());
                TransferCom.frgSwitcher("ConcessionFavorite");

                try{
                    DB_SQLiteDB_Adapter obj = new DB_SQLiteDB_Adapter(mContext,mActivity);
                    obj.DeleteTable();
                }catch(Exception ex){
                }
//                emrnAObj.setUseValue("COUNTiTEM", "0");
//                startActivity(emrnAObj.Start_Activity(mContext, FoodCatalog.class));
                //startActivity(emrnAObj.Start_Activity(mContext, FoodCatagoryTabs.class));

            }
        });

        // ################################################################
    }

    // Data downloader
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
                DownJsonToArray DataObj = new DownJsonToArray();
                listData2 = DataObj.getConcessionFavorite(new DataAbstractEvent(""+User_ID,""+VENUENAME_ID));
                if(listData2!=null){
//                n = listData2.size();
//                  serverMessage=""+DataObj.getServerResponce();
//                    Toast.makeText(mContext,""+n+"\n"+serverMessage, Toast.LENGTH_LONG).show();
                    venue_adapter= new AdapterConcession3str2Pic(mContext, listData2);
                }else{
                    serverMessage =DataObj.getServerResponce();
                }
            }catch(Exception ex){
                serverMessage=ex.toString();
            }
            return null;
        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            Toast.makeText(mContext, values.length+"", Toast.LENGTH_SHORT).show();
//        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog_1.isShowing())
                pDialog_1.dismiss();
//            Toast.makeText(mContext,""+n+"\n"+serverMessage, Toast.LENGTH_LONG).show();
            if(venue_adapter!=null)
                concession_listView1.setAdapter(venue_adapter);
            else{
                emrnAObj.alartBox("",""+serverMessage);
            }
        }
    }

}
