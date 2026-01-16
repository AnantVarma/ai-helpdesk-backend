//package com.anant.helpdesk.ai.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//public class OpenAiConfig {
//
//    @Bean
//    public WebClient openAiWebClient() {
//        return WebClient.builder()
//                .baseUrl("https://api.openai.com/v1/chat/completions")
//                .defaultHeader("Authorization", "Bearer " + System.getenv("OPENAI_API_KEY"))
//                .defaultHeader("Content-Type", "application/json")
//                .build();
//    }
//}
