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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

import axiz.four.androap.fanfood.CheckOut;
import axiz.four.androap.fanfood.Communicator;
import axiz.four.androap.fanfood.R;
import axiz.four.androap.fanfood.coustom_adapter.Adapter3str2Pic;
import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;
import axiz.four.androap.fanfood.serversite.DownJsonToArray;
import axiz.four.androap.fanfood.sqlitedb.DB_SQLiteDB_Adapter;

/**
 * Created by emran on 4/18/15.
 */
public class BeveragesItems extends Fragment {
    private ActionBar actionBar;
    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    private TextView concession_txtBottomTitle, allItems_sltvnu_txtTopTitle, allitem_txtBottomTitleconcession;
    public  TextView basket_count;
    private TextView itemDtls_txtVw_itemName, itemDtls_txtVw_itemPrice, itemDtls_txtVw_countView, itemDtls_txtVw_totalPrice;
    private EditText itemDtls_edtTxt_userCommants;
    private ListView concession_listView1;
    private LinearLayout allItems_listLayout, allItems_itmDtl;
    private ImageView itemDtls_imageView, itemBasket_imageView2;
    private ImageButton itemDtls_btn_dec, itemDtls_btn_inc;
    private Button itemDtls_addToCard;

    private DataAbstractEvent dataObj;

    private int CONCESSIO_ID=-1, VENUENAME_ID=-1;
    private float currentItemPrice = 0.0f;
    private float currentItemPriceSumation = 0.0f;
    public static int currentItemQuantity=0;
    private String currentItemID="0", ALL_ITEM_LIST="";

    private int currentItemPosition = -1;
    private ProgressDialog pDialog_1;
    Adapter3str2Pic venue_adapter;
    ArrayList<DataAbstractEvent> listData2;
    ArrayList<DataAbstractEvent> AddToBasCet;
    Communicator TransferCom;
    private View rootView;
    private String ItemCount = "0";
    private JSONArray JsoNAddToBasCet;

    public BeveragesItems(){

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        TransferCom = (Communicator) activity;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_beverage, container, false);
        this.rootView = rootView;
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

        allItems_listLayout= (LinearLayout) mActivity.findViewById(R.id.beverage_listLayout);
        allItems_itmDtl= (LinearLayout) mActivity.findViewById(R.id.beverage_itmDtl);

        allItems_sltvnu_txtTopTitle = (TextView) mActivity.findViewById(R.id.beverage_sltvnu_txtTopTitle);
        allItems_sltvnu_txtTopTitle.setTypeface(tf);
        allitem_txtBottomTitleconcession = (TextView) mActivity.findViewById(R.id.allitem_txtBottomTitleconcession);
        allitem_txtBottomTitleconcession.setTypeface(tf);
        basket_count = (TextView) mActivity.findViewById(R.id.beverage_basket_count );
        concession_txtBottomTitle = (TextView) mActivity.findViewById(R.id.beverage_txtBottomTitle);
        concession_txtBottomTitle.setTypeface(tf);
        concession_listView1 = (ListView) mActivity.findViewById(R.id.beverag_listView1);
        itemBasket_imageView2 = (ImageView) mActivity.findViewById(R.id.beverage_itemBasket_imageView2);

        basket_count.setText(""+currentItemQuantity);

        // for Item de
        itemDtls_edtTxt_userCommants =(EditText) mActivity.findViewById(R.id.beverage_itemDtls_edtTxt_userCommants);
        itemDtls_txtVw_itemName =(TextView) mActivity.findViewById(R.id.beverage_itemDtls_txtVw_itemName);
        itemDtls_txtVw_itemName.setTypeface(tf);
        itemDtls_txtVw_itemPrice =(TextView) mActivity.findViewById(R.id.beverage_itemDtls_txtVw_itemPrice);
        itemDtls_txtVw_countView=(TextView) mActivity.findViewById(R.id.beverage_itemDtls_txtVw_countView);
        itemDtls_txtVw_totalPrice=(TextView) mActivity.findViewById(R.id.beverage_itemDtls_txtVw_totalPrice);
        itemDtls_btn_dec = (ImageButton) mActivity.findViewById(R.id.beverage_itemDtls_btn_dec );
        itemDtls_btn_inc = (ImageButton) mActivity.findViewById(R.id.beverage_itemDtls_btn_inc);
        itemDtls_addToCard= (Button) mActivity.findViewById(R.id.itemDtls_addToCard);

        itemDtls_addToCard.setTypeface(tf);

