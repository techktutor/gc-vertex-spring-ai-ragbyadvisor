package com.gc.vertex.spring.ai.ragbyadvisor.controller;

import com.gc.vertex.spring.ai.ragbyadvisor.service.GcVertexSpringAIQuestionAnswerAdvisorRagService;
import com.gc.vertex.spring.ai.ragbyadvisor.service.GcVertexSpringAIRetrievalAugmentationAdvisorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rag")
public class GcVertexSpringAIRagAdvisorController {

    private final GcVertexSpringAIQuestionAnswerAdvisorRagService gcVertexSpringAIQuestionAnswerAdvisorRagService;
    private final GcVertexSpringAIRetrievalAugmentationAdvisorService gcVertexSpringAIRetrievalAugmentationAdvisorService;

    public GcVertexSpringAIRagAdvisorController(GcVertexSpringAIQuestionAnswerAdvisorRagService gcVertexSpringAIQuestionAnswerAdvisorRagService, GcVertexSpringAIRetrievalAugmentationAdvisorService gcVertexSpringAIRetrievalAugmentationAdvisorService) {
        this.gcVertexSpringAIQuestionAnswerAdvisorRagService = gcVertexSpringAIQuestionAnswerAdvisorRagService;
        this.gcVertexSpringAIRetrievalAugmentationAdvisorService = gcVertexSpringAIRetrievalAugmentationAdvisorService;
    }

    @PostMapping("/add")
    public String addDoc(@RequestBody String text) {
        gcVertexSpringAIQuestionAnswerAdvisorRagService.addDocument(text);
        return "Stored document in vector DB";
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return gcVertexSpringAIQuestionAnswerAdvisorRagService.ask(question);
    }

    @GetMapping("/askQuestion")
    public String askQuestion(@RequestParam String question) {
        return gcVertexSpringAIQuestionAnswerAdvisorRagService.askQuestion(question);
    }

    @GetMapping("/askNaiveRag")
    public String askNaiveRag(@RequestParam String question) {
        return gcVertexSpringAIRetrievalAugmentationAdvisorService.askNaiveRag(question);
    }

    @GetMapping("/askAdvancedRag")
    public String askAdvancedRag(@RequestParam String question) {
        return gcVertexSpringAIRetrievalAugmentationAdvisorService.askAdvancedRag(question);
    }
}
