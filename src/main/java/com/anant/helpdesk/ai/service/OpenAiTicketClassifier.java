//package com.anant.helpdesk.ai.service;
//
//import com.anant.helpdesk.common.enums.TicketCategory;
//import com.anant.helpdesk.common.enums.TicketPriority;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.Map;
//
//@Service
//public class OpenAiTicketClassifier implements AiTicketClassifier {
//
//    private final WebClient webClient;
//
//    public OpenAiTicketClassifier(WebClient openAiWebClient) {
//        this.webClient = openAiWebClient;
//    }
//
//    @Override
//    public TicketCategory classifyCategory(String ticketText) {
//
//        String prompt = """
//        Respond with ONLY ONE WORD from this list:
//        LOGIN_ISSUE, PAYMENT, BUG, FEATURE_REQUEST, GENERAL
//
//        Ticket:
//        """ + ticketText;
//
//        String response = callOpenAi(prompt);
//        return TicketCategory.valueOf(response.trim());
//    }
//
//    @Override
//    public TicketPriority classifyPriority(String ticketText) {
//
//        String prompt = """
//        Respond with ONLY ONE WORD from this list:
//        LOW, MEDIUM, HIGH, CRITICAL
//
//        Ticket:
//        """ + ticketText;
//
//        String response = callOpenAi(prompt);
//        return TicketPriority.valueOf(response.trim());
//    }
//
//    private String callOpenAi(String prompt) {
//
//        Map<String, Object> requestBody = Map.of(
//                "model", "gpt-3.5-turbo",
//                "messages", new Object[]{
//                        Map.of("role", "user", "content", prompt)
//                },
//                "temperature", 0
//        );
//
//        return webClient.post()
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToMono(Map.class)
//                .map(response ->
//                        ((Map)((Map)((java.util.List)response.get("choices"))
//                                .get(0)).get("message")).get("content").toString()
//                )
//                .block(); // blocking is OK for now
//    }
//}
