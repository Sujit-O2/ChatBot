package com.mycompany.chatbot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;

public class chatbotUrl {
    private String key = "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";

    public String chatbotStream(String userMessage, JTextArea area) {
        String test="";
        try {
            URI u = URI.create("https://openrouter.ai/api/v1/chat/completions");
            HttpURLConnection ucc = (HttpURLConnection) u.toURL().openConnection();
            ucc.setRequestMethod("POST");
            ucc.setRequestProperty("Content-Type", "application/json");
            ucc.setRequestProperty("Authorization", "Bearer " + key);
            ucc.setRequestProperty("HTTP-Referer", "http://localhost");
            ucc.setRequestProperty("X-Title", "Java Chatbot");
            ucc.setDoOutput(true);

            // ✅ Request with streaming enabled
            String jsonInputString = "{"
                    + "\"model\": \"deepseek/deepseek-chat\","
                    + "\"stream\": true,"
                    + "\"messages\": [{\"role\": \"user\", \"content\": \"" +userMessage + "\"}]"
                    + "}";

            try (OutputStream os = ucc.getOutputStream()) {
                os.write(jsonInputString.getBytes("UTF-8"));
            }
            

            BufferedReader br = new BufferedReader(new InputStreamReader(ucc.getInputStream(), "UTF-8"));
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("data: ")) {
                    String json = line.substring(6).trim();
                    if (json.equals("[DONE]")) break;

                    try {
                        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
                        JsonArray choices = obj.getAsJsonArray("choices");
                        if (choices != null && choices.size() > 0) {
                            JsonObject delta = choices.get(0).getAsJsonObject().getAsJsonObject("delta");
                            if (delta != null && delta.has("content")) {
                                String chunk = delta.get("content").getAsString();
                                test=chunk;
                                

                                SwingUtilities.invokeLater(() -> area.append(chunk));
                                
                            }
                        }
                        
                    } catch (Exception ex) {
                        System.out.println("Skipping malformed: " + line);
                    }
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> area.append("\n⚠️ Error: " + e.getMessage()));
        }
        return test;
    }
}
