package axiz.four.androap.fanfood;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class NotificationBoardActivity extends ActionBarActivity {
    TextView tvNoticeBord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_board);
        tvNoticeBord= (TextView) findViewById(R.id.tvNoticeBord);
        //Intent intent = getIntent();
        //String msgDetails=intent.getStringExtra("msgBody");
        //tvNoticeBord.setText(msgDetails);
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            try {
                JSONObject jsonObject = new JSONObject(mBundle.getString("com.parse.Data"));
                String data=jsonObject.getString("alert");
                Log.e("alert", data);
                tvNoticeBord.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
