package axiz.four.androap.fanfood.serversite;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import axiz.four.androap.fanfood.data_abstraction.DataAbstractEvent;

/**
 * Created by emran on 4/13/15.
 * */

public class DownJsonToArray {
    private HttpPost httppost;
    private HttpResponse response;
    private StringBuffer buffer;
    private HttpClient httpclient;
    private String ServerResponce = "";
    private String AllItemList="";

    protected String getServerData(String APIName, List<NameValuePair> SendIndexValu, boolean isArray) {

        if((APIName==null || APIName.equals("") || APIName.length()<1)
                || SendIndexValu==null  ){
            ServerResponce = "Null value of API Name";
            return null;
        }
        else if(SendIndexValu==null){
            ServerResponce = "Null value of SendIndexValu";
            return null;
        }

        String result = "";
        InputStream isr = null;
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://" + APILinks.HOST_IP + "/"+ APILinks.API_PATH + "/"+ APIName);
            Log.e("SendIndexValu",SendIndexValu.toString());
            httppost.setEntity(new UrlEncodedFormEntity(SendIndexValu));
            HttpResponse responce = httpclient.execute(httppost);

            HttpEntity entity = responce.getEntity();
            isr = entity.getContent();
        } catch (Exception ex) {
            ServerResponce = ""+"Error in http connection " + ex.toString();
            return null;
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();
            result = sb.toString();
        } catch (Exception ex) {
//            Log.e("log_tag- search all user",
//                    "Error converting result " + ex.toString());
            ServerResponce = ""+"Error converting result " + ex.toString();
            return null;
        }
        return result;
        // return result;
    }// End function


    public ArrayList<DataAbstractEvent> getStadumList() {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_GET_ALL_VENUE;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("ZIPCODE", "-1"));
//
//        ArrayList<String> JSONCatchValu = new ArrayList<String>();
//        JSONCatchValu.add("message");
//        JSONCatchValu.add("error");
//        JSONCatchValu.add("success");
//        JSONCatchValu.add("stadiumInfo");

        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                JSONArray jArray = reader.getJSONArray("stadiumInfo");

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    DataAbstractEvent u = new DataAbstractEvent(
                            jObj.getString("STADIUM_NAME"),
                            jObj.getString("STADIUM_STATE"),
                            "",
                            jObj.getString("STADIUM_ADDRESS2"),
//                            jObj.getString("STADIUM_DESC"),
                            jObj.getString("STADIUM_ADDRESS1"),
                            jObj.getString("STADIUM_ZIP"),
                            jObj.getString("STADIUM_LONGITUDE"),
                            jObj.getString("STADIUM_LATITUDE"),
                            jObj.getString("STADIUM_ID"));
                    userInfoDdataqBase.add(u);
                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
            ServerResponce = ex.toString();
            return null;
        }
        return  userInfoDdataqBase;
    }

    public ArrayList<DataAbstractEvent> getConcession(String VenueID, String sqlConceID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_GET_CONCESSION;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("STADIUM_ID", VenueID));

        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
//            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                JSONArray jArray = reader.getJSONArray("concessionInfo");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    String isSqlConcetion = "N";
                    if(sqlConceID!=null && (sqlConceID.equals(jObj.getString("CONCESSION_ID")))){
                        isSqlConcetion = "Y";
                    }
                    DataAbstractEvent u = new DataAbstractEvent(
                            jObj.getString("CONCESSION_NAME"),
                            jObj.getString("BRAND_NAME"),
                            jObj.getString("CONCESSION_STAND_NO"),
                            jObj.getString("CONCESSION_USERNAME"),
                            jObj.getString("CONCESSION_ID"),
                            isSqlConcetion);
                    userInfoDdataqBase.add(u);
                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
            ServerResponce = ex.toString();
            return null;
        }
        return  userInfoDdataqBase;
    }

    public ArrayList<DataAbstractEvent> getConcessionFavorite(DataAbstractEvent VenueID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_GET_FAVORIT_CONCESSION;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("USER_ID", VenueID.getTxt1()));
        SendIndexValu.add(new BasicNameValuePair("STADIUM_ID", VenueID.getTxt2()));
        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
