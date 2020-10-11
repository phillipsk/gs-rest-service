package email.kevinphillips.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;
import java.util.stream.Collectors;

public class CtctRefreshToken {

    public String localhost, api_key, client_secret;

    private CtctRefreshToken() {
        Properties props = new Properties();
        InputStream in = getClass().getResourceAsStream("/config.properties");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            props.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        localhost = props.getProperty("cc_localhost");
        api_key = props.getProperty("cc_api_key");
        client_secret = props.getProperty("cc_client_secret");
    }

    private static class CCgetcampaign {
//        Properties props = new Properties();
//
//        InputStream in = getClass().getResourceAsStream("/config.properties");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//        props
//        props
        /*
         * This method can be used to generate the URL an account owner would use to allow your app to access their account.
         * After visiting the URL, the account owner is prompted to log in and allow your app to access their account.
         * They are then redirected to your redirect URI with the authorization code appended as a query parameter.
         * e.g.: http://localhost:8080/?code={authorization_code}
         */

        /**
         * @param redirectUri URL Encoded Redirect URI
         * @param clientId    API Key
         * @return Full Authentication URL
         */
        public String getAuthenticationUrl(String redirectUri, String clientId) {
//https://api.cc.email/v3/idfed?client_id={your_client_id}&redirect_uri=https%3A%2F%2Flocalhost%3A8888&response_type=code&scope=contact_data+campaign_data

            // Create authorization URL
            StringBuilder authUrl = new StringBuilder()
                    .append("https://api.cc.email/v3/idfed")
                    .append("?client_id=" + clientId)
                    .append("&response_type=code")
                    .append("&scope=contact_data+campaign_data");
//                .append("&redirect_uri="+ redirectUri);
//                .append(redirectUri);

            return authUrl.toString();
        }

        /*
         * This method can be used to exchange an authorization code for an access token.
         * Make this call by passing in the code present when the account owner is redirected back to you after authenticating.
         * The response will contain an 'access_token' and 'refresh_token' in a JSON object.
         */

        /**
         * @param redirectUri       URL Encoded Redirect URI
         * @param clientId          API Key
         * @param clientSecret      API Secret
         * @param authOrRefreshCode Authorization Code
         * @param initiateAuth      Boolean to request initial Authentication
         * @return JSON string containing an access_token and a refresh_token
         */

        public String getToken(String redirectUri, String clientId, String clientSecret, String authOrRefreshCode,
                               Boolean initiateAuth)
                throws IOException {

            StringBuilder authResult = new StringBuilder();

            // Make authorization header with API Key:API Secret and encode
            String credentials = clientId + ":" + clientSecret;
            String auth = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

            // Create request URL
            StringBuilder requestUrl = new StringBuilder();
            if (initiateAuth) {
                requestUrl
                        .append("https://idfed.constantcontact.com/as/token.oauth2")
                        .append("?code=")
                        .append(authOrRefreshCode)
                        //                .append("&redirect_uri=")
                        //                .append(redirectUri)
                        .append("&grant_type=authorization_code");
            } else {
                requestUrl
                        .append("https://idfed.constantcontact.com/as/token.oauth2")
                        .append("?refresh_token=")
                        .append(authOrRefreshCode)
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

            return authResult.toString();
        }

        public void saveToFile(String data, String filename) {
//        String fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            try (
                    FileWriter fw = new FileWriter(filename)) {
//                FileWriter fw = new FileWriter(filename + "_" + fileSuffix)) {
                fw.write(data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public String readJsonKeyFromFile(String key, String filename) {
            String value = "";
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader(filename));
                JSONObject jsonObject = (JSONObject) obj;
                value = (String) jsonObject.get(key);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

//            public static String GetJSONValue (String JSONString, String Field){
//                return JSONString.substring(JSONString.indexOf(Field),
//                        JSONString.indexOf("\n", JSONString.indexOf(Field)))
//                        .replace(Field + "\": \"", "")
//                        .replace("\"", "").replace(",", "");
//            }

            return value;
        }

        public String readJsonKeyFromFile(String key, File filename) {
            String value = "";
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader(filename));
                JSONObject jsonObject = (JSONObject) obj;
                value = (String) jsonObject.get(key);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

//            public static String GetJSONValue (String JSONString, String Field){
//                return JSONString.substring(JSONString.indexOf(Field),
//                        JSONString.indexOf("\n", JSONString.indexOf(Field)))
//                        .replace(Field + "\": \"", "")
//                        .replace("\"", "").replace(",", "");
//            }

            return value;
        }

        public String getResourceFileAsString(String fileName) throws IOException {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            try (InputStream is = classLoader.getResourceAsStream(fileName)) {
                if (is == null) return null;
                try (InputStreamReader isr = new InputStreamReader(is);
                     BufferedReader reader = new BufferedReader(isr)) {
                    return reader.lines().collect(Collectors.joining(System.lineSeparator()));
                }
            }
        }
    }




    public static void main(String[] args) {
        CtctRefreshToken ct = new CtctRefreshToken();
        CCgetcampaign cc = new CCgetcampaign();


        JSONParser parser = new JSONParser();
        String refreshToken;
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        final String filename = "android-server-side/src/main/resources/conf/cc_response.json";
        refreshToken = cc.readJsonKeyFromFile("refresh_token", filename);
        String request = "";
        try {
            request = cc.getToken(ct.localhost, ct.api_key, ct.client_secret, refreshToken, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(request);
        cc.saveToFile(request, filename);

        new JSONObject();
        JSONObject obj;
        try {
            obj = (JSONObject) parser.parse(request);
            refreshToken = (String) obj.get("refresh_token");
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println(refreshToken);
    }

}

