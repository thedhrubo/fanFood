package axiz.four.androap.fanfood;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import axiz.four.androap.fanfood.emran_method.Emran_a;

/**
 * Created by emran on 4/6/15.
 */
public class Intro extends Activity {
    private Emran_a emrnAObj;
    private Activity mActivity;
    private Context mContext;

    private Button intro_btn_id_signin, intro_btn_id_register, intro_btn_id_getStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mActivity = (Activity)this;
        mContext = (Context)this;
        InitializeComponent();
        intro_btn_id_getStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                emrnAObj.setUseValue("User_ID", "0");
                startActivity(emrnAObj.Start_Activity(mContext, GetStarted.class));
            }
        });

        intro_btn_id_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(emrnAObj.Start_Activity(mContext, SignUp.class));// SignUp use as Register
            }
        });

        intro_btn_id_signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                emrnAObj.isFirst(1);
                emrnAObj.setUseValue("StayInLogin","OK");
                startActivity(emrnAObj.Start_Activity(mContext, MainLoginActivity.class));// SignUp use as Register
            }
        });

    }

    private void InitializeComponent() {
        emrnAObj = new Emran_a(mContext, mActivity);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "font/impact.ttf");
        intro_btn_id_signin = (Button) findViewById(R.id.intro_btn_id_signin);
        intro_btn_id_register = (Button) findViewById(R.id.intro_btn_id_register);
        intro_btn_id_getStart = (Button) findViewById(R.id.intro_btn_id_getStart);
        intro_btn_id_signin.setTypeface(tf);
        intro_btn_id_register.setTypeface(tf);
        intro_btn_id_getStart.setTypeface(tf);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            emrnAObj.CloseThisActivity();
        }
        return false;
    }

}
