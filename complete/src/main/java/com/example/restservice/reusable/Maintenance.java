package com.example.restservice.reusable;

import com.example.restservice.api.model.OAuthToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class Maintenance {

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

    public void loadJSONFile(String filename, Class<? extends OAuthToken> cls) {
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(filename);
//        TODO: Research JACKSON JSON ANNOTATION for mapping
        try {
            mapper.readValue(stream, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        InputStream stream = loader.getResourceAsStream(filename);
//        JSONParser jsonParser = new JSONParser();
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = (JSONObject) jsonParser.parse(
//                    new InputStreamReader(stream, StandardCharsets.UTF_8));
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }


    public static String GetJSONValue(String JSONString, String Field) {
        return JSONString.substring(JSONString.indexOf(Field),
                JSONString.indexOf("\n", JSONString.indexOf(Field)))
                .replace(Field + "\": \"", "")
                .replace("\"", "").replace(",", "");
    }

}
