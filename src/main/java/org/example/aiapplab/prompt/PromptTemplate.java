package org.example.aiapplab.prompt;
import lombok.Data;
import java.util.List;

@Data
public class PromptTemplate {

    private String role;
    private String task;
    private List<String> constraints;
    private String outputFormat;
    private String example;
}