//            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                JSONArray jArray = reader.getJSONArray("concessionInfo");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    DataAbstractEvent u = new DataAbstractEvent(
                            jObj.getString("CONCESSION_NAME"),
                            jObj.getString("BRAND_NAME"),
                            jObj.getString("CONCESSION_STAND_NO"),
                            jObj.getString("CONCESSION_USERNAME"),
                            jObj.getString("CONCESSION_ID"));
                    userInfoDdataqBase.add(u);
                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
            ServerResponce = ex.toString();
            return null;
        }
        return  userInfoDdataqBase;
//        return null;
    }

    public ArrayList<DataAbstractEvent> getAllItems(DataAbstractEvent VenueID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_GET_ALLITEMS;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
//        SendIndexValu.add(new BasicNameValuePair("VENUENAME_ID", VenueID.getTxt1()));
        SendIndexValu.add(new BasicNameValuePair("CONCESSION_ID", VenueID.getTxt2()));
        SendIndexValu.add(new BasicNameValuePair("CATEGORY_ID", VenueID.getTxt3()));

        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
//            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                this.AllItemList = result;
                JSONArray jArray = reader.getJSONArray("itemInfo");
                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObj = jArray.getJSONObject(i);

                    DataAbstractEvent u = new DataAbstractEvent(
                            jObj.getString("MENU_ITEM_NAME"),
                            jObj.getString("MENU_ITEM_MESSAGE"),
                            "$"+jObj.getString("PRICE"),
                            jObj.getString("PRICE"),
                            jObj.getString("REVISED_PRICE"),
                            jObj.getString("MENU_CATEGORY_ID"),
                            jObj.getString("BRAND_ID"),
                            jObj.getString("CONCESSION_ID"),
                            jObj.getString("MODIFIED_DATE"),
                            jObj.getString("QTY"),
                            jObj.getString("IS_BRAND"),
                            jObj.getString("MENU_ITEM_ID"));
                    userInfoDdataqBase.add(u);
                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
            ServerResponce = ex.toString();
            return null;
        }
        return  userInfoDdataqBase;
    }

    public ArrayList<DataAbstractEvent> getAllItems2(DataAbstractEvent VenueID, ArrayList<String> orderItemList) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_GET_ALLITEMS;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
//        SendIndexValu.add(new BasicNameValuePair("VENUENAME_ID", VenueID.getTxt1()));
        SendIndexValu.add(new BasicNameValuePair("CONCESSION_ID", VenueID.getTxt2()));
        SendIndexValu.add(new BasicNameValuePair("CATEGORY_ID", VenueID.getTxt3()));

        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
//            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                this.AllItemList = result;
                JSONArray jArray = reader.getJSONArray("itemInfo");
                for (int i = 0; i < jArray.length(); i++) {
                    String thisItemOrderd = "N";
                    JSONObject jObj = jArray.getJSONObject(i);
                    if(orderItemList!=null && orderItemList.size()>0){
                        for(int j= 0; j<orderItemList.size(); j++){
                            if(jObj.getString("MENU_ITEM_ID").equals(orderItemList.get(j))){
                                orderItemList.remove(j);
                                thisItemOrderd = "Y";
                            }
                        }
                    }
                    DataAbstractEvent u = new DataAbstractEvent(
                            jObj.getString("MENU_ITEM_NAME"),
                            jObj.getString("MENU_ITEM_MESSAGE"),
                            "$"+jObj.getString("PRICE"),
                            jObj.getString("PRICE"),
                            jObj.getString("REVISED_PRICE"),
                            jObj.getString("MENU_CATEGORY_ID"),
                            jObj.getString("BRAND_ID"),
                            jObj.getString("CONCESSION_ID"),
                            jObj.getString("MODIFIED_DATE"),
                            jObj.getString("QTY"),
                            jObj.getString("IS_BRAND"),
                            jObj.getString("MENU_ITEM_ID"),
                            thisItemOrderd);
                    userInfoDdataqBase.add(u);
                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
            ServerResponce = ex.toString();
            return null;
        }
        return  userInfoDdataqBase;
    }

    public ArrayList<DataAbstractEvent> getCatagoryItem(String allItemsLiust, String ItemFor, ArrayList<String> orderItemList) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
