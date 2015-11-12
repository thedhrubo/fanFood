package axiz.four.androap.fanfood;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import java.util.ArrayList;

import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;
import axiz.four.androap.fanfood.serversite.DownJsonToArray;
import axiz.four.androap.fanfood.utilities.Utils;

public class MainLoginActivity extends FragmentActivity {

    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    private TextView mainLogin_txt_id;
    private EditText signin_txt_email, signin_txt_passwd;
    private Button signin_btn_id_createNew, signin_btn_id_signin, signin_btn_id_forget, guest_btn_id_guest;
    private CheckBox login_rememberLogin;

    private String CurrentMessage = "", CurrentAlartTitle = "", User_ID = "0";
    private DataAbstractEvent dataObj;
    private ProgressDialog pDialog_1;
    private ArrayList<DataAbstractEvent> listData2;
    private boolean IS_OK = false;
    private String e, p;

    private ImageView facebook, google;

    SocialAuthAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            //String mData = mBundle.getString("com.parse.Data");
            //System.out.println("DATA : xxxxx : " + mData);
            try {
                JSONObject jsonObject = new JSONObject(mBundle.getString("com.parse.Data"));
                String data=jsonObject.getString("alert");
                Log.e("alert",data);
                Intent pushBord= new Intent(getApplicationContext(),NotificationBoardActivity.class);
                pushBord.putExtra("msgBody",data);
                //notificationDetails.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }*/
        try {
//            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            getActionBar().hide();
//           requestWindowFeature(Window.FEATURE_NO_TITLE);

            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } catch (Exception ex) {
            System.out.println("###: " + ex.toString());
            try {
                requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            } catch (Exception exc) {
                System.out.println("####" + ex.toString());
            }
        }
        setContentView(R.layout.activity_main_login);
        mActivity = (Activity) this;
        mContext = (Context) this;
        emrnAObj = new Emran_a(mContext, mActivity);

        InitializeComponent();
        payInfoCheck();
        RememberChack();
//        setVestLogin();

//        if(IsFirst()){
//            startActivity(emrnAObj.Start_Activity2(MainLoginActivity.this, Intro.class));
//        }

        //*************************** ****************************
        //Button action
        signin_btn_id_createNew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                emrnAObj.isFirst(1);
                startActivity(emrnAObj.Start_Activity(mContext, SignUp.class));// MainActivity use as Sign in
            }
        });


        signin_btn_id_signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //startActivity(emrnAObj.Start_Activity(mContext, SignUp.class));// MainActivity use as Sign in
                if (GetValue()) {
                    CurrentAlartTitle = "";
                    CurrentMessage = "";// Clear this variable
                    if (dataObj != null) {
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
                        emrnAObj.alartBox("", "Your information not complete, please try again ");
                    }
                } else {
                    emrnAObj.alartBox(CurrentAlartTitle, CurrentMessage);
                    CurrentAlartTitle = "";
                    CurrentMessage = "";
                }

            }
        });

        signin_btn_id_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emrnAObj.setUseValue("STATUS", "0");
                startActivity(emrnAObj.Start_Activity(mContext, PasswordRecover.class));
            }
        });

        //facebook, google
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.authorize(mContext, SocialAuthAdapter.Provider.FACEBOOK);
            }
        });


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.authorize(mContext, SocialAuthAdapter.Provider.GOOGLEPLUS);
            }
        });

        guest_btn_id_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emrnAObj.setUseValue("User_ID", "0");
                startActivity(emrnAObj.Start_Activity(mContext, GetStarted.class));
            }
        });
        //End Button action
        //*************************** ****************************

