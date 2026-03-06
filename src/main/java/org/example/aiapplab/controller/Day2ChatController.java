package org.example.aiapplab.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aiapplab.dto.ChatRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/day2")
public class Day2ChatController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/chat")
    public Map<String,String> chat(@RequestBody ChatRequest request) throws JsonProcessingException {

        String question = request.getQuestion();

        String prompt = "请用简洁易懂的方式回答：" + question;

        Map<String,Object> req = new HashMap<>();
        req.put("model","qwen2.5:14b");
        req.put("prompt",prompt);
        req.put("stream",false);

        String result = restTemplate.postForObject(
                "http://localhost:11434/api/generate",
                req,
                String.class
        );


        // 解析 JSON
        JsonNode jsonNode = objectMapper.readTree(result);
        String answer = jsonNode.get("response").asText();

        Map<String,String> response = new HashMap<>();
        response.put("question",question);
        response.put("answer",answer);

        return response;
    }
}