//        String APIName = APILinks.API_GET_ALLITEMS;
//        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
//        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
////        SendIndexValu.add(new BasicNameValuePair("VENUENAME_ID", VenueID.getTxt1()));
//        SendIndexValu.add(new BasicNameValuePair("CONCESSION_ID", VenueID.getTxt2()));
//        SendIndexValu.add(new BasicNameValuePair("CATEGORY_ID", VenueID.getTxt3()));

        // -----------------------------------------------------
        try {
            String result = allItemsLiust;
//            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                this.AllItemList = result;
                JSONArray jArray = reader.getJSONArray("itemInfo");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    String thisItemOrderd = "N";
                    if(orderItemList!=null && orderItemList.size()>0){
                        for(int j= 0; j<orderItemList.size(); j++){
                            if(jObj.getString("MENU_ITEM_ID").equals(orderItemList.get(j))){
                                orderItemList.remove(j);
                                thisItemOrderd = "Y";
                            }
                        }
                    }
                    if(jObj.getString("MENU_CATEGORY_ID").equals(ItemFor) || ItemFor.equals("-1")){
                        DataAbstractEvent u = new DataAbstractEvent(
                                jObj.getString("MENU_ITEM_NAME"),
                                jObj.getString("MENU_ITEM_MESSAGE"),
                                "$"+jObj.getString("PRICE"),
                                jObj.getString("PRICE"),
                                jObj.getString("REVISED_PRICE"),
                                jObj.getString("MENU_CATEGORY_ID"),
                                jObj.getString("BRAND_ID"),
                                jObj.getString("CONCESSION_ID"),
                                jObj.getString("MODIFIED_DATE"),
                                jObj.getString("QTY"),
                                jObj.getString("IS_BRAND"),
                                jObj.getString("MENU_ITEM_ID"), thisItemOrderd);
                        userInfoDdataqBase.add(u);
                    }
                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
            ServerResponce = ex.toString();
            return null;
        }
        return  userInfoDdataqBase;
    }

    public ArrayList<DataAbstractEvent> setRegistration(DataAbstractEvent VenueID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_SET_REGISTRATION;
//        String APIName = "user_registration";
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("F_NAME", VenueID.getTxt1()));
        SendIndexValu.add(new BasicNameValuePair("L_NAME", VenueID.getTxt2()));
        SendIndexValu.add(new BasicNameValuePair("EMAIL", VenueID.getTxt3()));
        SendIndexValu.add(new BasicNameValuePair("PHONE_NO", VenueID.getTxt4()));
        SendIndexValu.add(new BasicNameValuePair("PASSWORD", VenueID.getTxt5()));

        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
//            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                String suc_status = reader.getString("status");
                DataAbstractEvent u = new DataAbstractEvent(suc_status);
                userInfoDdataqBase.add(u);
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce=reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
//            ServerResponce = ex.toString()+"\n"+ServerResponce;
            ServerResponce = ex.toString();
            return null;
        }
        return  userInfoDdataqBase;
    }


    public ArrayList<DataAbstractEvent> getUserInfo(DataAbstractEvent VenueID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_SET_USER_INFO_UPDATE;
//        String APIName = "user_registration";
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("USER_ID", VenueID.getTxt1()));
        SendIndexValu.add(new BasicNameValuePair("EMAIL", VenueID.getTxt2()));


        try {
            String result = getServerData(APIName, SendIndexValu, true);
//            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                ServerResponce = "Success";
                JSONArray jArray = reader.getJSONArray("userinfo");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    DataAbstractEvent u = new DataAbstractEvent(
                            jObj.getString("F_NAME"),
                            jObj.getString("L_NAME"),
                            jObj.getString("EMAIL"),
                            jObj.getString("PHONE_NO"),
                            jObj.getString("IPAY_ID"),
                            jObj.getString("GOOGLE_WALLET_ID"),
                            jObj.getString("FACEBOOK_ID"),
                            jObj.getString("IS_SOCIAL"),
                            jObj.getString("STATUS"),
                            jObj.getString("USER_ID"),
                            "");
                    userInfoDdataqBase.add(u);
                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce= reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
//            ServerResponce = ex.toString()+"\n"+ServerResponce;
            ServerResponce = ex.toString()+"\n"+ServerResponce;
            return null;
        }
        return  userInfoDdataqBase;
//        return null;
    }


    public ArrayList<DataAbstractEvent> setUpdateInfo(DataAbstractEvent VenueID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_SET_UPDATE_USER_INFO;
//        String APIName = "user_registration";
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("F_NAME", VenueID.getTxt1()));
        SendIndexValu.add(new BasicNameValuePair("L_NAME", VenueID.getTxt2()));
        SendIndexValu.add(new BasicNameValuePair("EMAIL", VenueID.getTxt3()));
        SendIndexValu.add(new BasicNameValuePair("PHONE_NO", VenueID.getTxt4()));
