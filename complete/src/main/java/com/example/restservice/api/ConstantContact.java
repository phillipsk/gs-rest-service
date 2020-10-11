package com.example.restservice.api;

import com.example.restservice.api.model.OAuthToken;
import com.example.restservice.reusable.Maintenance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;

public class ConstantContact extends Maintenance implements OAuth {
    public static final String JSON_FILE = "conf/cc_response.json";
    private final String localhost;
    private final String clientID;
    private final String clientSecret;
    private String authToken;
    private String authUrl;
    private String accessToken;
    private String refreshToken;



    public ConstantContact(Properties props) {
        loadJSONconfig(JSON_FILE);
//        this.loadProperties(props);
        this.localhost = props.getProperty("cc_localhost");
        this.clientID = props.getProperty("cc_api_key");
        this.clientSecret = props.getProperty("cc_client_secret");
    }

    static class Token extends OAuthToken {
        String access_token;
        String refresh_token;
        public Token(String access_token, String refresh_token) {
            super(access_token, refresh_token);

        }
    }


    @Override
    public void loadJSONconfig(String filename) {
        loadJSONFile(JSON_FILE, Token.class);

//        this.accessToken = json.getString("access_token");
//        this.refreshToken = json.getString("refresh_token");

    }

    /*
     * This method can be used to generate the URL an account owner would use to allow your app to access their account.
     * After visiting the URL, the account owner is prompted to log in and allow your app to access their account.
     * They are then redirected to your redirect URI with the authorization code appended as a query parameter.
     * e.g.: http://localhost:8080/?code={authorization_code}
     */

    /**
     * @return Full Authentication URL
     */
    public void getAuthenticationUrl() {
//https://api.cc.email/v3/idfed?client_id={your_client_id}&redirect_uri=https%3A%2F%2Flocalhost%3A8888&response_type=code&scope=contact_data+campaign_data

        // Create authorization URL
        StringBuilder authUrl = new StringBuilder()
                .append("https://api.cc.email/v3/idfed")
                .append("?client_id=" + this.clientID)
                .append("&response_type=code")
                .append("&scope=contact_data+campaign_data");
        setAuthUrl(authUrl.toString());
//        return authUrl.toString();
    }

    /*
     * This method can be used to exchange an authorization code for an access token.
     * Make this call by passing in the code present when the account owner is redirected back to you after authenticating.
     * The response will contain an 'access_token' and 'refresh_token' in a JSON object.
     */

    /**
     * @param initiateAuth Boolean to request initial Authentication
     */

    public void getToken(Boolean initiateAuth) throws IOException {

        StringBuilder authResult = new StringBuilder();

        // Make authorization header with API Key:API Secret and encode
        String credentials = this.clientID + ":" + this.clientSecret;
        String auth = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

        // Create request URL
        StringBuilder requestUrl = new StringBuilder();
        if (initiateAuth) {
            requestUrl
                    .append("https://idfed.constantcontact.com/as/token.oauth2")
                    .append("?code=")
                    .append(this.authToken)
                    //                .append("&redirect_uri=")
                    //                .append(redirectUri)
                    .append("&grant_type=authorization_code");
        } else {
            requestUrl
                    .append("https://idfed.constantcontact.com/as/token.oauth2")
                    .append("?refresh_token=")
                    .append(this.refreshToken)
                    .append("&grant_type=refresh_token");
        }


        URL authorizeUrl;
        authorizeUrl = new URL(requestUrl.toString());

        // Open connection
        HttpURLConnection con = (HttpURLConnection) authorizeUrl.openConnection();
        // Set Method
        con.setRequestMethod("POST");
        // Add Auth Header
        con.setRequestProperty("Authorization", auth);
        // Read response from server
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            authResult.append(inputLine);
        }
        // Close the stream
        in.close();

        setAuthToken(authResult.toString());
//        return authResult.toString();
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getClientID() {
        return clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getLocalhost() {
        return localhost;
    }
}
