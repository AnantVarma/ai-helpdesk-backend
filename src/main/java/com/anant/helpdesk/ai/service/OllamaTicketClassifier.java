package com.anant.helpdesk.ai.service;

import com.anant.helpdesk.common.enums.TicketCategory;
import com.anant.helpdesk.common.enums.TicketPriority;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class OllamaTicketClassifier implements AiTicketClassifier {

    private final WebClient webClient;

    public OllamaTicketClassifier(WebClient ollamaWebClient) {
        this.webClient = ollamaWebClient;
    }

    @Override
    public TicketCategory classifyCategory(String text) {
        String prompt = """
           If the ticket mentions money, charges, payment, refund, billing or orders → PAYMENT.
                
           Classify this helpdesk ticket into EXACTLY ONE of:
           LOGIN_ISSUE, PAYMENT, BUG, FEATURE_REQUEST, GENERAL.
           
           Return ONLY the category name in uppercase, nothing else.
    
    Ticket:
    """ + text;

        String response = call(prompt);
        System.out.println(response);

        try {
            return TicketCategory.valueOf(response.trim());
        } catch (IllegalArgumentException e) {
            return TicketCategory.GENERAL;
        }
    }

    @Override
    public TicketPriority classifyPriority(String text) {
        String prompt = """
    Classify the urgency/priority of this helpdesk ticket into EXACTLY ONE of:
    LOW, MEDIUM, HIGH, CRITICAL
    
    Return ONLY the priority level in uppercase, nothing else.
    
    Ticket:
    """ + text;

        String response = call(prompt);

        // Handle common AI responses
        String normalized = response.trim().toUpperCase();

        if (normalized.contains("LOW")) return TicketPriority.LOW;
        if (normalized.contains("MEDIUM") || normalized.contains("NORMAL")) return TicketPriority.MEDIUM;
        if (normalized.contains("HIGH")) return TicketPriority.HIGH;
        if (normalized.contains("CRITICAL") || normalized.contains("URGENT")) return TicketPriority.CRITICAL;

        return TicketPriority.MEDIUM; // Default fallback
    }

    private String call(String prompt) {
        Map<String, Object> request = Map.of(
                "model", "qwen2:0.5b",
                "prompt", prompt,
                "stream", false
        );

        Map response = webClient.post()
                .uri("/api/generate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        String result = response.get("response").toString().trim();
        System.out.println("AI Response: " + result); // Add this line
        return result;
    }

}