//        SendIndexValu.add(new BasicNameValuePair("PASSWORD", VenueID.getTxt5()));

        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
//            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                String suc_status = reader.getString("status");
                DataAbstractEvent u = new DataAbstractEvent(suc_status);
                userInfoDdataqBase.add(u);
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce=reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
//            ServerResponce = ex.toString()+"\n"+ServerResponce;
            ServerResponce = ex.toString();
            return null;
        }
        return  userInfoDdataqBase;
    }


    public ArrayList<DataAbstractEvent> setLogin(DataAbstractEvent VenueID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_SET_LOGIN;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("EMAIL", VenueID.getTxt1()));
        SendIndexValu.add(new BasicNameValuePair("PASSWORD", VenueID.getTxt2()));

        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                String suc_status = reader.getString("status");
                String User_ID = reader.getString("usre_id");
                DataAbstractEvent u = new DataAbstractEvent(suc_status, User_ID);
                userInfoDdataqBase.add(u);
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }else{
                    ServerResponce = "Login failed";
                }
//                else if (suc_msg.equals("status")) {
//                    ServerResponce = reader.getString("error");
//                }
                return null;
            }
        } catch (Exception ex) {
//            ServerResponce = ex.toString()+"\n"+ServerResponce;
            ServerResponce = ex.toString()+"\n"+ServerResponce;
            return null;
        }
        return  userInfoDdataqBase;
    }

    //account_reverification
    public ArrayList<DataAbstractEvent> setPasswdRecoverEmail(DataAbstractEvent VenueID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_PASSWORD_RECOVER_EMAIL;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("EMAIL", VenueID.getTxt1()));

        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                String suc_status = reader.getString("status");
                DataAbstractEvent u = new DataAbstractEvent(suc_status);
                userInfoDdataqBase.add(u);
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
//            ServerResponce = ex.toString()+"\n"+ServerResponce;
            ServerResponce = ex.toString()+"\n"+ServerResponce;
            return null;
        }
        return  userInfoDdataqBase;
    }


    //isCodeTrue
    public ArrayList<DataAbstractEvent> setVerifyCode(DataAbstractEvent VenueID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_SET_VERIFICATION_CODE;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("EMAIL", VenueID.getTxt1()));
        SendIndexValu.add(new BasicNameValuePair("CODE", VenueID.getTxt2()));


        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
//            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                String suc_status = reader.getString("status");
                DataAbstractEvent u = new DataAbstractEvent(suc_status);
                userInfoDdataqBase.add(u);
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
//            ServerResponce = ex.toString()+"\n"+ServerResponce;
            ServerResponce = ex.toString()+"\n"+ServerResponce;
            return null;
        }
        return  userInfoDdataqBase;
    }

    //isCodeTrue
    public ArrayList<DataAbstractEvent> setResetPassword(DataAbstractEvent VenueID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_RESETPASSWORD;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("EMAIL", VenueID.getTxt1()));
        SendIndexValu.add(new BasicNameValuePair("PASSWORD", VenueID.getTxt2()));

        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                String suc_status = reader.getString("status");
                DataAbstractEvent u = new DataAbstractEvent(suc_status);
                userInfoDdataqBase.add(u);
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
//            ServerResponce = ex.toString()+"\n"+ServerResponce;
            ServerResponce = ex.toString()+"\n"+ServerResponce;
            return null;
        }
        return  userInfoDdataqBase;
    }
    //------------------------
//    public ArrayList<DataAbstractEvent> sendOrder(DataAbstractEvent VenueID, String cardNo,String expDat) {
    public ArrayList<DataAbstractEvent> sendOrder(DataAbstractEvent VenueID,String cardName, String cardNo,String expDat, String cVV,String method,String message) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_ORDER_SUBMIT;
//        String APIName = APILinks.API_GET_ALL_VENUE;

        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("ORDER_ITEMS", VenueID.getTxt1()));
        SendIndexValu.add(new BasicNameValuePair("EXPIRY_DATE", expDat));
        SendIndexValu.add(new BasicNameValuePair("CARD_NAME", cardName));
        SendIndexValu.add(new BasicNameValuePair("CARD_NUMBER", cardNo));
        SendIndexValu.add(new BasicNameValuePair("CARD_CVV", cVV));
        SendIndexValu.add(new BasicNameValuePair("SHIPMENT_METHOD", method));
        SendIndexValu.add(new BasicNameValuePair("SHIPMENT_ADDRESS", message));
        Log.e("Api:",method+message+SendIndexValu);
