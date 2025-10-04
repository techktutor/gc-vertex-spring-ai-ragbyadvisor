package com.gc.vertex.spring.ai.ragbyadvisor.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GcVertexSpringAIQuestionAnswerAdvisorRagService {

    private final VectorStore vectorStore;
    private final ChatModel chatModel;

    public GcVertexSpringAIQuestionAnswerAdvisorRagService(VectorStore vectorStore, ChatModel chatModel) {
        this.vectorStore = vectorStore;
        this.chatModel = chatModel;
    }

    public void addDocument(String text) {
        vectorStore.add(List.of(new Document(text)));
    }

    public String ask(String question) {
        var qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder().similarityThreshold(0.8d).topK(6).build())
                .build();

        ChatResponse response = ChatClient.builder(chatModel).build()
                .prompt()
                .advisors(qaAdvisor)
                .user(question)
                .call()
                .chatResponse();

        assert response != null;
        return response.getResult().getOutput().getText();
    }

    public String askQuestion(String question) {
        PromptTemplate customPromptTemplate = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
                .template("""
                        <query>
                        
                        Context information is below.
                        
                        ---------------------
                        <question_answer_context>
                        ---------------------
                        
                        Given the context information and no prior knowledge, answer the query.
                        
                        Follow these rules:
                        
                        1. If the answer is not in the context, just say that you don't know.
                        2. Avoid statements like "Based on the context..." or "The provided information...".
                        """)
                .build();

        QuestionAnswerAdvisor qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .promptTemplate(customPromptTemplate)
                .build();

        return ChatClient.builder(chatModel).build()
                .prompt(question)
                .advisors(qaAdvisor)
                .call()
                .content();
    }
}
