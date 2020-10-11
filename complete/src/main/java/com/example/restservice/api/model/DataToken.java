package com.example.restservice.api.model;

import java.io.InputStream;

public class DataToken {

    public static class API_OAuth {
        private String accessToken;
        private String refreshToken;

        public API_OAuth(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        private void loadJSON(String filename){
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream(filename);
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setAccessToken(String accessToken){
            this.accessToken = accessToken;
        }

        public void setRefreshToken(String refreshToken){
            this.refreshToken = refreshToken;
        }
    }

}
