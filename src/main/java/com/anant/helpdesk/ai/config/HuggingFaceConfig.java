//package com.anant.helpdesk.ai.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//public class HuggingFaceConfig {
//
//    @Value()
//    private String apiKey;
//
//    @Bean
//    public WebClient huggingFaceWebClient() {
//        return WebClient.builder()
//                .baseUrl("")
//                .defaultHeader("Authorization", "Bearer " + apiKey)
//                .build();
//    }
//}
