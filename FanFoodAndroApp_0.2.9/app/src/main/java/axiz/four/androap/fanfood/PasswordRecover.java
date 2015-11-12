package axiz.four.androap.fanfood;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.emran_method.Emran_a;
import axiz.four.androap.fanfood.serversite.DownJsonToArray;

public class PasswordRecover extends ActionBarActivity {

    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    private EditText signin_txt_email, passwdRecover_edittxt_verificationCode, signin_txt_passwd, signin_txt_Cpasswd;
    private Button signin_btn_id_createNew, signin_btn_id_signin, signin_btn_id_forget;
    private TextView paswd_recovar_toptitle;

    private String CurrentMessage="", CurrentAlartTitle="";
    private DataAbstractEvent dataObj;
    private ProgressDialog pDialog_1;
    private ArrayList<DataAbstractEvent> listData2;
    private boolean IS_OK = false;
    private String status="0";
    private int WORK_MODE =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover);
        mActivity = (Activity)this;
        mContext = (Context)this;
        emrnAObj = new Emran_a(mContext, mActivity);

//        status = emrnAObj.getUseValue("STATUS");

        InitializeComponent();

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
                if(GetValue()){
                    CurrentAlartTitle=""; CurrentMessage="";// Clear this variable
                    if(dataObj!=null){
                        if (emrnAObj.isWifiOnline(mActivity)
                                || emrnAObj.GetNetWorkState(mActivity)) {
                            try {
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

                }else{
                    emrnAObj.alartBox(CurrentAlartTitle, CurrentMessage);
                    CurrentAlartTitle=""; CurrentMessage="";
                }

            }
        });

        //End Button action
        //*************************** ****************************

    }

    private boolean GetValue(){
        boolean b=true;
        status = emrnAObj.getUseValue("STATUS");
        if(status.equals("0")){
            String EmailID  = signin_txt_email.getText().toString().trim();
            if(b && !emrnAObj.Email_Validation(EmailID)){
                b=false;
                CurrentMessage+=getResources().getString(R.string.show_signin_msg_email) + "\n";
            }
            // if(!b) CurrentAlartTitle = "Information required";
            if(b){
                dataObj = new DataAbstractEvent(EmailID);
                emrnAObj.setUseValue("EmailID", EmailID);
                WORK_MODE =0;
            }
            return b;
        }
        else if(status.equals("1")){
            String V_COde  = passwdRecover_edittxt_verificationCode.getText().toString().trim();
            if(b && !emrnAObj.isName2(V_COde,4)){
                b=false;
                CurrentMessage+=getResources().getString(R.string.passwdRecover_verificatrion_code) + "\n";
            }
            // if(!b) CurrentAlartTitle = "Information required";
            if(b){
                dataObj = new DataAbstractEvent(emrnAObj.getUseValue("EmailID"), V_COde);
                WORK_MODE =1;
            }
            return b;
        }
        else if(status.equals("2")){
            String Passwd  = signin_txt_passwd.getText().toString().trim();
            String Con_passwd  = signin_txt_Cpasswd.getText().toString().trim();
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
            // if(!b) CurrentAlartTitle = "Information required";
            if(b){
                dataObj = new DataAbstractEvent(emrnAObj.getUseValue("EmailID"), Passwd);
                WORK_MODE =2;
            }
            return b;
        }
        return false;
//        String Passwd  = signin_txt_passwd.getText().toString().trim();
    }

    private void InitializeComponent() {
        emrnAObj = new Emran_a(mContext, mActivity);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "font/impact.ttf");
        Typeface tf2 = Typeface.createFromAsset(mContext.getAssets(), "font/open_sans_regular.ttf");

        paswd_recovar_toptitle = (TextView) findViewById(R.id.paswd_recovar_toptitle);
        paswd_recovar_toptitle.setTypeface(tf);

                signin_btn_id_createNew = (Button)findViewById(R.id.passwdRecover_btn_id_createNew);
        signin_btn_id_signin = (Button)findViewById(R.id.passwdRecover_btn_id_signin);

        signin_btn_id_createNew.setTypeface(tf);
        signin_btn_id_signin.setTypeface(tf);

        signin_txt_email = (EditText) findViewById(R.id.passwdRecover_edittxt_email);
        passwdRecover_edittxt_verificationCode = (EditText) findViewById(R.id.passwdRecover_edittxt_verificationCode);
        signin_txt_passwd= (EditText) findViewById(R.id.passwdRecover_edittxt_passwd);
        signin_txt_Cpasswd= (EditText) findViewById(R.id.passwdRecover_edittxt_Cpasswd);
    }
    private void ShowValiedCode() {
        WORK_MODE = 1;
        passwdRecover_edittxt_verificationCode.setVisibility(EditText.VISIBLE);
        signin_txt_email.setVisibility(EditText.GONE);
        signin_txt_passwd.setVisibility(EditText.GONE);
        signin_txt_Cpasswd.setVisibility(EditText.GONE);
        emrnAObj.setUseValue("STATUS","1");
    }

    private void ShowPassword() {
        WORK_MODE = 2;
        passwdRecover_edittxt_verificationCode.setVisibility(EditText.GONE);
        signin_txt_email.setVisibility(EditText.GONE);
        signin_txt_passwd.setVisibility(EditText.VISIBLE);
        signin_txt_Cpasswd.setVisibility(EditText.VISIBLE);
        emrnAObj.setUseValue("STATUS","2");
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
        }else if(id == R.id.action_getstart){
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
            if(WORK_MODE ==0){
                try {
                    DownJsonToArray DdataObj = new DownJsonToArray();
                    listData2 = DdataObj.setPasswdRecoverEmail(dataObj);
                    if(listData2!=null){
                        IS_OK = isOk = true;
                        serverMessage =listData2.get(0).getTxt1();
                    }else{
                        serverMessage =DdataObj.getServerResponce();
                    }
                }catch(Exception ex){
                    serverMessage=ex.toString();
                }
            }else if(WORK_MODE == 1){
                try {
                    DownJsonToArray DdataObj = new DownJsonToArray();
                    listData2 = DdataObj.setVerifyCode(dataObj);
                    if(listData2!=null){
                        IS_OK = isOk = true;
                        serverMessage =listData2.get(0).getTxt1();
                    }else{
                        serverMessage =DdataObj.getServerResponce();
                    }
                }catch(Exception ex){
                    serverMessage=ex.toString();
                }

            }else if(WORK_MODE == 2){
                try {
                    DownJsonToArray DdataObj = new DownJsonToArray();
                    listData2 = DdataObj.setResetPassword(dataObj);
                    if(listData2!=null){
                        IS_OK = isOk = true;
                        serverMessage =listData2.get(0).getTxt1();
                    }else{
                        serverMessage =DdataObj.getServerResponce();
                    }
                }catch(Exception ex){
                    serverMessage=ex.toString();
                }

            }else if(WORK_MODE == 3){

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
            if(WORK_MODE ==0){
                if(isOk){
                    ShowValiedCode();// Show Varification code
                }else{
                    emrnAObj.alartBox("",serverMessage);
                    Log.d("Server info: ", serverMessage);
                }
//            emrnAObj.alartBox("",serverMessage);
            }// end WORK_MODE 0
            else if(WORK_MODE ==1){
                if(isOk){
                    ShowPassword();// Show Varification code
                }else{
                    emrnAObj.alartBox("",serverMessage);
                    Log.d("Server info: ", serverMessage);
                }
            }
            else if(WORK_MODE ==2){
                if(isOk){
                    Toast.makeText(mContext,serverMessage, Toast.LENGTH_LONG).show();
                    startActivity(emrnAObj.Start_Activity(mContext, MainLoginActivity.class));
                }else{
                    emrnAObj.alartBox("",serverMessage);
                    Log.d("Server info: ", serverMessage);
                }
            }
//             Toast.makeText(mContext,""+n+"\n"+serverMessage+"\n"+isOk, Toast.LENGTH_LONG).show();
        }
    }

}
