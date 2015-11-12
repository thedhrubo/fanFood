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
import axiz.four.androap.fanfood.coustom_adapter.Adapter3str2Pic;
import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;
import axiz.four.androap.fanfood.serversite.DownJsonToArray;

/**
 * Created by emran on 4/9/15.
 */
public class Favorite extends Fragment {
    private ActionBar actionBar;
    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    private TextView concession_txtBottomTitle, concession_sltvnu_txtTopTitle ;
    private ListView concession_listView1;

    private int VENUENAME_ID=-1;
    private String User_ID="0";
    private ProgressDialog pDialog_1;
    Adapter3str2Pic venue_adapter;
    ArrayList<DataAbstractEvent> listData2;
    Communicator TransferCom;

    private DataAbstractEvent dataObj;

    public Favorite(){ }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        TransferCom = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);

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
        concession_sltvnu_txtTopTitle = (TextView) mActivity.findViewById(R.id.favorite_sltvnu_txtTopTitle );
        concession_sltvnu_txtTopTitle.setTypeface(tf);

        concession_txtBottomTitle = (TextView) mActivity.findViewById(R.id.favorite_txtBottomTitle);
        concession_txtBottomTitle.setTypeface(tf);
        concession_listView1 = (ListView) mActivity.findViewById(R.id.favorite_listView1);

        // ********************* Set Venue name on footer text********
        try {
            VENUENAME_ID = Integer.parseInt(emrnAObj.getUseValue("VENUENAME_ID"));
            User_ID = emrnAObj.getUseValue("User_ID");
            if(!User_ID.equals("0")){
                concession_txtBottomTitle.setText("You are checked in");
            }
           // concession_txtBottomTitle.setText(emrnAObj.getUseValue("VENUENAME"));
           // VENUENAME_ID = Integer.parseInt(emrnAObj.getUseValue("VENUENAME_ID"));
        } catch (Exception ex) {
           // concession_txtBottomTitle.setText("");
        }

        concession_sltvnu_txtTopTitle.setText("Your favourite items");
        // ################################################################

        // ****************************************************************
        // ******************** ListView *********************

//        final ArrayList<DataAbstractEvent> listData = new ArrayList<DataAbstractEvent>();
//        listData.add(new DataAbstractEvent("Concession 1", "Southwest Corner","http://www.worldstadiums.com/stadium_pictures/north_america/united_states/michigan/ann_arbor_michigan1.jpg"));
//        listData.add(new DataAbstractEvent("Concession 2", "South Corner","http://media.pennlive.com/patriotnewssports/photo/11374920-large.jpg"));
//        listData.add(new DataAbstractEvent("Concession 3", "Southwest Corner2", "http://www.texasbob.com/stadium/simages/1022.jpg"));
//        listData.add(new DataAbstractEvent("Concession 4", "Southwest Corner3", "http://www.worldstadiums.com/stadium_pictures/north_america/united_states/new_york/orchard_park_rich1.jpg"));
//        listData.add(new DataAbstractEvent("Concession 5", "Southwest Corner4", "http://www.worldstadiums.com/stadium_pictures/north_america/united_states/new_york/orchard_park_rich1.jpg"));
//        listData.add(new DataAbstractEvent("Concession 6", "Southwest Corner5", "http://www.worldstadiums.com/stadium_pictures/north_america/united_states/new_york/orchard_park_rich1.jpg"));
//        listData.add(new DataAbstractEvent("Concession 7", "Southwest Corner6", "http://www.worldstadiums.com/stadium_pictures/north_america/united_states/new_york/orchard_park_rich1.jpg"));
//        listData.add(new DataAbstractEvent("Concession 8", "Southwest Corner7", "http://www.worldstadiums.com/stadium_pictures/north_america/united_states/new_york/orchard_park_rich1.jpg"));
//        listData.add(new DataAbstractEvent("Concession 9", "Southwest Corner8", "http://www.worldstadiums.com/stadium_pictures/north_america/united_states/new_york/orchard_park_rich1.jpg"));
//        listData.add(new DataAbstractEvent("Concession 10", "Southwest Corner9", "http://www.worldstadiums.com/stadium_pictures/north_america/united_states/new_york/orchard_park_rich1.jpg"));
//        listData.add(new DataAbstractEvent("Concession 11", "Southwest Corner10", "http://www.worldstadiums.com/stadium_pictures/north_america/united_states/new_york/orchard_park_rich1.jpg"));
//        listData.add(new DataAbstractEvent("Concession 12", "Southwest Corner11", "http://www.worldstadiums.com/stadium_pictures/north_america/united_states/new_york/orchard_park_rich1.jpg"));
//        listData.add(new DataAbstractEvent("Concession 13", "Southwest Corner12", "http://www.worldstadiums.com/stadium_pictures/north_america/united_states/new_york/orchard_park_rich1.jpg"));
//        listData.add(new DataAbstractEvent("Concession 14", "Southwest Corner13", "http://www.worldstadiums.com/stadium_pictures/north_america/united_states/new_york/orchard_park_rich1.jpg"));

        if(!User_ID.equals("0")){
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
        }else{
            emrnAObj.alartBox("", emrnAObj.LOGIN_BEFOUR);
        }

        concession_listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

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
                DownJsonToArray dataObj2 = new DownJsonToArray();
                dataObj = new DataAbstractEvent(User_ID, ""+VENUENAME_ID);
                listData2 = dataObj2.getFavoriteByUser(dataObj);
                if(listData2!=null){
                n = listData2.size();
                serverMessage+=""+dataObj2.getServerResponce();
//                    Toast.makeText(mContext,""+n+"\n"+serverMessage, Toast.LENGTH_LONG).show();
                    venue_adapter= new Adapter3str2Pic(mContext, listData2);
                }else{
                    serverMessage =dataObj2.getServerResponce();
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
            emrnAObj.alartBox("",""+serverMessage+"\n"+n);
            if(venue_adapter!=null){
                concession_listView1.setAdapter(venue_adapter);
            }
        }
    }

}
