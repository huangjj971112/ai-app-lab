package org.example.aiapplab.parser;

import lombok.Data;
import java.util.List;

@Data
public class CodeReviewResult {

    private List<String> logicIssues;
    private List<String> performanceIssues;
    private List<String> securityRisks;
    private List<String> suggestions;

}