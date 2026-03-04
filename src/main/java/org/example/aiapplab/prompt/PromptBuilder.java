package org.example.aiapplab.prompt;

public class PromptBuilder {

    public static String build(PromptTemplate template) {
        StringBuilder sb = new StringBuilder();

        sb.append("【角色】\n")
                .append(template.getRole()).append("\n\n");

        sb.append("【任务】\n")
                .append(template.getTask()).append("\n\n");

        if (template.getConstraints() != null && !template.getConstraints().isEmpty()) {
            sb.append("【约束】\n");
            int i = 1;
            for (String c : template.getConstraints()) {
                sb.append(i++).append(". ").append(c).append("\n");
            }
            sb.append("\n");
        }

        sb.append("【输出格式】\n")
                .append(template.getOutputFormat()).append("\n\n");

        if (template.getExample() != null) {
            sb.append("【示例】\n")
                    .append(template.getExample()).append("\n");
        }

        return sb.toString();
    }
}