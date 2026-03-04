package org.example.aiapplab.service;
import org.springframework.stereotype.Service;

@Service
public class LlmService {

    public String callModel(String prompt) {
        // 先假返回
        return """
        {
          "logicIssues": [],
          "performanceIssues": [],
          "securityRisks": [],
          "suggestions": ["示例返回"]
        }
        """;
    }
}