//        SendIndexValu.add(new BasicNameValuePair("ZIPCODE", "-1"));

        try {
            String result = getServerData(APIName, SendIndexValu, true);
//            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            Log.e("response",result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                ServerResponce = "Success";
                JSONArray jArray = reader.getJSONArray("orderAck");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    DataAbstractEvent u = new DataAbstractEvent(
                            jObj.getString("CONCESSION_ID"),
                            jObj.getString("ORDER_ID"),
                            jObj.getString("ORDER_NUMBER"),
                            jObj.getString("REQUIRE_TIME"),
                            jObj.getString("MESSAGE"),
                            jObj.getString("DISCOUNT_MONEY"),
                            jObj.getString("TOTAL_PRICE"));
                    userInfoDdataqBase.add(u);
                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce= reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
//            ServerResponce = ex.toString()+"\n"+ServerResponce;
            ServerResponce = ex.toString()+"\n"+ServerResponce;
            return null;
        }
        return  userInfoDdataqBase;
//        return  null;
    }

    public ArrayList<DataAbstractEvent> getOrderHistory(DataAbstractEvent VenueID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_ORDER_HISTORY;
//        String APIName = APILinks.API_GET_ALL_VENUE;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("USER_ID", VenueID.getTxt1()));
//        SendIndexValu.add(new BasicNameValuePair("ZIPCODE", "-1"));

        try {
            String result = getServerData(APIName, SendIndexValu, true);
//            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            System.out.println(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                ServerResponce = "Success";
//                JSONArray jArray = reader.getJSONArray("orders");
                JSONArray jArray = reader.getJSONArray("orderInfo");
//                ServerResponce+="\n"+jArray.length();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    DataAbstractEvent u = new DataAbstractEvent(
                            ""+jObj.getString("order_number"),
                            "$"+jObj.getString("order_amount"),
                            jObj.getString("brand_name"),
                            jObj.getString("order_status"),
                            jObj.getString("order_net_amount"),
                            jObj.getString("concession_stand_no"),
                            jObj.getString("concession_id"),
                            jObj.getString("order_discount"),
                            jObj.getString("order_discount_in_percent"),

                            jObj.getString("stadium_name"),
                            jObj.getString("order_tax_in_percent"));
                    userInfoDdataqBase.add(u);
                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce= reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
//            ServerResponce = ex.toString()+"\n"+ServerResponce;
            ServerResponce = ex.toString()+"\n"+ServerResponce;
            return null;
        }
        return  userInfoDdataqBase;
//        return  null;
    }


    public ArrayList<DataAbstractEvent> getNotification() {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_ORDER_NOTIFICATION;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
//        SendIndexValu.add(new BasicNameValuePair("STADIUM_ID", User_ID));

        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                JSONArray jArray = reader.getJSONArray("orderNotification");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    DataAbstractEvent u = new DataAbstractEvent(
                            jObj.getString("TITLE"),
                            jObj.getString("MESSAGE"),
                            "",
                            jObj.getString("CONCESSION_STAND_NAME"),
                            jObj.getString("NOTIFICATION_DATE"),
                            jObj.getString("NOTIFICATION_TIME"),
                            jObj.getString("ID"));
                    userInfoDdataqBase.add(u);
                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
            ServerResponce = ex.toString();
            return null;
        }
        return  userInfoDdataqBase;
//        return null;
    }

    public ArrayList<DataAbstractEvent> getFAQ() {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_ORDER_FAQ;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
//        SendIndexValu.add(new BasicNameValuePair("STADIUM_ID", User_ID));

        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                JSONArray jArray = reader.getJSONArray("orderFaq");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    DataAbstractEvent u = new DataAbstractEvent(
                            jObj.getString("TITLE"),
                            jObj.getString("MESSAGE"),
                            "",
                            jObj.getString("FAQ_DATE"),
                            jObj.getString("FAQ_TIME"),
                            jObj.getString("ID"));
                    userInfoDdataqBase.add(u);
                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
            ServerResponce = ex.toString();
            return null;
        }
        return  userInfoDdataqBase;
