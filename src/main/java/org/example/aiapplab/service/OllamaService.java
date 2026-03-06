package org.example.aiapplab.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Service
public class OllamaService {

    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private static final String MODEL = "qwen2.5:14b";

    /**
     * 流式调用 Ollama
     */
    public void streamChat(String prompt, Consumer<String> callback) {

        try {

            URL url = new URL(OLLAMA_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // 请求体
            JSONObject body = new JSONObject();
            body.put("model", MODEL);
            body.put("prompt", prompt);
            body.put("stream", true);

            // 发送请求
            OutputStream os = conn.getOutputStream();
            os.write(body.toString().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            // 读取返回流（重点：UTF-8）
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
            );

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                JSONObject json = new JSONObject(line);

                String text = json.optString("response");

                if (callback != null && text != null) {
                    callback.accept(text);
                }
            }

            reader.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}