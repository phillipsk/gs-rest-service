package com.example.restservice.api;

import java.io.IOException;

public interface OAuth {

    public void getAuthenticationUrl();
    public void getToken (Boolean initiateAuth) throws IOException;
//    public void loadProperties(Properties props);
    public void loadJSONconfig(String filename);
}