        AddToBasCet = new ArrayList<DataAbstractEvent>();
        JsoNAddToBasCet = new JSONArray();

        // ********************* Set Venue name on footer text *********************
        try {
            concession_txtBottomTitle
                    .setText(emrnAObj.getUseValue("VENUENAME"));
            allitem_txtBottomTitleconcession.setText(emrnAObj.getUseValue("CONCESSIONnAME"));
            VENUENAME_ID = Integer.parseInt(emrnAObj.getUseValue("VENUENAME_ID"));
            CONCESSIO_ID = Integer.parseInt(emrnAObj.getUseValue("CONCESSIO_ID"));
            emrnAObj.setUseValue("IsShow", "YES");
//            basket_count.setText(emrnAObj.getUseValue("BsktItems"));
            String CATEGORY_ID ="3";
            dataObj = new DataAbstractEvent(VENUENAME_ID+"", CONCESSIO_ID+"", CATEGORY_ID );
            ALL_ITEM_LIST = emrnAObj.getUseValue("ALL_ITEM_LIST");
        } catch (Exception ex) {
            concession_txtBottomTitle.setText("");
        }
        // ################################################################

        allItems_sltvnu_txtTopTitle.setText("SELECT MENU");

        // ****************************************************************
        // ******************** ListView *********************
        LoadItems();


        this.rootView.setFocusableInTouchMode(true);
        this.rootView.requestFocus();
        this.rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if(allItems_itmDtl.getVisibility()==LinearLayout.VISIBLE) {
                        showItemList();
                        emrnAObj.setUseValue("IsShow", "YES");
                        return false;
                    }
                    else if(allItems_itmDtl.getVisibility()!=LinearLayout.VISIBLE) {{
                        if(emrnAObj.getUseValue("IsShow").equals("YES")){
                           emrnAObj.setUseValue("IsShow", "NO");
                        }
                        else if(emrnAObj.getUseValue("IsShow").equals("NO")){
//                            try{
//                            DB_SQLiteDB_Adapter obj = new DB_SQLiteDB_Adapter(mContext,mActivity);
//                                obj.DeleteTable();
//                               Toast.makeText(mContext,"Order canceled", Toast.LENGTH_LONG).show();
//                            }catch(Exception ex){
//                                emrnAObj.alartBox("","zsd");
//                                Toast.makeText(mContext,"Order cancel exception:\n"+ex.toString(), Toast.LENGTH_SHORT).show();
//                            }
                            return false;
                        }
//                        emrnAObj.alartBox("", "sss");
                    }
                    }
                    return true;
                }
                return true;
            }
        });



//        concession_listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View v, int position,
//                                    long id) {
//
//                Toast.makeText(mContext,"Name: "+listData2.get(position).getTxt1()+"\nMessage: "+listData2.get(position).getTxt2()
//                        +"\nPrice: "+listData2.get(position).getTxt3(),Toast.LENGTH_SHORT).show();
//
//            }
//        });

//        concession_listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                for (int j = 0; j < adapterView.getChildCount(); j++)
//                    adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
//
//                // change the background color of the selected element
//                view.setBackgroundColor(Color.LTGRAY);
//            }});

        concession_listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
//                    for (int ctr = 0; ctr <= adapterView.getChildCount(); ctr++) {
//                        if (i == ctr) {
//                            concession_listView1.getChildAt(ctr).setBackgroundColor(Color.WHITE);
//
//                        } else {
//                           // concession_listView1.getChildAt(ctr).setBackgroundColor(Color.WHITE);
//                        }
//                    }
                    openItemDtls(i);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Log.v("Selected item", concession_listView1.getItemAtPosition(i));
            }
        });

