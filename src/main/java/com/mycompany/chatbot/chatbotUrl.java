/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatbot;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author sujit
 */



public class chatbotUrl {
private String key="AIzaSyCwwtOpu8nSK9Z9BV3TkjYDHDCKLoFDeD0";
    public String chatbotUrl1(String s) {
      try{
          URL u=new URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent");
          HttpURLConnection ucc=(HttpURLConnection)u.openConnection();
          ucc.setRequestMethod("POST");
          ucc.setRequestProperty("Content-type","application/json" );
          ucc.setRequestProperty("X-goog-api-key",key );
            ucc.setDoOutput(true);

            // 🧠 Request body
            String userMessage = s;
            String jsonInput = "{\n" +
                    "  \"contents\": [\n" +
                    "    {\n" +
                    "      \"parts\": [\n" +
                    "        { \"text\": \"" + userMessage + "\" }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
            OutputStream os=ucc.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();
            os.close();
            BufferedReader br=new BufferedReader(new InputStreamReader(ucc.getInputStream()));
            String sui;
            StringBuffer brr=new StringBuffer();
            
            while((sui=br.readLine())!=null){
                brr.append(sui);
                 
            }
            br.close();
          JsonElement ele=JsonParser.parseString(brr.toString());
          JsonObject obj=ele.getAsJsonObject();
          JsonArray arr=obj.getAsJsonArray("candidates");
          if (arr != null && arr.size() > 0) {
    JsonObject content = arr.get(0).getAsJsonObject()
                                   .getAsJsonObject("content");

    JsonArray parts = content.getAsJsonArray("parts");
    if (parts != null && parts.size() > 0) {
        String reply = parts.get(0).getAsJsonObject().get("text").getAsString();
        return reply;
    }
}
return "No reply found.";
           
          
      }
      catch(Exception e){
          return "Can't give responce.";
          
      }
  }
      
}