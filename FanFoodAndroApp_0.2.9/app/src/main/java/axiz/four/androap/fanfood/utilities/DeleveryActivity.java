package axiz.four.androap.fanfood.utilities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import axiz.four.androap.fanfood.R;

public class DeleveryActivity extends FragmentActivity {
    EditText etAdress;
    Button button1;
    RadioGroup deliveryRadioGroup;
    RadioButton rbExpress,rdDelivery;
    LinearLayout delivery_linearLayout;
    int flag=0;
    String method="0",message=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delevery);

        etAdress=(EditText)findViewById(R.id.etAdress);
        button1=(Button)findViewById(R.id.button1);
        deliveryRadioGroup=(RadioGroup) findViewById(R.id.radioButtonGroup);
        rbExpress=(RadioButton) findViewById(R.id.radioButtonExpress);
        rdDelivery=(RadioButton) findViewById(R.id.radioButtonDelivery);
        delivery_linearLayout=(LinearLayout) findViewById(R.id.delivery_linearLayout);
        //strat
        deliveryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                String name=rb.getText().toString();
                Log.e("Button name",name);
                if(name.equalsIgnoreCase("Delivery")){
                    delivery_linearLayout.setVisibility(View.VISIBLE);
                    method="1";
                    flag=1;
                    Toast.makeText(getApplicationContext(),"delivey "+method+"Flag value: "+flag,Toast.LENGTH_LONG).show();
                }
                if(name.equalsIgnoreCase("Express Pickup")){
                    delivery_linearLayout.setVisibility(View.INVISIBLE);
                    method="0";
                    message="";
                    flag=0;
                    Toast.makeText(getApplicationContext(),"Express "+method+"Flag value: "+flag,Toast.LENGTH_LONG).show();
                }
            }
        });
        //end

        /*rdDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                delivery_linearLayout.setVisibility(View.INVISIBLE);
                method="1";
                flag=1;
                Toast.makeText(getApplicationContext(),"delivey "+method+"Flag value: "+flag,Toast.LENGTH_LONG).show();
            }
        });
        rbExpress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                delivery_linearLayout.setVisibility(View.VISIBLE);
                method="0";
                message="";
                flag=0;
                Toast.makeText(getApplicationContext(),"Express "+method+"Flag value: "+flag,Toast.LENGTH_LONG).show();
            }
        });*/
    button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("flag invoked ",""+flag);
            if(flag==1){
                message=etAdress.getText().toString();
                Log.e("Message invoked ",message);
            }
            //Log.e("Result",method+message);
            Intent intent=new Intent();
            intent.putExtra("METHOD",method);
            intent.putExtra("MESSAGE",message);
            setResult(2,intent);
            finish();
        }
    });
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(getApplicationContext(),"back button pressed",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delevery, menu);
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
