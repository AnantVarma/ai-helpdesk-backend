//package com.anant.helpdesk.ai.service;
//
//import com.anant.helpdesk.common.enums.TicketCategory;
//import com.anant.helpdesk.common.enums.TicketPriority;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.beans.factory.annotation.Value;
//
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class GeminiTicketClassifier implements AiTicketClassifier {
//
//    private final WebClient webClient;
//    private final String apiKey;
//
//    public GeminiTicketClassifier(WebClient geminiWebClient,
//                                  @Value("${gemini.api.key}") String apiKey) {
//        this.webClient = geminiWebClient;
//        this.apiKey = apiKey;
//    }
//
//    @Override
//    public TicketCategory classifyCategory(String text) {
//        String prompt = """
//        Classify this helpdesk ticket into one of these:
//        LOGIN_ISSUE, PAYMENT, BUG, FEATURE_REQUEST, GENERAL
//
//        Return ONLY the category name.
//
//        Ticket:
//        """ + text;
//
//        String response = callGemini(prompt);
//        return TicketCategory.valueOf(response.trim());
//    }
//
//    @Override
//    public TicketPriority classifyPriority(String text) {
//        String prompt = """
//        Classify the priority of this helpdesk ticket:
//        LOW, MEDIUM, HIGH, CRITICAL
//
//        Return ONLY one value.
//
//        Ticket:
//        """ + text;
//
//        String response = callGemini(prompt);
//        System.out.println("THe pripority category predicted was : "+response);
//        return TicketPriority.valueOf(response.trim());
//    }
//
//    private String callGemini(String prompt) {
//
//        Map<String, Object> request = Map.of(
//                "contents", List.of(
//                        Map.of(
//                                "role", "user",
//                                "parts", List.of(
//                                        Map.of("text", prompt)
//                                )
//                        )
//                )
//        );
//
//        return webClient.post()
//                .bodyValue(request)
//                .retrieve()
//                .bodyToMono(Map.class)
//                .map(resp -> {
//                    List<Map<String, Object>> candidates =
//                            (List<Map<String, Object>>) resp.get("candidates");
//
//                    Map<String, Object> content =
//                            (Map<String, Object>) candidates.get(0).get("content");
//
//                    List<Map<String, Object>> parts =
//                            (List<Map<String, Object>>) content.get("parts");
//
//                    return parts.get(0).get("text").toString().trim();
//                })
//                .block();
//    }
//
//
//
//}