//        try{
//            String whatNowShow = emrnAObj.getUseValue("ItemShow");
//            if(whatNowShow==null || whatNowShow.equals("YES")){
//                emrnAObj.setUseValue("ItemShow","NO");
//                showItemList();
//            }
//        }finally {
//            showItemList();
//        }
        showItemList();

        // ********** Button Activity
        itemDtls_addToCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SetItemOnBasket();
            }
        });

        itemDtls_btn_dec.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int currentCount = emrnAObj.DecrementStringDigite(itemDtls_txtVw_countView.getText().toString());
                if(currentCount>=1){
                    ItemCount=currentCount+"";
                    currentItemPriceSumation-=currentItemPrice;
                    itemDtls_txtVw_countView.setText(currentCount+"");
                    itemDtls_txtVw_totalPrice.setText(currentItemPriceSumation+"");
                }else{
                    itemDtls_txtVw_countView.setText("1");
                }

            }
        });

        itemDtls_btn_inc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int currentCount = emrnAObj.IncrementStringDigite(itemDtls_txtVw_countView.getText().toString());
                if(currentCount<100){
                    ItemCount=currentCount+"";
                    currentItemPriceSumation+=currentItemPrice;
                    itemDtls_txtVw_countView.setText(currentCount+"");
                    itemDtls_txtVw_totalPrice.setText(currentItemPriceSumation+"");
                }else{
                    itemDtls_txtVw_countView.setText("1");
                }

            }
        });

        itemBasket_imageView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                DB_SQLiteDB_Adapter sqlObj = new DB_SQLiteDB_Adapter(mContext, mActivity);
                ArrayList<String> orderItemList = sqlObj.getSqlOrderItemList();
