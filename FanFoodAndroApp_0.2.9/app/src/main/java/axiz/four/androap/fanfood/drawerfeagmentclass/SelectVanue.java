package axiz.four.androap.fanfood.drawerfeagmentclass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import axiz.four.androap.fanfood.Communicator;
import axiz.four.androap.fanfood.R;
import axiz.four.androap.fanfood.coustom_adapter.Adapter3str2Pic;
import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;
import axiz.four.androap.fanfood.serversite.DownJsonToArray;

/**
 * Created by emran on 4/8/15.
 */
public class SelectVanue extends Fragment {

    private ActionBar actionBar;
    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    private TextView frgmnt_sltvnu_txtTopTitle, frgmnt_sltvnu_txtBottomTitle;
    private ListView frgmnt_sltmnu_listView1;
    Adapter3str2Pic venue_adapter;
    private ProgressDialog pDialog_1;
    ArrayList<DataAbstractEvent> listData2;
    Communicator TransferCom;

    public SelectVanue() {
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        TransferCom = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Get vew
        View rootView = inflater.inflate(R.layout.fragment_selectvanue,
                container, false);
        // ****************************************************************
        // ******************** Initialize components *********************
        mContext = rootView.getContext();
        mActivity = getActivity();
        actionBar = (android.app.ActionBar)mActivity.getActionBar();
        mActivity.setProgressBarIndeterminateVisibility(true);

//        actionBar = getActivity().getActionBar();
        emrnAObj = new Emran_a(mContext, mActivity);
        // *** SetTitle and SetColor ***
//        actionBar.setTitle("xdfvzdf");
//        actionBar.setTitle(Html.fromHtml("<font color='#" + mContext.getResources().getColor(R.color.actionbar_textcolor) + "'><b>" +
//                mContext.getResources().getString(R.string.lavel_getstart)
//                + " </b></font>"));

        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
                "font/impact.ttf");

        frgmnt_sltvnu_txtTopTitle = (TextView) rootView
                .findViewById(R.id.frgmnt_sltvnu_txtTopTitle);
        frgmnt_sltvnu_txtBottomTitle = (TextView) rootView
                .findViewById(R.id.frgmnt_sltvnu_txtBottomTitle);

        frgmnt_sltvnu_txtTopTitle.setTypeface(tf);
        frgmnt_sltvnu_txtTopTitle.setText("SELECT VENUE");
        frgmnt_sltvnu_txtBottomTitle.setTypeface(tf);

        frgmnt_sltmnu_listView1 = (ListView)rootView
                .findViewById(R.id.frgmnt_sltmnu_listView1);
        //################################################################

        // ****************************************************************
        // ******************** ListView *********************

//        final ArrayList<DataAbstractEvent> listData = new ArrayList<DataAbstractEvent>();
//        listData.add(new DataAbstractEvent("Prairie High School Stadium", "Cedar Rapid", "IA. (1.0 m)","http://www.worldstadiums.com/stadium_pictures/north_america/united_states/michigan/ann_arbor_michigan1.jpg"));
//        listData.add(new DataAbstractEvent("Cedar Rapid Ice Arena", "Cedar Rapid", "IA (6.1 M)","http://media.pennlive.com/patriotnewssports/photo/11374920-large.jpg"));
//        listData.add(new DataAbstractEvent("Xavier High School Stadium", "Cedar Rapid", "IA (13.7 m)", "http://www.texasbob.com/stadium/simages/1022.jpg"));
//        listData.add(new DataAbstractEvent
//                ("UNI-Dome", "Cedar Falls", "IA (63.8 m)", "http://www.worldstadiums.com/stadium_pictures/north_america/united_states/new_york/orchard_park_rich1.jpg"));

//        final ArrayList<DataAbstractEvent> listData2 = dataObj.getStadumList();

//        Adapter3str2Pic venue_adapter = new Adapter3str2Pic(mContext, listData2);
        try {

            if(!emrnAObj.getUseValue("User_ID").equals("0")){
                frgmnt_sltvnu_txtBottomTitle.setText("You are checked in");
            }
            // concession_txtBottomTitle.setText(emrnAObj.getUseValue("VENUENAME"));
            // VENUENAME_ID = Integer.parseInt(emrnAObj.getUseValue("VENUENAME_ID"));
        } catch (Exception ex) {
//            frgmnt_sltvnu_txtBottomTitle.setText("");
        }
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

        frgmnt_sltmnu_listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub
                emrnAObj.setUseValue("VENUENAME", listData2.get(position).getTxt1());
                emrnAObj.setUseValue("VENUENAME_ID", listData2.get(position).getTxt9());
//                startActivity(emrnAObj.Start_Activity(mContext, ConcessionS.class));
                try{
                    TransferCom.frgSwitcher("SelectVenue");
                }catch(Exception ex){
                    emrnAObj.alartBox("",ex.toString());
                }
            }
        });
        return rootView;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }

    //

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
                listData2 = dataObj.getStadumList();
                if(listData2!=null){
//                n = listData2.size();
//                serverMessage+="\nA";
                venue_adapter= new Adapter3str2Pic(mContext, listData2);
                }else{
                    serverMessage =dataObj.getServerResponce();
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
            if(venue_adapter!=null)
                frgmnt_sltmnu_listView1.setAdapter(venue_adapter);
            else
            Toast.makeText(mContext, serverMessage, Toast.LENGTH_SHORT).show();

        }
    }
}
