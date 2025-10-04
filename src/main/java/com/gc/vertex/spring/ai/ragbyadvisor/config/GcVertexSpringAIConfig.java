package com.gc.vertex.spring.ai.ragbyadvisor.config;

import org.springframework.ai.vertexai.embedding.VertexAiEmbeddingConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GcVertexSpringAIConfig {

    @Bean
    public VertexAiEmbeddingConnectionDetails vertexAiEmbeddingConnectionDetails() {
        return VertexAiEmbeddingConnectionDetails.builder()
                .projectId("gc-vertex-spring-ai-project") // Replace with your GCP project ID
                .location("us-central1") // Replace with your desired GCP region
                .build();
    }
}
