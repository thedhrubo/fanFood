package axiz.four.androap.fanfood.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;
import axiz.four.androap.fanfood.drawerfeagmentclass.OrderHistory;
import axiz.four.androap.fanfood.emran_method.Emran_a;

/**
 * Created by emran on 10/26/15.
 */
public class LongOperation extends AsyncTask<String, Void, String> {
    String rowId,message,result;
    private static Emran_a emrnAObj;
    private ArrayAdapter<DataAbstractEvent> adapter;
    public  Context context;
    int position;
    private final String url="http://4axiz.com/fanfood/backend/api_fanfood/orderDisable";
    public LongOperation(String rowId, int position) {
        this.rowId=rowId;
        this.position=position;
    }

    @Override
    protected String doInBackground(String... params) {
        //Log.e("msg", "executed"+rowId+"  "+url);
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(url);
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        String responses = null;
        try {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("MACHINE_CODE", "emran4axiz"));
        nameValuePairs.add(new BasicNameValuePair("ORDER_ID", rowId));

           // Log.e("msg", "httpClient execute started"+rowId+"  "+httpResponse);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse=httpClient.execute(httpPost);
            //Log.e("msg", "httpClient executed"+rowId+"  "+httpResponse);
            httpEntity=httpResponse.getEntity();
            responses= EntityUtils.toString(httpEntity);
            //Log.e("msg", "httpClient executed"+rowId+"  "+responses);
            JSONObject jsValue= new JSONObject(responses);
            message=jsValue.getString("message");

            String s3=new String("success");
            if(message.equals(s3)){
                Log.e("message delete", message);
                result=message;
               // refreshData();
                //handler.send

            }else{
               // handler.sendEmptyMessage(1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        int before=OrderHistory.venue_adapter.getCount();
        OrderHistory.venue_adapter.notifyDataSetChanged();
        OrderHistory.listData2.remove(position);
        int after=OrderHistory.venue_adapter.getCount();
        Log.e("venue_adapter",""+before+" "+after);
        //Intent refreshIntent= new Intent(context.getApplicationContext(),OrderHistory.class);
        //context.startActivity(refreshIntent);
        //super.onPostExecute(s);
       // emrnAObj.alartBox("",message);
        //Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }

};


