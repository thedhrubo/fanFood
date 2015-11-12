package axiz.four.androap.fanfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import axiz.four.androap.fanfood.coustom_adapter.AdapterCheckOut;
import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;
import axiz.four.androap.fanfood.serversite.DownJsonToArray;
import axiz.four.androap.fanfood.sqlitedb.DB_SQLiteDB_Adapter;
import axiz.four.androap.fanfood.utilities.DeleveryActivity;

/**
 * Created by emran on 5/2/15.
 */
public class CheckOut extends Activity {
    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;
    private String message="";
    String method;
    private TextView checkout_txtTopTitle, checkout_basket_count,
            checkout_txtBottomTitle,tvConVen,tvTotal;
    public static TextView checkout_totalPrice,tvConvenience_fee;
    private ListView checkout_listView1;
    private Button checkout_dicCode, checkout_submit,
            checkout_btn_conShop, checkout_clrAll, checkout_btn_conShop2,chooseOption;
    private float extraFee;

    private String CurrentMessage = "", CurrentAlartTitle = "", discountCode = "", User_ID = "0", testJson = "";
    private DataAbstractEvent dataObj;
    private ProgressDialog pDialog_1;
    private ArrayList<DataAbstractEvent> listData2;
    private ArrayList<DataAbstractEvent> AddToBasCet;
    private AdapterCheckOut venue_adapter;
    public static float tatalPrice = 0.0f;
    private JSONObject JsonOrderObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        mActivity = (Activity) this;
        mContext = (Context) this;
        emrnAObj = new Emran_a(mContext, mActivity);

        InitializeComponent();
        User_ID = emrnAObj.getUseValue("User_ID");
        checkout_txtBottomTitle.setText(emrnAObj.getUseValue("CONCESSIONnAME"));

        loadData();
        setListAdapter();

        checkout_dicCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDiscountCode();
            }
        });

        checkout_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetValue();
                String User_ID = emrnAObj.getUseValue("User_ID");
                if(User_ID.equals("0")){// 0 is as a guast user
                    
                    emrnAObj.setUseValue("FromWher","CheckOutPayMent");
                    Toast.makeText(mContext,"Please login again",Toast.LENGTH_SHORT).show();
                    startActivity(emrnAObj.Start_Activity(mContext, MainLoginActivity.class));

                 }else{

                    // goto submit order directly
                    gotoSubmitOrder();
                    //reCheckPassAndOrder();

                }
            }


        });

        checkout_clrAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
                ad.setTitle("");
                ad.setMessage("Do you want to remove all items?");
                // set Button: PositiveButton
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        try {
                            DB_SQLiteDB_Adapter obj = new DB_SQLiteDB_Adapter(mContext, mActivity);
                            obj.DeleteTable();
                            checkout_basket_count.setText("0");
                            checkout_totalPrice.setText("0");
                            tatalPrice = 0.0f;
                            checkout_submit.setEnabled(false);
                            checkout_clrAll.setEnabled(false);

//                    loadData();
//                    AddToBasCet = null;
                            try {
                                checkout_listView1.setAdapter(null);
                            } catch (Exception ex) {

                            }
//                    setListAdapter();
                        } catch (Exception ex) {
                            emrnAObj.alartBox("", "Items not delete");
                        }
                    }
                });

                // set Button: setNegativeButton
                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        // Toast.makeText(MainActivity.this, msg+"\nNot login",
                        // Toast.LENGTH_SHORT).show();
                    }
                });
                ad.show();

            }
        });