//        login_rememberLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                String ml = signin_txt_email.getText().toString();
//                String ps = signin_txt_passwd.getText().toString();
//                if(isChecked){
//                    if(emrnAObj.isName2(ml,2) && emrnAObj.isName2(ps,5)){
//                        emrnAObj.setUseValue("RM_EMAIL", ml);
//                        emrnAObj.setUseValue("RM_PASS", ps);
//                    }else{
//                        emrnAObj.alartBox("","Please give up correct information");
//                        login_rememberLogin.setChecked(false);
//                    }
//                }else{
//                    emrnAObj.setUseValue("RM_EMAIL", "");
//                    emrnAObj.setUseValue("RM_PASS", "");
//                }
//            }
//        });
        //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void payInfoCheck() {

        String StayInLogin = emrnAObj.getUseValue("StayInLogin");

        if (StayInLogin != null) {
            if (StayInLogin.equals("OK")) {
               // paymentInformation();
            }
        } else {
            startActivity(emrnAObj.Start_Activity2(MainLoginActivity.this, Intro.class));
        }
    }

    private boolean paymentInformation() {
        String cardNo = emrnAObj.getUseValue("CARD_NUMBER");
        String expDat = emrnAObj.getUseValue("EXPIRY_DATE");
        if (!IsFirst()) {
            if (cardNo == null || expDat == null) {
                Toast.makeText(mContext, "Require payment information", Toast.LENGTH_LONG).show();
                startActivity(emrnAObj.Start_Activity(mContext, Payment.class));// MainActivity use as Sign in
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private void RememberChack() {
        try {
            String eml = emrnAObj.getUseValue("RM_EMAIL");
            String pas = emrnAObj.getUseValue("RM_PASS");
//            Toast.makeText(mContext, ""+eml+", "+pas+"" , Toast.LENGTH_SHORT).show();
            if (eml != null && !eml.equals("") && pas != null && !pas.equals("")) {
                signin_txt_email.setText("" + eml);
                signin_txt_passwd.setText("" + pas);
                login_rememberLogin.setChecked(true);
            } else {
                signin_txt_email.setText("");
                signin_txt_passwd.setText("");
                login_rememberLogin.setChecked(false);
            }
        } catch (Exception ex) {
            Toast.makeText(mContext, "Not working" + ", " + "" + ex.toString(), Toast.LENGTH_SHORT).show();
            emrnAObj.setUseValue("RM_EMAIL", "");
            emrnAObj.setUseValue("RM_PASS", "");
            login_rememberLogin.setChecked(false);
        }
    }

    private boolean GetValue() {
        boolean b = true;
        String EmailID = signin_txt_email.getText().toString().trim();
        String Passwd = signin_txt_passwd.getText().toString().trim();

        if (b && !emrnAObj.Email_Validation(EmailID)) {
            b = false;
            CurrentMessage += getResources().getString(R.string.show_signin_msg_email) + "\n";
        }

        if (b && !emrnAObj.isName2(Passwd, 5)) {
            b = false;
            CurrentMessage += getResources().getString(R.string.show_signin_msg_pass) + "\n";
        }
        // if(!b) CurrentAlartTitle = "Information required";
        if (b) {
            dataObj = new DataAbstractEvent(EmailID, Passwd);
            emrnAObj.setUseValue("UserEmail", EmailID);
            if (login_rememberLogin.isChecked()) {
                emrnAObj.setUseValue("RM_EMAIL", EmailID);
                emrnAObj.setUseValue("RM_PASS", Passwd);
                emrnAObj.setUseValue("RE_CHK_PASS", Passwd);// This value need for order time
            } else {
                emrnAObj.setUseValue("RM_EMAIL", "");
                emrnAObj.setUseValue("RM_PASS", "");
            }
        }
        return b;
    }

    /*
 * This Function use for check, this app is first time run?
 * */
    private boolean IsFirst() {
        /* 0 for get is registered. If registered then return false, meaning is Not first. if return true meaning need to registration*/
        return emrnAObj.isFirst(0);
//		return false;
    }

    private void setVestLogin() {
        signin_txt_email.setText("emran@4axiz.com");
        signin_txt_passwd.setText("123456");
    }


    private void InitializeComponent() {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "font/impact.ttf");
        Typeface tf2 = Typeface.createFromAsset(mContext.getAssets(), "font/open_sans_regular.ttf");

        signin_btn_id_createNew = (Button) findViewById(R.id.signin_btn_id_createNew);
        signin_btn_id_signin = (Button) findViewById(R.id.signin_btn_id_signin);
        signin_btn_id_forget = (Button) findViewById(R.id.signin_btn_id_forget);
        guest_btn_id_guest = (Button) findViewById(R.id.guest_btn_id_guest);
        signin_btn_id_createNew.setTypeface(tf);
        signin_btn_id_signin.setTypeface(tf);
        signin_btn_id_forget.setTypeface(tf2);
        guest_btn_id_guest.setTypeface(tf);

        facebook = (ImageView) findViewById(R.id.signin_img_imageView5);
        google = (ImageView) findViewById(R.id.signin_img_imageView4);

        mainLogin_txt_id = (TextView) findViewById(R.id.mainLogin_txt_id);
        mainLogin_txt_id.setTypeface(tf);

        signin_txt_email = (EditText) findViewById(R.id.signin_edittxt_email);
        signin_txt_passwd = (EditText) findViewById(R.id.signin_edittxt_passwd);

        login_rememberLogin = (CheckBox) findViewById(R.id.login_rememberLogin);

        adapter = new SocialAuthAdapter(new ResponseListener());
    }


    //----------- menu options --------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signup) {
            startActivity(emrnAObj.Start_Activity(mContext, SignUp.class));// SignUp use as Register
//            return true;
        } else if (id == R.id.action_getstart) {
            emrnAObj.setUseValue("User_ID", "0");
            startActivity(emrnAObj.Start_Activity(mContext, GetStarted.class));
//            return true;
        }

        return super.onOptionsItemSelected(item);
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
                listData2 = DdataObj.setLogin(dataObj);
                if (listData2 != null) {
                    IS_OK = isOk = true;
                    serverMessage = listData2.get(0).getTxt1();
                    User_ID = listData2.get(0).getTxt2();
                    emrnAObj.setUseValue("User_ID", User_ID);
//                    emrnAObj.setUseValue("User_Email", dataObj.getTxt1());
                } else {
                    serverMessage = DdataObj.getServerResponce();
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
            if (isOk) {
//                emrnAObj.isFirst(1);
                //if(serverMessage)
//                Toast.makeText(mContext,serverMessage+"\n"+User_ID, Toast.LENGTH_LONG).show();
                emrnAObj.isFirst(1);


//                if(paymentInformation()){
                    /* FromWher desite the history. When a guest user try to order then softer need to authentication usinf this activity. And then
                    If login success full software go to priveous state by use finish();. If FromWher!= PayMent, app start tur as new but order stale have in list.
                    * */
                String FromWher = emrnAObj.getUseValue("FromWher");
                if (FromWher!=null && FromWher.equals("CheckOutPayMent")) {
                    emrnAObj.setUseValue("FromWher", "SetNew");
                    finish();
                } else {
                    startActivity(emrnAObj.Start_Activity(mContext, GetStarted.class));
                }
//                }
            } else {
                emrnAObj.alartBox("", serverMessage);
                Log.d("Server info: ", serverMessage);
            }
            // emrnAObj.alartBox("",serverMessage+"\nID: "+User_ID);
//             Toast.makeText(mContext,""+n+"\n"+serverMessage+"\n"+isOk, Toast.LENGTH_LONG).show();
        }
    }

    private void doSocialLogin() {
        Toast.makeText(mContext, "" + e, Toast.LENGTH_SHORT).show();
    }

    //----------------------------------------

    private final class ResponseListener implements DialogListener {
        public void onComplete(Bundle values) {

            adapter.getUserProfileAsync(new ProfileDataListener());
           /* progress = new ProgressDialog(LoginActivity.this);
            progress.setTitle("Please Wait");
            progress.setMessage("Connecting To Server...");
            progress.setCancelable(true);
            progress.show();*/
            // adapter.getContactListAsync(new ContactDataListener());
        }

        @Override
        public void onBack() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onCancel() {
            // TODO Auto-generated method stub

            //progress.dismiss();
        }

        @Override
        public void onError(SocialAuthError arg0) {
            // TODO Auto-generated method stub
            //progress.dismiss();
        }
    }

    // To receive the profile response after authentication
    private final class ProfileDataListener implements
            SocialAuthListener<Profile> {


        @Override
        public void onExecute(String provider, org.brickred.socialauth.Profile t) {

            Log.d("Custom-UI", "Receiving Data");

            Profile profileMap = t;

            e = profileMap.getEmail();

            doSocialLogin();

            //Toast.makeText(LoginActivity.this, profileMap.getFirstName()+ (" " + profileMap.getLastName()).replace("null", ""), Toast.LENGTH_LONG).show();

           /* User.name = profileMap.getFirstName()
                    + (" " + profileMap.getLastName()).replace("null", "");
            User.email = profileMap.getEmail();
            User.birthday = profileMap.getDob() + "";
            User.phone = "";
            User.id = profileMap.getValidatedId();
            User.gender = profileMap.getGender();
            User.firstName = profileMap.getFirstName();
            User.lastName = (profileMap.getLastName() + "").replace("null", "");
            User.bitmapPropic = ImageProcessor.processImage(profileMap
                    .getProfileImageURL());*/


        }

        @Override
        public void onError(SocialAuthError e) {

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.checkLogin(MainLoginActivity.this).length() > 0) {
//            Toast.makeText(MainLoginActivity.this, "Logged In ID = "+Utils.checkLogin(MainLoginActivity.this), Toast.LENGTH_SHORT).show();
        } else {
            //  Toast.makeText(MainLoginActivity.this, "No Logged User", Toast.LENGTH_SHORT).show();
        }
    }
}