//        return null;
    }

    public ArrayList<DataAbstractEvent> getFavoriteByUser(DataAbstractEvent VenueID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_FAVORITE;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("USER_ID", VenueID.getTxt1()));
        SendIndexValu.add(new BasicNameValuePair("STADIUM_ID", VenueID.getTxt2()));

        // -----------------------------------------------------
        try {
            String result = getServerData(APIName, SendIndexValu, true);
            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                JSONArray jArray = reader.getJSONArray("fevoriteItems");

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObj = jArray.getJSONObject(i);
                    DataAbstractEvent u = new DataAbstractEvent(
                            jObj.getString("MENU_ITEM_NAME"),
                            jObj.getString("MENU_ITEM_PRICE"),
                            "$"+jObj.getString("MENU_ITEM_PRICE"),
                            jObj.getString("MENU_ITEM_PRICE"),
                            jObj.getString("CONCESSION_STAND_NO"),
                            jObj.getString("MENU_CATEGORY_ID"),
                            jObj.getString("CONCESSION_ID"));
                    userInfoDdataqBase.add(u);
                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce = reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
            ServerResponce = ex.toString();
            return null;
        }
//        return  userInfoDdataqBase;
        return null;
    }

    //-------------------------------------------------------------------
    public ArrayList<DataAbstractEvent> setFavoriteByUse(DataAbstractEvent VenueID) {
        ArrayList<DataAbstractEvent> userInfoDdataqBase = new ArrayList<DataAbstractEvent>();
        String APIName = APILinks.API_ADDTO_FAVORITE;
//      String APIName = APILinks.API_GET_ALL_VENUE;
        List<NameValuePair> SendIndexValu = new ArrayList<NameValuePair>(1);
        SendIndexValu.add(new BasicNameValuePair("MACHINE_CODE", APILinks.ACCESS_CODE));
        SendIndexValu.add(new BasicNameValuePair("item_id", VenueID.getTxt1()));
        SendIndexValu.add(new BasicNameValuePair("user_id", VenueID.getTxt2()));
        SendIndexValu.add(new BasicNameValuePair("concession_id", VenueID.getTxt3()));
        SendIndexValu.add(new BasicNameValuePair("stadium_id", VenueID.getTxt4()));
//        SendIndexValu.add(new BasicNameValuePair("ZIPCODE", "-1"));

        try {
            String result = getServerData(APIName, SendIndexValu, true);
//            ServerResponce = result;
//            return null;
            JSONObject reader = new JSONObject(result);
            System.out.println(result);
            String suc_msg = reader.getString("message");
            if (suc_msg.equals("success")) {
                String suc_status = reader.getString("status");
                ServerResponce = "Success";
                DataAbstractEvent u = new DataAbstractEvent(suc_status);
                userInfoDdataqBase.add(u);
//                JSONArray jArray = reader.getJSONArray("orders");
//                JSONArray jArray = reader.getJSONArray("orderInfo");
////                ServerResponce+="\n"+jArray.length();
//                for (int i = 0; i < jArray.length(); i++) {
//                    JSONObject jObj = jArray.getJSONObject(i);
//                    DataAbstractEvent u = new DataAbstractEvent(
//                            ""+jObj.getString("order_number"),
//                            "$"+jObj.getString("order_amount"),
//                            jObj.getString("brand_name"),
//                            jObj.getString("order_status"),
//                            jObj.getString("order_net_amount"),
//                            jObj.getString("concession_stand_no"),
//                            jObj.getString("concession_id"),
//                            jObj.getString("order_discount"),
//                            jObj.getString("order_discount_in_percent"),
//
//                            jObj.getString("stadium_name"),
//                            jObj.getString("order_tax_in_percent"));
//                    userInfoDdataqBase.add(u);
//                }
            } else {
                if (suc_msg.equals("failed")) {
                    ServerResponce= reader.getString("error");
                }
                return null;
            }
        } catch (Exception ex) {
//            ServerResponce = ex.toString()+"\n"+ServerResponce;
            ServerResponce = ex.toString()+"\n"+ServerResponce;
            return null;
        }
        return  userInfoDdataqBase;
//        return  null;
    }

    public String getAllItemList(){ return this.AllItemList; }
    public String getServerResponce(){
        return ServerResponce;
    }
}
