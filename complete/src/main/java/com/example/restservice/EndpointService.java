package com.example.restservice;

import com.example.restservice.api.ConstantContact;
import com.example.restservice.api.OAuthRefresh;
import com.example.restservice.api.model.DataToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EndpointService {
    public static final String CONFIG_FILE = "conf/config.properties";

    private static final String temp_access_token = "fb78MFqjeHF8DqoxUF38O1l1nuR4";
    private static final String temp_refresh_token = "mU3z1z5rsK00tUsueJMe6C2egoDYYfL261yTktsy9M";

    public static void main(String[] args) {
        EndpointService es = new EndpointService();
        Properties props = new Properties();
        es.loadProperties(CONFIG_FILE, props);

//        File configFile = new File("conf/config.properties");
        ConstantContact cc = new ConstantContact(props);

        OAuthRefresh oa = new OAuthRefresh();
        //        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
//                oa::sendRequest, 0, 3, TimeUnit.SECONDS);

        DataToken.API_OAuth apiOAuth = new DataToken.API_OAuth(temp_access_token, temp_refresh_token);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> oa.refreshToken(cc, apiOAuth), 0, 10, TimeUnit.SECONDS);
//        oa.refreshToken(apiOAuth);
    }

    private Properties loadProperties(String filename, Properties props) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(filename);
        try {
            props.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

//    private JSONObject loadJSON(String filename, JSONObject jsonObject){
//
//    }
}