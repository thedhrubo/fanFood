package axiz.four.androap.fanfood.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fida1989 on 4/23/15.
 */
public class Utils {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Email = "emailKey";
    public static final String MACHINE_CODE = "emran4axiz";

    public static String checkLogin(Context c) {
        SharedPreferences sharedpreferences = c.getSharedPreferences(Utils.MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences.getString("id", "").length() > 0) {
            //Toast.makeText(c, "Logged In ID = " + sharedpreferences.getString("id", ""), Toast.LENGTH_SHORT).show();
            return sharedpreferences.getString("id", "");
        } else {
            //Toast.makeText(c, "No Logged User", Toast.LENGTH_SHORT).show();
            return "";
        }
    }
}
