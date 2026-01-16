//package com.anant.helpdesk.ai.service;
//
//import com.anant.helpdesk.common.enums.TicketCategory;
//import com.anant.helpdesk.common.enums.TicketPriority;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class HuggingFaceTicketClassifier implements AiTicketClassifier {
//
//    private final WebClient webClient;
//
//    public HuggingFaceTicketClassifier(WebClient huggingFaceWebClient) {
//        this.webClient = huggingFaceWebClient;
//    }
//
//    @Override
//    public TicketCategory classifyCategory(String text) {
//        String[] labels = {
//                "LOGIN_ISSUE", "PAYMENT", "BUG", "FEATURE_REQUEST", "GENERAL"
//        };
//        return TicketCategory.valueOf(classify(text, labels));
//    }
//
//    @Override
//    public TicketPriority classifyPriority(String text) {
//        String[] labels = {
//                "LOW", "MEDIUM", "HIGH", "CRITICAL"
//        };
//        return TicketPriority.valueOf(classify(text, labels));
//    }
//
//    private String classify(String text, String[] labels) {
//
//        Map<String, Object> request = Map.of(
//                "inputs", text,
//                "parameters", Map.of(
//                        "candidate_labels", labels
//                )
//        );
//
//        Map response = webClient.post()
//                .bodyValue(request)
//                .retrieve()
//                .bodyToMono(Map.class)
//                .block();
//
//        List<String> labelList = (List<String>) response.get("labels");
//        return labelList.get(0); // highest confidence
//    }
//}
