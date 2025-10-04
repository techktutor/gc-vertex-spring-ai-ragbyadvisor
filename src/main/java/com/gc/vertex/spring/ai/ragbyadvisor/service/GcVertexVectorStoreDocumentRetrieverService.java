package com.gc.vertex.spring.ai.ragbyadvisor.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GcVertexVectorStoreDocumentRetrieverService {

    private final VectorStore vectorStore;

    public GcVertexVectorStoreDocumentRetrieverService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<Document> listDocuments(String query) {
        DocumentRetriever documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .similarityThreshold(0.73)
                .topK(5)
                .filterExpression(new FilterExpressionBuilder()
                        .eq("genre", "fairytale")
                        .build())
                .build();

        return documentRetriever.retrieve(new Query("What is the main character of the story?"));
    }
}
