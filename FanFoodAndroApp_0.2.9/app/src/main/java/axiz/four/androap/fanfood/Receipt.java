package axiz.four.androap.fanfood;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import axiz.four.androap.fanfood.emran_method.Emran_a;

public class Receipt extends Activity {
	
	private ActionBar actionBar;
	private Emran_a emrnAObj;
	private Activity mActivity;
	private Context mContext;

    private TextView receipt_orderId, receipt_ordrTime, receipt_urMessage, receipt_totalMony;

    private String OrderNumber;
    private String OrderTime;
    private String OrderMessage;
    private String OrderTotalPrice;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receipt);

        mActivity = (Activity)this;
        mContext = (Context)this;
        emrnAObj = new Emran_a(mContext, mActivity);

        InitializeComponent();

        setVelue();
	}

    public void backToConcession(View view) {
        Intent backToConcessionIntent=new Intent(Receipt.this,GetStarted.class);
        startActivity(backToConcessionIntent);
    }


    private void InitializeComponent() {
//        emrnAObj = new Emran_a(mContext, mActivity);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "font/impact.ttf");
        Typeface tf2 = Typeface.createFromAsset(mContext.getAssets(), "font/open_sans_regular.ttf");

        receipt_orderId = (TextView) findViewById(R.id.receipt_orderId);
        receipt_ordrTime = (TextView) findViewById(R.id.receipt_ordrTime);
        receipt_urMessage = (TextView) findViewById(R.id.receipt_urMessage);
        receipt_totalMony = (TextView) findViewById(R.id.receipt_totalMony);

        receipt_orderId.setTypeface(tf);
        receipt_totalMony.setTypeface(tf);
    }

    private void setVelue() {
        try{
            //receipt_orderId, receipt_ordrTime, receipt_urMessage, receipt_totalMony
            Log.e("Recept :",emrnAObj.getUseValue("OrderNumber"));
            receipt_orderId.setText(emrnAObj.getUseValue("OrderNumber"));
            receipt_ordrTime.setText(emrnAObj.getUseValue("OrderTime"));
//          receipt_urMessage.setText(emrnAObj.getUseValue("OrderMessage"));
            receipt_totalMony.setText("TOTAL: "+emrnAObj.getUseValue("OrderTotalPrice"));
        }catch(Exception ex){

        }
    }

}
