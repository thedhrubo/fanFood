package axiz.four.androap.fanfood;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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

/**
 * Created by emran on 4/7/15.
 */
public class SignUp extends Activity {
    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    private ImageView signup_img_imageView1, signup_img_imageView2;
    private TextView signup_txt_id_signup;
    private EditText signup_txt_fname, signup_txt_lname, signup_txt_email,
            signup_txt_phonenum, signup_txt_passwd, signup_txt_passwd2,
            signup_txt_passwd3_con;
    private Button signup_btn_id_signup;

    private DataAbstractEvent dataObj;
    private String CurrentMessage="", CurrentAlartTitle="";

    private ProgressDialog pDialog_1;
    private ArrayList<DataAbstractEvent> listData2;
    private boolean IS_OK = false;

    //---------------------------------------------
    SocialAuthAdapter adapter;
    int whatSocial =0;// 0 = no Social, 1=google, 2 = facebook


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // ****************************************************************
        // ******************** Initialize components *********************
        mActivity = (Activity) this;
        mContext = (Context) this;
        InitializeComponent();
        //################################################################

        //****************************************************************
        //******************** Button action activity ********************
        signup_btn_id_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//				emrnAObj.isFirst(1);
                if(GetValue()){
                    CurrentAlartTitle=""; CurrentMessage="";// Clear this variable
                    if(dataObj!=null){

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

                    }else{
                        emrnAObj.alartBox("","Your information not complete, please try again ");
                    }
//                    startActivity(emrnAObj.Start_Activity(mContext, Payment.class));
                }else{
                    emrnAObj.alartBox(CurrentAlartTitle, CurrentMessage);
                    CurrentAlartTitle=""; CurrentMessage="";
                }
            }
        });
        //################################################################

        //*************** Login by google *******************
//        signup_img_imageView2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                whatSocial =1;// 0 = no Social, 1=google, 2 = facebook
//                emrnAObj.alartBox("","HI google+");
//                adapter.authorize(SignUp.this, SocialAuthAdapter.Provider.GOOGLEPLUS);
//
//                return false;
//            }
//        });

        signup_img_imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatSocial =1;// 0 = no Social, 1=google, 2 = facebook
               // emrnAObj.alartBox("","HI google+");
                adapter.authorize(SignUp.this, SocialAuthAdapter.Provider.GOOGLEPLUS);
            }
        });

        //*************** Login by Facebook *******************
        signup_img_imageView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                adapter.authorize(SignUp.this, SocialAuthAdapter.Provider.FACEBOOK);
                return false;
            }
        });
    }

    private void InitializeComponent() {
        emrnAObj = new Emran_a(mContext, mActivity);
        dataObj = new DataAbstractEvent();
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
                "font/impact.ttf");
        Typeface tf2 = Typeface.createFromAsset(mContext.getAssets(),
                "font/open_sans_regular.ttf");
        signup_txt_id_signup = (TextView) findViewById(R.id.signup_txt_id_signup);
        signup_txt_id_signup.setTypeface(tf);

        signup_txt_fname = (EditText) findViewById(R.id.signup_txt_fname);
        signup_txt_lname = (EditText) findViewById(R.id.signup_txt_lname);
        signup_txt_email = (EditText) findViewById(R.id.signup_txt_email);
        signup_txt_phonenum = (EditText) findViewById(R.id.signup_txt_phonenum);
        signup_txt_passwd = (EditText) findViewById(R.id.signup_txt_passwd);
        signup_txt_passwd2 = (EditText) findViewById(R.id.signup_txt_passwd2);
        signup_txt_passwd3_con = (EditText) findViewById(R.id.signup_txt_passwd_con);

        signup_txt_fname.setTypeface(tf2);
        signup_txt_lname.setTypeface(tf2);
        signup_txt_email.setTypeface(tf2);
        signup_txt_phonenum.setTypeface(tf2);
