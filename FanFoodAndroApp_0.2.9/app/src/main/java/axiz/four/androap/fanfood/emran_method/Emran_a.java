package axiz.four.androap.fanfood.emran_method;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Emran_a {
    private Context G_context;
    private Activity G_activity;

    public final String INTERNET_Required = "Internet required";
    public final String INTERNET_Need = "Check your connection and try again";
    public final String LOGIN_BEFOUR = "Please login before";

    public Emran_a() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Emran_a(Context context) {
        super();
        G_context = context;
    }

    public Emran_a(Context context, Activity activity) {
        super();
        // TODO Auto-generated constructor stub
        G_context = context;
        G_activity = activity;
    }

    /**
     * <b>isFirst</b> method use for remember user use this app first time or
     * not.</br> In this method I use SharedPreferences class.</br> <b>Input
     * type:</b> Integer </br> <b>Input value:</b> 0, 1</br> </br> 0 input for
     * request get is it first time, If first time return true</br> 1 input for
     * request set is already used first time and return true </br>
     */

    public boolean isFirst(int keyCode) {

        SharedPreferences preData = PreferenceManager
                .getDefaultSharedPreferences(G_context);
        SharedPreferences.Editor addData = preData.edit();

        boolean rtnVal = true;
        switch (keyCode) {
            case 0: {// 0 for Login test

                if (preData.contains("registered")) {
                    rtnVal = false;
                }
                // else{
                // addData.putBoolean("registered", true);
                // }
                addData.commit();
                break;
            }
            case 1: {// Set is notFirst time
                addData.putBoolean("registered", true);
                addData.commit();
                break;
            }
            case 2: {
                addData.putBoolean("registered", false);
                addData.commit();
                break;
            }
            case 3: {

                break;
            }
            case 4: {

                break;
            }
            default:
                break;
        }
        return rtnVal;
    }

    // public String[] setGetUserID_pass(String userID, String passW, int
    // workMode){
    //
    // SharedPreferences preData = PreferenceManager
    // .getDefaultSharedPreferences(G_context);
    // SharedPreferences.Editor addData = preData.edit();
    //
    // switch (workMode) {
    // case 0:{
    // //Set
    // addData.putString("userID", ""+userID);
    // addData.putString("passW", ""+passW);
    // addData.commit();
    // break;
    // }
    // case 1:{//Get
    // userID = preData.getString("userID","");
    // passW = preData.getString("passW","");
    // addData.commit();
    // break;
    // }
    // default:
    // break;
    // }
    // String[] rtn ={userID, passW};
    // return rtn;
    //
    // }

    /**
     * <b>Start_Activity</b> method use for start new activity with new
     * task.</br> In this method I use Intent class.</br> <b>First
     * parameter:</b> Put current context</br> <b>Second parameter:</b> Put
     * target activity class</br> </br> <b>Return:</b> intent object</br>
     */

    public Intent Start_Activity(Context context, Class<?> class1) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(context, class1);
        // intent.putExtra(EXTRA_MESSAGE, message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * <b>Start_Activity2</b> method use for start new activity with clear
     * top.</br> In this method I use Intent class.</br> <b>First parameter:</b>
     * Put current context</br> <b>Second parameter:</b> Put target activity
     * class</br> </br> <b>Return:</b> intent object</br>
     */
    public Intent Start_Activity2(Context context, Class<?> class1) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(context, class1);
        // intent.putExtra(EXTRA_MESSAGE, message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public Intent Start_CamaraActivity(Context context, Class<?> class1,
                                       String EventNane, String EventDate, String EventNo) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(context, class1);
        intent.putExtra("EventNane", EventNane);
        intent.putExtra("EventNane", EventDate);
        intent.putExtra("EventNane", EventNo);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        return intent;
    }

    public void setRemember(boolean isChecked, String val) {

        String SharedPreferencesName = "CanSaveID";
        SharedPreferences prefs = G_context.getSharedPreferences(
                SharedPreferencesName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        // String val =prefs.getString(date_indx+"", null);
        editor.putString("isChack_id", "");
        // String ad_valu_mod = prefs.getString("isChack_id","10");

        if (isChecked) {
            editor.putString("isChack_id", "" + val);
        } else {
            editor.putString("isChack_id", "");
        }
        editor.commit();
    }

    public String getRemember(String val) {
        String SharedPreferencesName = "CanSave";
        SharedPreferences prefs = G_context.getSharedPreferences(
                SharedPreferencesName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String valu = prefs.getString("isChack_id", null);
        // editor.putString("isChack_id", "0");
        if (valu != null && valu != "") {
            val = valu;
        } else {
            val = "";
        }
        editor.commit();
        return val;
    }

    public void setUseValue(String key, String value) {
        String SharedPreferencesName = "user_data";
        SharedPreferences prefs = G_context.getSharedPreferences(
                SharedPreferencesName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        // String valu = prefs.getString(key, value);
        editor.putString(key, value);
        editor.commit();
    }

    public String getUseValue(String key) {
        String SharedPreferencesName = "user_data";
        SharedPreferences prefs = G_context.getSharedPreferences(
                SharedPreferencesName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String valu = prefs.getString(key, null);
        editor.commit();
        return valu;
    }

    public String[] getRemember(String val, String pass) {
        String SharedPreferencesName = "CanSave_Id_Pass";
        SharedPreferences prefs = G_context.getSharedPreferences(
                SharedPreferencesName, Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        String mail_id = prefs.getString("id", null);

        String passwd = prefs.getString("passwd", null);
        // editor.putString("isChack_id", "0");
        if (mail_id != null && mail_id != "") {
            val = mail_id;
        } else {
            val = "";
        }

        if (passwd != null && passwd != "") {
            pass = passwd;
        } else {
            pass = "";
        }
        editor.commit();
        String[] s = {val, pass};
        return s;
    }

    public void setRemember(String val, String pass) {
        String SharedPreferencesName = "CanSave_Id_Pass";
        SharedPreferences prefs = G_context.getSharedPreferences(
                SharedPreferencesName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        // String mail_id = prefs.getString("id", null);
        // String passwd = prefs.getString("passwd", null);
        // editor.putString("isChack_id", "0");

        if (val != null && val != "") {
            editor.putString("id", val);
        }

        if (pass != null && pass != "") {
            editor.putString("passwd", pass);
        }
        editor.commit();

    }

    public void setBlankRemember(String val, String pass) {
        String SharedPreferencesName = "CanSave_Id_Pass";
        SharedPreferences prefs = G_context.getSharedPreferences(
                SharedPreferencesName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        // String mail_id = prefs.getString("id", null);
        // String passwd = prefs.getString("passwd", null);
        // editor.putString("isChack_id", "0");

        if (val != null) {
            editor.putString("id", val);
        }

        if (pass != null) {
            editor.putString("passwd", pass);
        }
        editor.commit();

    }

    public boolean isUNI_ID(String uni_emailID) {
        boolean isOKid = false;
        if (!uni_emailID.trim().isEmpty()) {
            int ln = uni_emailID.length();
            if (ln >= 7) {
                isOKid = true;
            }
        }
        return isOKid;
    }

    /**
     * Get network is have or not
     */
    @SuppressLint("ShowToast")
    public boolean GetNetWorkState(Activity cOntext) {
        boolean St = false;
        try {
            ConnectivityManager conMgr = (ConnectivityManager) cOntext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
                    || conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
                // notify user you are online
                St = true;

            } else if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
                    || conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
                // notify user you are not online
                St = false;
            }
        } catch (Exception ex) {
            Toast.makeText(cOntext.getApplicationContext(),
                    "Network connection exception", Toast.LENGTH_LONG);
        }
        return St;
    }

    public boolean isWifiOnline(Activity cOntext) {
        ConnectivityManager cm = (ConnectivityManager) cOntext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void alartBox(String title, String dMsg) {
        // Alert Dialog. Import korte hobe android.app.AlertDialog; Attention
        Builder ad = new AlertDialog.Builder(this.G_context);
        ad.setTitle(title);
        ad.setMessage(dMsg);
        // set Button: PositiveButton
        // ad.setPositiveButton("YES", new DialogInterface.OnClickListener() {
        //
        // @Override
        // public void onClick(DialogInterface dialog, int which) {
        // // TODO Auto-generated method stub
        // startActivity(activityIntent);
        // }
        // });

        // set Button: setNegativeButton
        ad.setNegativeButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                // Toast.makeText(MainActivity.this, msg+"\nNot login",
                // Toast.LENGTH_SHORT).show();
            }
        });
        ad.show();
    }

    public boolean Email_Validation(String emailAddress) {
        Pattern pattern;
        Matcher matcher;
        // Email testing Regular expiration
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        // final String EMAIL_PATTERN =
        // "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@uni.edu"
        // + "$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

    public boolean isName(String name) {
        // boolean b = false;
        if (name.length() > 0 && name != null && !name.equals("null")) {
            // b = true;
            return true;
        }
        return false;
    }

    public boolean isName2(String name, int n) {
        boolean b = false;
        if (name.length() > n) {
            b = true;
        }
        return b;
    }

    public boolean isName3(String name, int n) {
        boolean b = false;
        if (name.length() < n) {
            b = true;
        }
        return b;
    }

    public String getDate(String date_) {
        char[] dt = date_.toCharArray();
        // String tmp="";
        date_ = "";
        for (int i = 0; i < dt.length; i++) {
            if (dt[i] == '/') {
                break;
            } else {
                date_ += dt[i];
            }
        }
        return date_;
    }

    public String getMonth(String date_) {
        char[] dt = date_.toCharArray();
        // String tmp="";
        date_ = "";
        int c = 0;
        for (int i = 0; i < dt.length; i++) {
            if (dt[i] == '/') {
                c += 1;
            } else {
                if (c == 1) {
                    date_ += dt[i];
                }
            }
        }
        return date_;
    }

    public String getYr(String date_) {
        char[] dt = date_.toCharArray();
        // String tmp="";
        date_ = "";
        int c = 0;
        for (int i = 0; i < dt.length; i++) {
            if (dt[i] == '/') {
                c += 1;
            } else {
                if (c == 2) {
                    date_ += dt[i];
                }
            }
        }
        return date_;
    }

    public String getHnr(String Time_) {
        String ampm = Time_;
        char[] dt = Time_.toCharArray();
        // String tmp="";
        Time_ = "";
        for (int i = 0; i < dt.length; i++) {
            if (dt[i] == ':') {
                break;
            } else {
                Time_ += dt[i];
            }
        }

        String AMPM = getAMPM(ampm);
        if (AMPM.equals("PM")) {
            try {
                int tmp_time = Integer.parseInt(Time_);
                tmp_time += 12;
                Time_ = tmp_time + "";
            } catch (Exception ex) {

            }
        }

        return Time_;
    }

    public String getHnr24(String Time_) {
        char[] dt = Time_.toCharArray();
        // String tmp="";
        Time_ = "";
        for (int i = 0; i < dt.length; i++) {
            if (dt[i] == ':') {
                break;
            } else {
                Time_ += dt[i];
            }
        }
        return Time_;
    }

    public String getMit(String Time_) {
        char[] dt = Time_.toCharArray();
        // String tmp="";
        Time_ = "";
        int c = 0;
        for (int i = 0; i < dt.length; i++) {
            if (dt[i] == ':' || dt[i] == ' ') {
                c += 1;
            } else {
                if (c == 1) {
                    Time_ += dt[i];
                }
            }
        }
        return Time_;
    }

    public String getAMPM(String Time_) {
        char[] dt = Time_.toCharArray();
        // String tmp="";
        Time_ = "";
        int c = 0;
        for (int i = 0; i < dt.length; i++) {
            if (dt[i] == ':' || dt[i] == ' ') {
                c += 1;
            } else {
                if (c == 2) {
                    Time_ += dt[i];
                }
            }
        }
        return Time_;
    }

    public int getCurrentYear() {
        Calendar cldr = Calendar.getInstance();
        return cldr.get(Calendar.YEAR);
    }

    public int getCurrentMonth() {
        Calendar cldr = Calendar.getInstance();
        return cldr.get(Calendar.MONTH);
    }

    public int getCurrentDate() {
        Calendar cldr = Calendar.getInstance();
        return cldr.get(Calendar.DAY_OF_MONTH);
    }

    public int CalculateBirthDayYer(String a, int mYear) {

        if (!a.equals("") && a.length() > 0) {
            try {
                int ag = Integer.parseInt(a);
                if (ag < mYear && ag <= 100) {
                    mYear = mYear - ag;
                }
            } catch (Exception ex) {

            }
        }
        return mYear;
    }

    public boolean isOddValue(int val) {
        if (val % 2 == 0)
            return false;
        else
            return true;
    }

    public boolean testEmailAddresses(String toEmailAddress) {
        boolean b = true;
        List<String> toEmailList = Arrays.asList(toEmailAddress
                .split("\\s*,\\s*"));
        for (int i = 0; i < toEmailList.size(); i++) {
            if (!Email_Validation(toEmailList.get(i))) {
                b = false;
                break;
            }
        }
        return b;
    }

    // Method for Report

    public int idFilter(int selector, String ID) {
        switch (selector) {
            case 1: {

                break;
            }
            case 2: {

                break;
            }
            case 3: {

                break;
            }
            case 4: {

                break;
            }
            case 5: {
                try {
                    selector = Integer.parseInt(ID.substring(2));
                    System.out.println("\n\t ****** Test\n" + "Val = " + selector
                            + ", ID:" + ID + " ******");
                } catch (Exception ex) {
                    System.out.println("\n\t ****** Test Exception\n"
                            + "Number exception " + ex.toString() + " ******");
                }

                break;
            }
            case 6: {

                break;
            }

            case 7: {

                break;
            }

            default:
                break;
        }
        return selector;
    }

    public String setTextColor(String m_name, int green) {
        final SpannableStringBuilder sb = new SpannableStringBuilder(m_name);
        final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.GREEN);

        // Span to set text color to some RGB value
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);

        // Span to make text bold
        sb.setSpan(fcs, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(bss, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return sb + "";
    }

    public String getYerCutID(String st7, int selecter) {

        try {
            List<String> toList = Arrays.asList(st7.split("\\s*,\\s*"));
            if (selecter == 0)
                st7 = toList.get(0);// return index
            else if (selecter == 1) {
                st7 = toList.get(1);// return year
            } else {
                st7 = "" + getCurrentYear();
            }

        } catch (Exception ex) {
            st7 = "" + getCurrentYear();
        }
        return st7;
    }

    public int getSpinnerValIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

    public Bitmap Download_image(String img_url) throws IOException {
        // URL url = new URL(img_url);
        HttpURLConnection connection = (HttpURLConnection) new URL(img_url)
                .openConnection();
        connection.setDoInput(true);
        connection.connect();
        // Emran_Picture emrPObj = new Emran_Picture();
        // emrPObj.resiz_PictureWH(BitmapFactory.decodeStream(connection.getInputStream()),
        // 150, 150);
        // InputStream input = connection.getInputStream();
        // Bitmap myBitmap = BitmapFactory.decodeStream(input);
        // return BitmapFactory.decodeStream(input);
        return BitmapFactory.decodeStream(connection.getInputStream());
        // return
        // emrPObj.resiz_PictureWH(BitmapFactory.decodeStream(connection.getInputStream()),
        // 150, 150);
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This Funcetion use for spli a string and return to a String List. I am
     * Md. Emran Hossain to build this function. Function build date: 2015/03/05
     * email: emranhossain.rip@gmail.com
     */
    public List<String> Emran_Spliter(String MainString, String Splitvalue) {
        List<String> StringList = null;
        try {
            StringList = Arrays.asList(MainString.split("\\s*" + Splitvalue
                    + "\\s*"));
        } catch (Exception ex) {
            return null;
        }
        return StringList;
    }

    public void CloseThisActivity() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        G_context.startActivity(startMain);
    }

	/*
     * Date: 2015/03/21 Subject: Create few method for FANFOOD project
	 * Programmer Name: Md. Emran Hossain Email: emranhossain.rip@gmail.com
	 * Phone: 01913439865
	 */

    /**
     * This Function use for convert String decimal to integer value with
     * increment 1.</br> <b>Input parameter:</b> String value in decimal digit
     * <i>[0 to 9 and -9 to 0]</i> <b>Return:</b> Return null when catch
     * unaccepted exception. Return integer value with one increment.
     */
    @SuppressWarnings("null")
    public int IncrementStringDigite(String s) {
        if (s != null && !s.equalsIgnoreCase("")) {
            try {
                return Integer.parseInt(s) + 1;
            } catch (Exception ex) {
                return (Integer) null;
            }
        } else
            return (Integer) null;
    }

    /**
     * This Function use for convert String decimal to integer value with
     * decrement 1.</br> <b>Input parameter:</b> String value in decimal digit
     * <i>[0 to 9 and -9 to 0]</i> <b>Return:</b> Return null when catch
     * unaccepted exception. Return integer value with one decrement.
     */
    @SuppressWarnings("null")
    public int DecrementStringDigite(String s) {
        if (s != null && !s.equalsIgnoreCase("")) {
            try {
                return Integer.parseInt(s) - 1;
            } catch (Exception ex) {
                return (Integer) null;
            }
        } else
            return (Integer) null;
    }

    /**
     * GPS
     */
    // ************************** GPS Function **************************
    public void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(G_context);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("GPS Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                G_context.startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
/*
 * Camara example:p http://www.tutorialspoint.com/android/android_camera.htm
 * http
 * ://androidexample.com/Camera_Photo_Capture_And_Show_Captured_Photo_On_Activity_
 * /index.php?view=article_discription&aid=77&aaid=101
 */