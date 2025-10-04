package com.gc.vertex.spring.ai.ragbyadvisor.controller;

import com.gc.vertex.spring.ai.ragbyadvisor.service.GcVertexQuestionAnswerAdvisorRagService;
import com.gc.vertex.spring.ai.ragbyadvisor.service.GcVertexRetrievalAugmentationAdvisorService;
import com.gc.vertex.spring.ai.ragbyadvisor.service.GcVertexVectorStoreDocumentRetrieverService;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rag")
public class GcVertexSpringAIRagAdvisorController {

    private final GcVertexQuestionAnswerAdvisorRagService gcVertexQuestionAnswerAdvisorRagService;
    private final GcVertexRetrievalAugmentationAdvisorService gcVertexRetrievalAugmentationAdvisorService;
    private final GcVertexVectorStoreDocumentRetrieverService gcVertexVectorStoreDocumentRetrieverService;

    public GcVertexSpringAIRagAdvisorController(GcVertexQuestionAnswerAdvisorRagService gcVertexQuestionAnswerAdvisorRagService, GcVertexRetrievalAugmentationAdvisorService gcVertexRetrievalAugmentationAdvisorService, GcVertexVectorStoreDocumentRetrieverService gcVertexVectorStoreDocumentRetrieverService) {
        this.gcVertexQuestionAnswerAdvisorRagService = gcVertexQuestionAnswerAdvisorRagService;
        this.gcVertexRetrievalAugmentationAdvisorService = gcVertexRetrievalAugmentationAdvisorService;
        this.gcVertexVectorStoreDocumentRetrieverService = gcVertexVectorStoreDocumentRetrieverService;
    }

    @PostMapping("/add")
    public String addDoc(@RequestBody String text) {
        gcVertexQuestionAnswerAdvisorRagService.addDocument(text);
        return "Stored document in vector DB";
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return gcVertexQuestionAnswerAdvisorRagService.ask(question);
    }

    @GetMapping("/askQuestion")
    public String askQuestion(@RequestParam String question) {
        return gcVertexQuestionAnswerAdvisorRagService.askQuestion(question);
    }

    @GetMapping("/askNaiveRag")
    public String askNaiveRag(@RequestParam String question) {
        return gcVertexRetrievalAugmentationAdvisorService.askNaiveRag(question);
    }

    @GetMapping("/askAdvancedRag")
    public String askAdvancedRag(@RequestParam String question) {
        return gcVertexRetrievalAugmentationAdvisorService.askAdvancedRag(question);
    }

    @GetMapping("/listDocuments")
    public List<Document> listDocuments(@RequestParam String question) {
        return gcVertexVectorStoreDocumentRetrieverService.listDocuments(question);
    }
}
