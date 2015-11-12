package axiz.four.androap.fanfood;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import axiz.four.androap.fanfood.emran_method.Emran_a;

/**
 * Created by emran on 4/7/15.
 */
public class Payment  extends Activity {
    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    private TextView payment_txt_id_signup;
    private EditText payment_txt_cardNo, payment_txt_mm, payment_txt_yy,
            payment_txt_cvv;
    private AutoCompleteTextView payment_txt_country;
    private Button payment_btn_id_register;
    private String CurrentMessage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);
        // ****************************************************************
        // ******************** Initialize components *********************
        mActivity = (Activity) this;
        mContext = (Context) this;
        InitializeComponent();
        // ################################################################
        //******************** Initialize Country name ********************
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.countries_array,R.layout.dropdown_singletext);

        payment_txt_country.setAdapter(adapter);

        // ################################################################

        // ****************************************************************
        // ******************** Button action activity ********************
        payment_btn_id_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(getValue()){
                    emrnAObj.isFirst(1);
                    Toast.makeText(mContext,"Registration complete",Toast.LENGTH_SHORT).show();
//                    startActivity(emrnAObj.Start_Activity(mContext, MainLoginActivity.class));
                    String User_ID = emrnAObj.getUseValue("User_ID");
                    if(User_ID.equals("0")){// 0 is as a guast user
                        finish();
                        Toast.makeText(mContext,"Please login again",Toast.LENGTH_SHORT).show();
                        startActivity(emrnAObj.Start_Activity(mContext, MainLoginActivity.class));
                    }
                    finish();
                }
            }
        });
        // ################################################################
    }

    private boolean getValue(){
//        payment_txt_mm, payment_txt_yy,
//                payment_txt_cvv
        String cardNo = payment_txt_cardNo.getText().toString().trim();
        String mm = payment_txt_mm.getText().toString().trim();
        String yy  = payment_txt_yy.getText().toString().trim();
        String cVV  = payment_txt_cvv.getText().toString().trim();
        String country  = payment_txt_country.getText().toString().trim();

        boolean b=true;
        if(!emrnAObj.isName(cardNo)){
            b=false;
            CurrentMessage+="Please enter correct card number" + "\n";
        }

        if(b && !emrnAObj.isName(mm)){
            b=false;
            CurrentMessage+="Enter month (06 for June)"+ "\n";
        }


        if(b && !emrnAObj.isName(yy)){
            b=false;
            CurrentMessage+="Enter year (15 for 2015)" + "\n";
        }

        if(b && !emrnAObj.isName(cVV)){
            b=false;
            CurrentMessage+="Enter CVV" + "\n";
        }

        if(b){
            try{
                int m = Integer.parseInt(mm);
                if(m<10){
                    mm="0"+m;
                }
                emrnAObj.setUseValue("EXPIRY_DATE",mm+yy);
                emrnAObj.setUseValue("CARD_NUMBER",cardNo);
                emrnAObj.setUseValue("CARD_CVV",cVV);
                if(country!=null && country.length()>0){
                    emrnAObj.setUseValue("CARD_COUNTRY",country);
                }
            }catch(Exception ex){
                System.out.println("###: "+ex.toString());
                b=false;
            }
        }
        return b;
    }

    private void InitializeComponent() {
        emrnAObj = new Emran_a(mContext, mActivity);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
                "font/impact.ttf");
        Typeface tf2 = Typeface.createFromAsset(mContext.getAssets(),
                "font/open_sans_regular.ttf");
        payment_txt_id_signup = (TextView) findViewById(R.id.payment_txt_id_signup);

        //text1= (TextView) findViewById(android.R.id.text1);
        payment_txt_id_signup.setTypeface(tf);
//		text1.setTypeface(tf2);

        payment_txt_cardNo = (EditText) findViewById(R.id.payment_txt_cardNo);
        payment_txt_mm = (EditText) findViewById(R.id.payment_txt_mm);
        payment_txt_yy = (EditText) findViewById(R.id.payment_txt_yy);
        payment_txt_cvv = (EditText) findViewById(R.id.payment_txt_cvv);

        payment_txt_cardNo.setTypeface(tf2);
        payment_txt_mm.setTypeface(tf2);
        payment_txt_yy.setTypeface(tf2);
        payment_txt_cvv.setTypeface(tf2);

        payment_txt_country = (AutoCompleteTextView) findViewById(R.id.payment_txt_country);
        payment_txt_country.setTypeface(tf2);

        payment_btn_id_register = (Button) findViewById(R.id.payment_btn_id_register);
        payment_btn_id_register.setTypeface(tf);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            String RegDone = emrnAObj.getUseValue("RegDone");
            if(RegDone!=null && RegDone.equals("YES")){
                finish();
                //Toast.makeText(mContext,"Please login again",Toast.LENGTH_SHORT).show();
                startActivity(emrnAObj.Start_Activity(mContext, MainLoginActivity.class));

            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
