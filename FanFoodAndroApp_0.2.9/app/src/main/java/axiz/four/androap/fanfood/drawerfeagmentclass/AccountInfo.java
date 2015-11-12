package axiz.four.androap.fanfood.drawerfeagmentclass;

import android.app.ActionBar;
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


import axiz.four.androap.fanfood.R;
import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;
import axiz.four.androap.fanfood.serversite.DownJsonToArray;

public class AccountInfo extends Fragment {
    private ActionBar actionBar;
    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    private Button account_btn_id_signup, account_btn_accountUpdate;

    private EditText signup_txt_fname, signup_txt_lname, signup_txt_email,
            signup_txt_phonenum, signup_txt_passwd, signup_txt_passwd2,
            signup_txt_passwd3_con,
            account_txt_cardName,account_txt_cardNo, account_txt_mm, account_txt_yy,
            account_txt_cvv;
    ;
    private Button signup_btn_id_signup;

    private DataAbstractEvent dataObj;
    private String CurrentMessage = "", CurrentAlartTitle = "";

    private ProgressDialog pDialog_1;
    private ArrayList<DataAbstractEvent> listData2;
    private boolean IS_OK = false;

    private String User_ID = "0", UserEmail = "";
    private int WORK_MODE = 0;

    private AutoCompleteTextView account_txt_country;
    private Button account_btn_id_register;

    public AccountInfo() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        mContext = rootView.getContext();
        mActivity = getActivity();
        actionBar = (ActionBar) mActivity.getActionBar();
        emrnAObj = new Emran_a(mContext, mActivity);

        User_ID = emrnAObj.getUseValue("User_ID");
        UserEmail = emrnAObj.getUseValue("UserEmail");

        InitializeComponent(rootView);

//		account_btn_id_signup = (Button) rootView.findViewById(R.id.account_btn_id_signup);
//		account_btn_accountUpdate = (Button) rootView.findViewById(R.id.account_btn_accountUpdate);
//		Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "font/impact.ttf");
//		account_btn_id_signup.setTypeface(tf);
//		account_btn_accountUpdate.setTypeface(tf);

