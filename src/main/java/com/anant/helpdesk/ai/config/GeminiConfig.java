//package com.anant.helpdesk.ai.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.ClientRequest;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.net.URI;
//import java.util.Map;
//
//@Configuration
//public class GeminiConfig {
//
//    @Value("${gemini.api.key}")
//    private String apiKey;
//
//    @Bean
//    public WebClient geminiWebClient(@Value("${gemini.api.key}") String apiKey) {
//        return WebClient.builder()
//                .baseUrl("https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash-latest:generateContent")
//                .defaultHeader("Content-Type", "application/json")
//                .filter((request, next) -> {
//                    String urlWithKey = request.url().toString() + "?key=" + apiKey;
//                    return next.exchange(
//                            ClientRequest.from(request)
//                                    .url(URI.create(urlWithKey))
//                                    .build()
//                    );
//                })
//                .build();
//    }
//}