//                int sz = orderItemList.size();
                try {

                    if(orderItemList.size()>0){
//                        emrnAObj.alartBox("", "Current Items in this order is: " + sz);
                        startActivity(emrnAObj.Start_Activity(mContext, CheckOut.class));
//                        Intent intent = new Intent(mContext, CheckOut.class);
//
//                        Bundle b = new Bundle();
//                        b.putSerializable("myObject", AddToBasCet);
//                        intent.putExtras(b);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                    }
                }catch(Exception ex) {
                    emrnAObj.alartBox("", "Current Items in this order is: 0\n"+ex.toString());
                }
                return false;
            }
        });
        // ################################################################
    }// emd

    private void LoadItems() {
        if(CONCESSIO_ID>0){
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
    }

//    protected void SetItemOnBasket() {
//        int countFoood = 0;
//        try{
//            countFoood = Integer.parseInt(ItemCount);
//            ++countFoood;
//            emrnAObj.setUseValue("COUNTiTEM", countFoood+"");
//        }catch(Exception ex){
//
//        }
//    }

    protected void SetItemOnBasket() {
//        AddToBasCet
        try{
            String itemName = itemDtls_txtVw_itemName.getText().toString();
//            String itemPriceString = itemDtls_txtVw_itemName.getText().toString();
            String currentItemQuantity = itemDtls_txtVw_countView.getText().toString();
            String isCancel="0";
            String userComments= "";
            String date= "";
            String T1F13_order_time= "";

            DataAbstractEvent getItemToBasket = new DataAbstractEvent(
                    VENUENAME_ID+"", CONCESSIO_ID+"", currentItemID,
                    itemName, "$"+currentItemPrice, ""+currentItemPrice,
                    currentItemQuantity, ""+currentItemPriceSumation, isCancel,
                    userComments,date,T1F13_order_time);

            AddToBasCet.add(getItemToBasket);

            DB_SQLiteDB_Adapter dbAda = new DB_SQLiteDB_Adapter(mContext, mActivity);
            long a = dbAda.Insert_TB1_Order(getItemToBasket);

//            if(a>0){
//                emrnAObj.alartBox("","Insert Successfull");
//            }else{
//                emrnAObj.alartBox("","Insert unsuccessfull");
//            }

/*
        JSONObject obj = new JSONObject();
        obj.put("VENUENAME_ID", VENUENAME_ID);
        obj.put("CONCESSIO_ID",CONCESSIO_ID+"");
        obj.put("currentItemID",currentItemID+"");
        obj.put("ITEMNAME",itemDtls_txtVw_itemName.getText().toString());
        obj.put("ITEMPRICE",itemDtls_txtVw_itemPrice.getText().toString());
        obj.put("ITEMCURRENTPRICE",currentItemPrice+"");
        obj.put("ITEMQUAN",currentItemQuantity+"");
        obj.put("ITEMSUMPRICE",currentItemPriceSumation+"");
*/
        Toast.makeText(mContext,"This item has been added to basket."+AddToBasCet.size(), Toast.LENGTH_LONG).show();
//            allItems_listLayout.setVisibility(LinearLayout.VISIBLE);
//            allItems_itmDtl.setVisibility(LinearLayout.GONE);
            showItemList();

            if(AddToBasCet!=null){
                DB_SQLiteDB_Adapter sqlObj = new DB_SQLiteDB_Adapter(mContext, mActivity);
//                ArrayList<String> orderItemList = sqlObj.getSqlOrderItemList();
                basket_count.setText(sqlObj.getCountist()+"");

//                int cItm = Integer.parseInt(emrnAObj.getUseValue("BsktItems"));
//                basket_count.setText((cItm+AddToBasCet.size())+"");
                LoadItems();
            }
            else
                basket_count.setText("0");
        }catch (Exception ex){
            emrnAObj.alartBox("","Item add unsuccess:\n"+ex.toString());
        }

    }


    private void openItemDtls(int i) {
        currentItemPosition = i;
        try {
            allItems_listLayout.setVisibility(LinearLayout.GONE);
            allItems_itmDtl.setVisibility(LinearLayout.VISIBLE);
            emrnAObj.setUseValue("ItemShow", "YES");
            DataAbstractEvent itemData = listData2.get(i);
            itemDtls_txtVw_itemName.setText(itemData.getTxt1());
            itemDtls_txtVw_itemPrice.setText(itemData.getTxt3());
            currentItemPrice = Float.parseFloat(itemData.getTxt4());
            currentItemID = itemData.getTxt12();
            currentItemPriceSumation = currentItemPrice;
            itemDtls_txtVw_totalPrice.setText(currentItemPrice+"");
        } finally {

        }

    }
    private void showItemList(){
        allItems_listLayout.setVisibility(LinearLayout.VISIBLE);
        allItems_itmDtl.setVisibility(LinearLayout.GONE);
        emrnAObj.setUseValue("ItemShow","NO");
        itemDtls_txtVw_countView.setText("1");
        itemDtls_txtVw_totalPrice.setText("0.0");
        currentItemPriceSumation=0.0f;
        currentItemQuantity=1;
        currentItemPrice=0;
    }

//    // PopUp Item details
//    private void Item_details(){
//        LayoutInflater layoutInflater = (LayoutInflater)mActivity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View popupView = layoutInflater.inflate(R.layout.inner_layout_itemdtl, null);
//        final PopupWindow popupWindow = new PopupWindow(
//                popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        popupWindow.showAsDropDown(concession_listView1, 50, -30);
//    }
    // ################################################################

    // Data downloader
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        boolean b = false;
        int n = 0, bItemList=0;
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
                DownJsonToArray DdataObj = new DownJsonToArray();
                DB_SQLiteDB_Adapter sqlObj = new DB_SQLiteDB_Adapter(mContext, mActivity);
                ArrayList<String> orderItemList = sqlObj.getSqlOrderItemList();
                if(orderItemList!=null){
                    bItemList = sqlObj.getCountist();
                    emrnAObj.setUseValue("BsktItems",""+bItemList);
                }
//              listData2 = DdataObj.getAllItems(dataObj);
                listData2 =  DdataObj.getCatagoryItem(ALL_ITEM_LIST, 3+"", orderItemList); // 1 = HOT FOOD, 2 = SNACKS, 3 = DRINKS; 4 = BEVERAGES
                if(listData2!=null){
//                    n = listData2.size();
                    // serverMessage+=n+"\nA"+DdataObj.getServerResponce()+" "+listData2.get(0).getTxt1();
//                    Toast.makeText(mContext,""+n+"\n"+serverMessage, Toast.LENGTH_LONG).show();
                    venue_adapter= new Adapter3str2Pic(mContext, listData2);
                }else{
                    serverMessage =DdataObj.getServerResponce();
                }
            }catch(Exception ex){
                serverMessage=ex.toString();
            }
            return null;
        }

//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            Toast.makeText(mContext, values.length+"", Toast.LENGTH_SHORT).show();
//        }

        public void myOnKeyDown(int key_code){
            showItemList();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog_1.isShowing())
                pDialog_1.dismiss();
//            Toast.makeText(mContext,""+n+"\n"+serverMessage, Toast.LENGTH_LONG).show();
            if(venue_adapter!=null){
                concession_listView1.setAdapter(venue_adapter);
            }
            else{
                emrnAObj.alartBox("",""+serverMessage+"");
                basket_count.setText(""+bItemList);
                //emrnAObj.alartBox("",""+serverMessage+" venue_adapter is not null");
                //Toast.makeText(mContext,"Under working",Toast.LENGTH_SHORT).show();
            }
           // Toast.makeText(mContext,"Beverage DataSize, "+listData2.size(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // emrnAObj.alartBox("","onResume");
        try{
            DB_SQLiteDB_Adapter sqlObj = new DB_SQLiteDB_Adapter(mContext, mActivity);
            basket_count.setText(sqlObj.getCountist()+"");
        }catch (Exception ex){
            //   emrnAObj.alartBox("onResume", ex.toString());
        }
    }

}