//
    }// end

    private void gotoSubmitOrder() {
        if (dataObj != null) {
            if (!User_ID.equals("0")) {
                if (emrnAObj.isWifiOnline(mActivity)
                        || emrnAObj.GetNetWorkState(mActivity)) {
                    try {
                        //WORK_MODE = 0;
                        new GetContacts().execute();
                    } catch (Exception ex) {
                        emrnAObj.alartBox("System exception",
                                "System not work!\n" + ex.toString());
                    }
                } else {
                    emrnAObj.alartBox(emrnAObj.INTERNET_Required, emrnAObj.INTERNET_Need);
                }
            } else {
                emrnAObj.alartBox("", "Please login");
            }

        } else {
            emrnAObj.alartBox("", "Your information not complete, please try again ");
        }
    }

    private void GetValue() {
        if (paymentInformation()) {
            try {
                String cardNo = emrnAObj.getUseValue("CARD_NUMBER");
                String expDat = emrnAObj.getUseValue("EXPIRY_DATE");
                String cVV = emrnAObj.getUseValue("CARD_CVV");

                if ((cardNo != null && expDat != null && cVV!=null) && (cardNo.length()>0 && expDat.length()>0 && cVV.length()>0)) {
                    dataObj = new DataAbstractEvent(JsonOrderObj.toString());
                    System.out.println("### OrderJSON: " + JsonOrderObj.toString());
                } else {
                    System.out.println("### Pament exception: " + cardNo + ", " + expDat+", "+cVV);
                }
            } catch (Exception ex) {
                System.out.println("### Pament exception: " + ex.toString());
                dataObj = null;
            }
        }
//        emrnAObj.alartBox("",""+JsonOrderObj.toString());
        //return b;
    }

    private boolean paymentInformation() {
        String cardNo = emrnAObj.getUseValue("CARD_NUMBER");
        String expDat = emrnAObj.getUseValue("EXPIRY_DATE");
        String cVV = emrnAObj.getUseValue("CARD_CVV");

        if (!IsFirst()) {
            if ((cardNo == null || expDat == null || cVV == null) || (cardNo.length()<=0 || expDat.length()<=0 || cVV.length()<=0)) {
                Toast.makeText(mContext, "Require payment information", Toast.LENGTH_LONG).show();
                finish();
                startActivity(emrnAObj.Start_Activity(mContext, Payment.class));// MainActivity use as Sign in
                return false;
            } else {
                return true;
            }
        } else {
            Toast.makeText(mContext, "Require registration or login", Toast.LENGTH_LONG).show();
            finish();
            startActivity(emrnAObj.Start_Activity(mContext, MainLoginActivity.class));// MainActivity use as Sign in
            return false;
        }
    }

    /*
* This Function use for check, this app is first time run?
* */
    private boolean IsFirst() {
        /* 0 for get is registered. If registered then return false, meaning is Not first. if return true meaning need to registration*/
        return emrnAObj.isFirst(0);
//		return false;
    }

    private void setListAdapter() {
        if (AddToBasCet != null && AddToBasCet.size() > 0) {
            venue_adapter = new AdapterCheckOut(mContext, AddToBasCet);
            checkout_listView1.setAdapter(venue_adapter);
            checkout_submit.setEnabled(true);
            checkout_clrAll.setEnabled(true);
        }

    }

    private void loadData() {
        try {
            JSONArray jsonArray = new JSONArray();
            DB_SQLiteDB_Adapter dbAda = new DB_SQLiteDB_Adapter(mContext, mActivity);
            AddToBasCet = dbAda.GetAll_Data(1);
            //JsonOrderObj.put("USER_MAIL","emran@4axiz.com");
            JsonOrderObj.put("USER_ID", User_ID);
            JsonOrderObj.put("CONCESSION_ID", emrnAObj.getUseValue("CONCESSIO_ID"));
            JsonOrderObj.put("USER_MAIL", emrnAObj.getUseValue("User_Email"));
//            checkout_basket_count.setText(AddToBasCet.size()+"");
            int numberOfItem = 0;

            for (int i = 0; i < AddToBasCet.size(); i++) {
//                tatalPrice+=Float.parseFloat(AddToBasCet.get(i).getTxt8());
                tatalPrice += Float.parseFloat(AddToBasCet.get(i).getTxt13());
                JSONObject orderItem = new JSONObject();
                orderItem.put("VENUE_ID", AddToBasCet.get(i).getTxt1());
//                orderItem.put("CONCESSION_ID", AddToBasCet.get(i).getTxt2());
                orderItem.put("ITEM_ID", AddToBasCet.get(i).getTxt3());//currentItemID
                orderItem.put("ITEM_NAME", AddToBasCet.get(i).getTxt4());//T1F5_itemName
//                orderItem.put("ITEM_PRICE", AddToBasCet.get(i).getTxt6());//T1F7_itemPrice
                orderItem.put("ITEM_PRICE", AddToBasCet.get(i).getTxt13());//T1F7_itemPrice
                orderItem.put("ITEM_QUANTITY", AddToBasCet.get(i).getTxt7());//T1F8_itemQantity
                orderItem.put("TOTAL_PRICE", AddToBasCet.get(i).getTxt8());//T1F9_totalPrice
                orderItem.put("USER_COMMENTS", AddToBasCet.get(i).getTxt10());//T1F11_userComments

                try {
                    numberOfItem += Integer.parseInt(AddToBasCet.get(i).getTxt14());
                } catch (NumberFormatException ex) {
                }

                jsonArray.put(orderItem);
            }
//            checkout_basket_count.setText(numberOfItem);
            extraFee=Float.parseFloat(tvConvenience_fee.getText().toString());
            //extraFee=1;
            tatalPrice=tatalPrice+extraFee;
            JsonOrderObj.put("ORDER_TOTAL", "" + tatalPrice);

            JsonOrderObj.put("ORDER_ARRAY", jsonArray);

            checkout_totalPrice.setText("" + tatalPrice);
            //testJson = JsonOrderObj.toString();
//            System.out.println("#####################################################################\n"+testJson);
//            emrnAObj.alartBox("JSONTest", testJson);
            // emrnAObj.alartBox("","Size: "+AddToBasCet.size()+"\nTotal price: "+tatalPrice);
        } catch (Exception ex) {
            emrnAObj.alartBox("", "" + ex.toString());
        }
    }

    protected String getDiscountCode() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input
        // as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        input.setHint("DISCOUNT CODE");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                discountCode = input.getText().toString();
                try {
                    if (emrnAObj.isName(discountCode))
                        JsonOrderObj.put("DISCOUNT_CODE", discountCode);
                    else {
                        emrnAObj.alartBox("", "Please input discount code");
                    }
                } catch (Exception ex) {

                }
            }
        });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.show();
        return null;
    }


    // This function is unused
    protected String reCheckPassAndOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input
        // as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setHint("Password");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pas = input.getText().toString();
                try {
                    if (emrnAObj.getUseValue("RE_CHK_PASS").toString().equals(pas)){
                        gotoSubmitOrder();
                    }
                    else {
                        //emrnAObj.alartBox("", "Wrong password");
                        Toast.makeText(mContext, "Incorrect password", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {

                }
            }
        });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.show();
        return null;
    }


    protected String getLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        // Set up the input
        final EditText inputEmail = new EditText(this);
        final EditText inputPasswd = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input
        // as a password, and will mask the text
        inputEmail.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        inputEmail.setHint("Email ID");
        builder.setView(inputEmail);

        inputPasswd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputPasswd.setHint("Password");
        builder.setView(inputPasswd);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                discountCode = inputPasswd.getText().toString();
