package axiz.four.androap.fanfood;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import axiz.four.androap.fanfood.emran_method.Emran_a;

/**
 * Created by emran on 4/18/15.
 */
public class FoodCatalog extends Activity {
    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    private TextView fdctroy_toptextView1Title1, fdctroy_txtBottomTitle, fdctroy_Count;
    private ListView fdctroy_listView1;

    private String CurrentMessage = "", CurrentAlartTitle = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodcatalog);
        mActivity = (Activity) this;
        mContext = (Context) this;
        // ****************************************************************
        // ******************** Initialize components *********************
        InitializeComponent();
        // ############################################################

        // ********************* Set Venue name on footer text********
        // emrnAObj.alartBox("",emrnAObj.getUseValue("VENUENAME"));
        try {
            fdctroy_txtBottomTitle
                    .setText(emrnAObj.getUseValue("CONCESSIONnAME")+"");
            fdctroy_Count.setText(emrnAObj.getUseValue("CONCESSIO_ID")+"");
        } catch (Exception ex) {
            fdctroy_txtBottomTitle.setText("");
        }
        // ################################################################

    }// end oncreat method

    private void InitializeComponent() {
        emrnAObj = new Emran_a(mContext, mActivity);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
                "font/impact.ttf");
        Typeface tf2 = Typeface.createFromAsset(mContext.getAssets(),
                "font/open_sans_regular.ttf");

        fdctroy_toptextView1Title1 = (TextView) findViewById(R.id.fdctroy_toptextView1Title1);
        fdctroy_txtBottomTitle = (TextView) findViewById(R.id.fdctroy_txtBottomTitle);
        fdctroy_Count= (TextView) findViewById(R.id.fdctroy_Count);
        fdctroy_toptextView1Title1.setTypeface(tf);
        fdctroy_txtBottomTitle.setTypeface(tf);
        fdctroy_Count.setTypeface(tf2);

        fdctroy_listView1 = (ListView) findViewById(R.id.fdctroy_listView1);

    }
}