//		signup_txt_passwd.setTypeface(tf2);
//		signup_txt_passwd2.setTypeface(tf2);
//		signup_txt_passwd3_con.setTypeface(tf2);

        signup_btn_id_signup = (Button) findViewById(R.id.signup_btn_id_signup);
        signup_btn_id_signup.setTypeface(tf);

        signup_img_imageView1 = (ImageView) findViewById(R.id.signup_img_imageView1);
        signup_img_imageView2 = (ImageView) findViewById(R.id.signup_img_imageView2);

        adapter = new SocialAuthAdapter(new ResponseListener());
    }

    //get value from input text.
    private boolean GetValue(){
        String FirstName = signup_txt_fname.getText().toString().trim();
        String LastName = signup_txt_lname.getText().toString().trim();
        String EmailID  = signup_txt_email.getText().toString().trim();
        String Phone  = signup_txt_phonenum.getText().toString().trim();
        String Passwd  = signup_txt_passwd.getText().toString().trim();
        String Con_passwd  = signup_txt_passwd3_con.getText().toString().trim();

        boolean b=true;
        if(!emrnAObj.isName(FirstName)){
            b=false;
            CurrentMessage+=getResources().getString(R.string.show_signup_msg_fname) + "\n";
        }

        if(b && !emrnAObj.isName(LastName)){
            b=false;
            CurrentMessage+=getResources().getString(R.string.show_signup_msg_lname) + "\n";
        }

        if(b && !emrnAObj.Email_Validation(EmailID)){
            b=false;
            CurrentMessage+=getResources().getString(R.string.show_signup_msg_email) + "\n";
        }

        if(b && !emrnAObj.isName2(Phone, 5)){
            b=false;
            CurrentMessage+=getResources().getString(R.string.show_signup_msg_phn) + "\n";
        }

        if(b && !emrnAObj.isName2(Passwd, 5)){
            b=false;
            CurrentMessage+=getResources().getString(R.string.show_signup_msg_pass) + "\n";
        }

        if(b && !emrnAObj.isName2(Con_passwd, 5)){
            b=false;
            CurrentMessage+=getResources().getString(R.string.show_signup_msg_conpass) + "\n";
        }

        if(b){
            if(!Passwd.equals(Con_passwd)){
                CurrentMessage+=getResources().getString(R.string.show_signup_msg_con_pass) + "\n";
                b=false;
            }
        }

        //if(!b) CurrentAlartTitle = "Information required";
        if(b){
            dataObj = new DataAbstractEvent(FirstName, LastName, EmailID, Phone, Passwd);
            emrnAObj.setUseValue("FirstName", FirstName);
            emrnAObj.setUseValue("LastName", LastName);
            emrnAObj.setUseValue("EmailID", EmailID);
            emrnAObj.setUseValue("Phone", Phone);
            emrnAObj.setUseValue("Passwd", Passwd);
        }
        return b;
    }

    //
    private class GetContacts extends AsyncTask<Void, Integer, Void> {
        boolean isOk = false;
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
                DownJsonToArray DdataObj = new DownJsonToArray();
                listData2 = DdataObj.setRegistration(dataObj);
                if(listData2!=null){
                    IS_OK = isOk = true;
//                    serverMessage =listData2.get(0).getTxt1()+"\n"+DdataObj.getServerResponce();
                    serverMessage =listData2.get(0).getTxt1();

                }else{
                    serverMessage =DdataObj.getServerResponce();
                }
            }catch(Exception ex){
                serverMessage=ex.toString();
            }
            return null;
        }
        //
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Toast.makeText(mContext, values.length+"", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog_1.isShowing())
                pDialog_1.dismiss();
            if(isOk){
                emrnAObj.isFirst(1);
//              Toast.makeText(mContext,serverMessage, Toast.LENGTH_LONG).show();
//                startActivity(emrnAObj.Start_Activity(mContext, MainLoginActivity.class));
                finish();
                emrnAObj.setUseValue("RegDone","YES");
                startActivity(emrnAObj.Start_Activity(mContext, Payment.class));
            }else{
                emrnAObj.alartBox("",serverMessage);
                Log.d("Server info: ", serverMessage);
            }
//             Toast.makeText(mContext,""+n+"\n"+serverMessage+"\n"+isOk, Toast.LENGTH_LONG).show();
//            emrnAObj.alartBox("",serverMessage);
//            Log.d("Server info: ", serverMessage);

        }
    }





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
    private final class ProfileDataListener implements SocialAuthListener<Profile> {

        @Override
        public void onExecute(String provider, org.brickred.socialauth.Profile t) {

            //Log.d("Custom-UI", "Receiving Data");

            Profile profileMap = t;
            String FirstName = profileMap.getFirstName();
            String LastName = profileMap.getLastName();
            String EmailID = profileMap.getEmail();
            String Phone = "";
            String Passwd = "";
            emrnAObj.alartBox("","Email");
            dataObj = new DataAbstractEvent(FirstName, LastName, EmailID, Phone, Passwd);
            emrnAObj.setUseValue("FirstName", FirstName);
            emrnAObj.setUseValue("LastName", LastName);
            emrnAObj.setUseValue("EmailID", EmailID);
            emrnAObj.setUseValue("SocialIndex", whatSocial+"");
//          emrnAObj.setUseValue("Phone", Phone);
//          emrnAObj.setUseValue("Passwd", Passwd);

          //  SocialRegister();
        }

        @Override
        public void onError(SocialAuthError e) {

            emrnAObj.alartBox("",""+e.toString());

        }
    }


}