//                try {
//                    if(emrnAObj.isName(discountCode))
//                        JsonOrderObj.put("DISCOUNT_CODE", discountCode);
//                    else {
//                        emrnAObj.alartBox("","Please input discount code");
//                    }
//                }catch (Exception ex){
//
//                }
            }
        });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.show();
        return null;
    }


    private void InitializeComponent() {
        emrnAObj = new Emran_a(mContext, mActivity);
        JsonOrderObj = new JSONObject();
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "font/impact.ttf");
        Typeface tf2 = Typeface.createFromAsset(mContext.getAssets(), "font/open_sans_regular.ttf");

        checkout_txtTopTitle = (TextView) findViewById(R.id.checkout_txtTopTitle);
        checkout_basket_count = (TextView) findViewById(R.id.checkout_basket_count);
        checkout_totalPrice = (TextView) findViewById(R.id.checkout_totalPrice);
        checkout_txtBottomTitle = (TextView) findViewById(R.id.checkout_txtBottomTitle);
        tvConVen = (TextView) findViewById(R.id.tvConVen);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvConvenience_fee = (TextView) findViewById(R.id.tvConvenience_fee);

        //tvConVen.setTypeface(tf);
        //tvTotal.setTypeface(tf);
        checkout_txtBottomTitle.setTypeface(tf);
        checkout_txtBottomTitle.setTypeface(tf);
        checkout_txtTopTitle.setText("CHECKOUT");

        checkout_btn_conShop = (Button) findViewById(R.id.checkout_btn_conShop2);
        chooseOption = (Button) findViewById(R.id.btnChooseOption);
        checkout_dicCode = (Button) findViewById(R.id.checkout_dicCode);
        checkout_submit = (Button) findViewById(R.id.checkout_submit);
        checkout_clrAll = (Button) findViewById(R.id.checkout_clrAll);

        checkout_dicCode.setTypeface(tf);
        checkout_submit.setTypeface(tf);
        checkout_clrAll.setTypeface(tf);

        checkout_btn_conShop.setTypeface(tf);
        chooseOption.setTypeface(tf);
        checkout_btn_conShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tatalPrice = 0.0f;
                finish();
            }
        });
        //checkout_basket_count.setTypeface(tf2);

        checkout_listView1 = (ListView) findViewById(R.id.checkout_listView1);

    }

    private class GetContacts extends AsyncTask<Void, Integer, Void> {
        boolean isOk = false;
        int n = 0;
        String serverMessage = "";

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
                String cardName = emrnAObj.getUseValue("CARD_NAME");
                String cardNo = emrnAObj.getUseValue("CARD_NUMBER");
                String expDat = emrnAObj.getUseValue("EXPIRY_DATE");
                String cVV = emrnAObj.getUseValue("CARD_CVV");
                System.out.println("\n### CARD_NUMBER:" + cardNo + " --- EXPIRY_DATE" + expDat + " --- CVV: "+cVV+" --- ###\n");
                listData2 = DdataObj.sendOrder(dataObj, cardName,cardNo, expDat, cVV,method,message);
                if (listData2 != null) {
                    isOk = true;
//                    serverMessage =listData2.get(0).getTxt3();
                    serverMessage = DdataObj.getServerResponce();
                } else {
                    serverMessage = DdataObj.getServerResponce() + "";
                }
            } catch (Exception ex) {
                serverMessage = ex.toString();
            }
            return null;
        }

        //
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Toast.makeText(mContext, values.length + "", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog_1.isShowing())
                pDialog_1.dismiss();


            // emrnAObj.alartBox("",""+serverMessage);
            System.out.println("\n###: Server responce" + serverMessage + "");
            if (listData2 != null) {
//                emrnAObj.isFirst(1);
                //if(serverMessage)
//                showRecest();
                Toast.makeText(mContext, serverMessage + "", Toast.LENGTH_SHORT).show();
                if (showRecest()) {
                    startActivity(emrnAObj.Start_Activity(mContext, Receipt.class));
                } else {
                    emrnAObj.alartBox("", "Order information not complete");
                }
                // Toast.makeText(mContext,serverMessage+"", Toast.LENGTH_SHORT).show();

                // startActivity(emrnAObj.Start_Activity(mContext, GetStarted.class));
            } else {
                /// emrnAObj.alartBox("",serverMessage);
                emrnAObj.alartBox("", "Internal server error");
                Log.d("Server info: ", serverMessage);
            }