        GetUserInfo();
        signup_btn_id_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GetValue()) {
                    CurrentAlartTitle = "";
                    CurrentMessage = "";// Clear this variable
                    if (dataObj != null) {

                        if (emrnAObj.isWifiOnline(mActivity)
                                || emrnAObj.GetNetWorkState(mActivity)) {
                            try {
                                WORK_MODE = 1;//One for update
                                new GetContacts().execute();
                            } catch (Exception ex) {
                                emrnAObj.alartBox("System exception",
                                        "System not work!\n" + ex.toString());
                            }

                        } else {
                            emrnAObj.alartBox(emrnAObj.INTERNET_Required, emrnAObj.INTERNET_Need);
                        }

                    } else {
                        emrnAObj.alartBox("", "Your information not complete, please try again ");
                    }
//                    startActivity(emrnAObj.Start_Activity(mContext, Payment.class));
                } else {
                    emrnAObj.alartBox(CurrentAlartTitle, CurrentMessage);
                    CurrentAlartTitle = "";
                    CurrentMessage = "";
                }
            }
        });

        account_btn_id_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateCardInfo()) {
                    Toast.makeText(mContext, "Update complete", Toast.LENGTH_SHORT).show();
//                    emrnAObj.isFirst(1);
//                    Toast.makeText(mContext,"Registration complete",Toast.LENGTH_SHORT).show();
////                    startActivity(emrnAObj.Start_Activity(mContext, MainLoginActivity.class));
//                    String User_ID = emrnAObj.getUseValue("User_ID");
//                    if(User_ID.equals("0")){// 0 is as a guast user
//                        rootView.finish();
//                        Toast.makeText(mContext,"Please login again",Toast.LENGTH_SHORT).show();
//                        startActivity(emrnAObj.Start_Activity(mContext, MainLoginActivity.class));
//                    }
//                    finish();
                }
            }
        });

        return rootView;
    }

    private void GetUserInfo() {

        try {
//            User_ID = emrnAObj.getUseValue("");
//            UserEmail = emrnAObj.getUseValue("UserEmail");
            if (Integer.parseInt(User_ID) > 0) {
                if (emrnAObj.isWifiOnline(mActivity)
                        || emrnAObj.GetNetWorkState(mActivity)) {
                    try {
                        WORK_MODE = 0;
                        new GetContacts().execute();
                    } catch (Exception ex) {
                        emrnAObj.alartBox("System exception",
                                "System not work!\n" + ex.toString());
                    }
                } else {
                    emrnAObj.alartBox(emrnAObj.INTERNET_Required, emrnAObj.INTERNET_Need);
                }
            } else {
                emrnAObj.alartBox("", emrnAObj.LOGIN_BEFOUR);
                signup_btn_id_signup.setEnabled(false);
            }
        } catch (Exception ex) {

        }

        signup_btn_id_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void InitializeComponent(View rootView) {

        dataObj = new DataAbstractEvent();
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
                "font/impact.ttf");
        Typeface tf2 = Typeface.createFromAsset(mContext.getAssets(),
                "font/open_sans_regular.ttf");
//        signup_txt_id_signup = (TextView) findViewById(R.id.signup_txt_id_signup);
//        signup_txt_id_signup.setTypeface(tf);

        signup_txt_fname = (EditText) rootView.findViewById(R.id.account_txt_fname);
        signup_txt_lname = (EditText) rootView.findViewById(R.id.account_txt_lname);
        signup_txt_email = (EditText) rootView.findViewById(R.id.account_txt_email);
        signup_txt_phonenum = (EditText) rootView.findViewById(R.id.account_txt_phonenum);
        signup_txt_passwd = (EditText) rootView.findViewById(R.id.account_txt_passwd);
        signup_txt_passwd2 = (EditText) rootView.findViewById(R.id.account_txt_passwd2);
        signup_txt_passwd3_con = (EditText) rootView.findViewById(R.id.account_txt_passwd_con);

        signup_txt_passwd.setVisibility(EditText.GONE);
        signup_txt_passwd2.setVisibility(EditText.GONE);
        signup_txt_passwd3_con.setVisibility(EditText.GONE);

        signup_txt_fname.setTypeface(tf2);
        signup_txt_lname.setTypeface(tf2);
        signup_txt_email.setTypeface(tf2);
        signup_txt_email.setVisibility(EditText.GONE);
        signup_txt_phonenum.setTypeface(tf2);
//		signup_txt_passwd.setTypeface(tf2);
//		signup_txt_passwd2.setTypeface(tf2);
//		signup_txt_passwd3_con.setTypeface(tf2);
        signup_btn_id_signup = (Button) rootView.findViewById(R.id.account_btn_updateInfo);
        signup_btn_id_signup.setTypeface(tf);

//        signup_img_imageView1 = (ImageView) rootView.findViewById(R.id.signup_img_imageView1);
//        signup_img_imageView2 = (ImageView) rootView.findViewById(R.id.signup_img_imageView2);

        //adapter = new SocialAuthAdapter(new ResponseListener());

        //--------- Pay card information
        account_txt_cardName = (EditText) rootView.findViewById(R.id.account_txt_cardName);
        account_txt_cardNo = (EditText) rootView.findViewById(R.id.account_txt_cardNo);
        account_txt_mm = (EditText) rootView.findViewById(R.id.account_txt_mm);
        account_txt_yy = (EditText) rootView.findViewById(R.id.account_txt_yy);
        account_txt_cvv = (EditText) rootView.findViewById(R.id.account_txt_cvv);

        account_txt_cardName.setTypeface(tf2);
        account_txt_cardNo.setTypeface(tf2);
        account_txt_mm.setTypeface(tf2);
        account_txt_yy.setTypeface(tf2);
        account_txt_cvv.setTypeface(tf2);

        account_txt_country = (AutoCompleteTextView) rootView.findViewById(R.id.account_txt_country);
        account_txt_country.setTypeface(tf2);

        account_btn_id_register = (Button) rootView.findViewById(R.id.account_btn_id_register);
        account_btn_id_register.setTypeface(tf);
    }

    private void setUserValus() {

        signup_txt_fname.setText(listData2.get(0).getTxt1());
        signup_txt_lname.setText(listData2.get(0).getTxt2());
        signup_txt_email.setText(listData2.get(0).getTxt3());
        signup_txt_phonenum.setText(listData2.get(0).getTxt4());
        signup_txt_passwd.setText("");
        signup_txt_passwd3_con.setText("");

        try {

            String cardName = emrnAObj.getUseValue("CARD_NAME");
            String cardNo = emrnAObj.getUseValue("CARD_NUMBER");
            String mm = emrnAObj.getUseValue("EXPIRY_DATE");
            mm =mm.substring(0,2);
            String yy = emrnAObj.getUseValue("EXPIRY_DATE");
            yy = yy.substring(2,4);
            String cVV = emrnAObj.getUseValue("CARD_CVV");
            String country = emrnAObj.getUseValue("CARD_COUNTRY");

            System.out.println("### mm: "+mm+", yy: "+yy+", cVV: "+cVV );

            if(cardName!=null && cardName.length()>0){ account_txt_cardName.setText(cardName); }
            if(cardNo!=null && cardNo.length()>0){ account_txt_cardNo.setText(cardNo); }
            if(mm!=null && mm.length()>0){ account_txt_mm.setText(mm); }
            if(yy!=null && yy.length()>0){ account_txt_yy.setText(yy); }
            if(cVV!=null && cVV.length()>0){ account_txt_cvv.setText(cVV); }
            if(country!=null && country.length()>0){ account_txt_country.setText(country); }

        } catch (Exception ex) {
            System.out.println("### Card info exception: "+ex.toString());
        }

    }

    private boolean updateCardInfo() {
//        payment_txt_mm, payment_txt_yy,
//                payment_txt_cvv
        String cardName = account_txt_cardName.getText().toString().trim();
        String cardNo = account_txt_cardNo.getText().toString().trim();
        String mm = account_txt_mm.getText().toString().trim();
        String yy = account_txt_yy.getText().toString().trim();
        String cVV = account_txt_cvv.getText().toString().trim();
        String country = account_txt_country.getText().toString().trim();

        boolean b = true;
        if (!emrnAObj.isName(cardNo)) {
            b = false;
            CurrentMessage += "Please enter correct card number" + "\n";
        }

        if (b && !emrnAObj.isName(mm)) {
            b = false;
            CurrentMessage += "Enter month (06 for June)" + "\n";
        }


        if (b && !emrnAObj.isName(yy)) {
            b = false;
            CurrentMessage += "Enter year (15 for 2015)" + "\n";
        }

        if (b && !emrnAObj.isName(cVV)) {
            b = false;
            CurrentMessage += "Enter CVV" + "\n";
        }

        if (b) {
            try {
                int m = Integer.parseInt(mm);
                if (m < 10) {
                    mm = "0" + m;
                }
                emrnAObj.setUseValue("EXPIRY_DATE", mm + yy);
                emrnAObj.setUseValue("CARD_NAME", cardName);
                emrnAObj.setUseValue("CARD_NUMBER", cardNo);
                emrnAObj.setUseValue("CARD_CVV", cVV);
                if (country != null && country.length() > 0) {
                    emrnAObj.setUseValue("CARD_COUNTRY", country);
                }
            } catch (Exception ex) {
                System.out.println("###: " + ex.toString());
                b = false;
            }
        }
        return b;
    }


    //get value from input text.
    private boolean GetValue() {
        String FirstName = signup_txt_fname.getText().toString().trim();
        String LastName = signup_txt_lname.getText().toString().trim();
        String EmailID = signup_txt_email.getText().toString().trim();
        String Phone = signup_txt_phonenum.getText().toString().trim();
//        String Passwd  = signup_txt_passwd.getText().toString().trim();
//        String Con_passwd  = signup_txt_passwd3_con.getText().toString().trim();

        boolean b = true;
        if (!emrnAObj.isName(FirstName)) {
            b = false;
            CurrentMessage += getResources().getString(R.string.show_signup_msg_fname) + "\n";
        }

        if (b && !emrnAObj.isName(LastName)) {
            b = false;
            CurrentMessage += getResources().getString(R.string.show_signup_msg_lname) + "\n";
        }

        if (b && !emrnAObj.Email_Validation(EmailID)) {
            b = false;
            CurrentMessage += getResources().getString(R.string.show_signup_msg_email) + "\n";
        }

        if (b && !emrnAObj.isName2(Phone, 5)) {
            b = false;
            CurrentMessage += getResources().getString(R.string.show_signup_msg_phn) + "\n";
        }

//        if(b && !emrnAObj.isName2(Passwd, 5)){
//            b=false;
//            CurrentMessage+=getResources().getString(R.string.show_signup_msg_pass) + "\n";
//        }

//        if(b && !emrnAObj.isName2(Con_passwd, 5)){
//            b=false;
//            CurrentMessage+=getResources().getString(R.string.show_signup_msg_conpass) + "\n";
//        }
//
//        if(b){
//            if(!Passwd.equals(Con_passwd)){
//                CurrentMessage+=getResources().getString(R.string.show_signup_msg_con_pass) + "\n";
//                b=false;
//            }
//        }

        //if(!b) CurrentAlartTitle = "Information required";
        if (b) {
            dataObj = new DataAbstractEvent(FirstName, LastName, EmailID, Phone);
//            emrnAObj.setUseValue("FirstName", FirstName);
//            emrnAObj.setUseValue("LastName", LastName);
//            emrnAObj.setUseValue("EmailID", EmailID);
//            emrnAObj.setUseValue("Phone", Phone);
//            emrnAObj.setUseValue("Passwd", Passwd);
        }
        return b;
    }

    //------------------- API connectior -------------------

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
                if (WORK_MODE == 0) {
                    dataObj = new DataAbstractEvent(User_ID, UserEmail);
                    listData2 = DdataObj.getUserInfo(dataObj);
                    if (listData2 != null) {
                        IS_OK = isOk = true;
//                        serverMessage =listData2.get(0).getTxt1();
//                        User_ID =listData2.get(0).getTxt2();
//                        emrnAObj.setUseValue("User_ID", User_ID);
                        //                    emrnAObj.setUseValue("User_Email", dataObj.getTxt1());
                    } else {
                        serverMessage = DdataObj.getServerResponce();
                    }
                } else if (WORK_MODE == 1) {
//                    dataObj = new DataAbstractEvent(User_ID, UserEmail);
                    listData2 = DdataObj.setUpdateInfo(dataObj);
                    if (listData2 != null) {
                        IS_OK = isOk = true;
                        serverMessage = listData2.get(0).getTxt1();

                    } else {
                        serverMessage = DdataObj.getServerResponce();
                    }
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
            if (WORK_MODE == 0 && isOk) {
//                emrnAObj.isFirst(1);
//               if(serverMessage)
//                Toast.makeText(mContext,serverMessage+"\n"+User_ID, Toast.LENGTH_LONG).show();
                // startActivity(emrnAObj.Start_Activity(mContext, GetStarted.class));
                setUserValus();
            } else if (WORK_MODE == 1 && isOk) {
                emrnAObj.alartBox("", serverMessage);
            } else {
                emrnAObj.alartBox("", serverMessage);
                Log.d("Server info: ", serverMessage);
            }
            // emrnAObj.alartBox("",serverMessage);
//            emrnAObj.alartBox("",serverMessage);
//             Toast.makeText(mContext,""+n+"\n"+serverMessage+"\n"+isOk, Toast.LENGTH_LONG).show();
        }
    }


}
