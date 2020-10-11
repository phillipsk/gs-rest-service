package com.example.restservice.api;

import com.example.restservice.api.model.DataToken;
//import org.json.JSONException;
//import org.json.JSONObject;

import java.io.IOException;

public class OAuthRefresh {


    //    public void refreshToken(Class<?> cls,
    public void refreshToken(ConstantContact constantContact,
                             DataToken.API_OAuth apiOAuth) {
//        if (cls == String.class)
        ConstantContact cc = constantContact;
        String request = "";
        System.out.println("Before = " + apiOAuth.getRefreshToken());
        try {
            cc.getToken(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        JSONObject obj = new JSONObject();
//        try {
//            obj = new JSONObject(request);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            apiOAuth.setAccessToken(obj.getString("access_token"));
//            apiOAuth.setRefreshToken(obj.getString("refresh_token"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        System.out.println("After = " + apiOAuth.getRefreshToken());

    }

}