//            emrnAObj.alartBox("",serverMessage);
//             Toast.makeText(mContext,""+n+"\n"+serverMessage+"\n"+isOk, Toast.LENGTH_LONG).show();
        }
    }

    public boolean showRecest() {
        try {
            emrnAObj.setUseValue("OrderNumber", listData2.get(0).getTxt3());
            //emrnAObj.setUseValue("OrderNumber", listData2.get(0).getTxt1());
//            if(listData2.get(position).getTxt4().equals("0"))
            emrnAObj.setUseValue("OrderTime", "Waiting");
//            else if(listData2.get(position).getTxt4().equals("1"))
//                emrnAObj.setUseValue("OrderTime", "Processing");
//            else if(listData2.get(position).getTxt4().equals("2"))
//                emrnAObj.setUseValue("OrderTime", "Complite");
//            else if(listData2.get(position).getTxt4().equals("3"))
//                emrnAObj.setUseValue("OrderTime", "Delivered");
//            else
//                emrnAObj.setUseValue("OrderTime", "Delivered");

            emrnAObj.setUseValue("OrderMessage", listData2.get(0).getTxt1());
            emrnAObj.setUseValue("OrderTotalPrice", listData2.get(0).getTxt7());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //emrnAObj.alartBox("","OnDistroy");
//        System.out.println("### "+" CheckOut: ondistroy");
        tatalPrice = 0.0f;
    }

    public void getDelivery(View view){
        Intent intent=new Intent(CheckOut.this, DeleveryActivity.class);
        startActivityForResult(intent, 2);// Activity is started with requestCode 2
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            method=data.getStringExtra("METHOD");
            if(method.equalsIgnoreCase("1")){
                tvConvenience_fee.setText("2");
                tatalPrice=tatalPrice+1;
                checkout_totalPrice.setText("" + tatalPrice);
                try {
                    JsonOrderObj.put("ORDER_TOTAL", "" + tatalPrice);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            message=data.getStringExtra("MESSAGE");
            //Toast.makeText(getApplicationContext(),"value: "+method+message,Toast.LENGTH_LONG).show();
            //textView1.setText(message);
        }
    }


}
