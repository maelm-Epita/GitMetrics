package com.mael_minard.gitmetrics.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/repos")
public class ReposController {

    private final WebClient githubWebClient;

    public ReposController(WebClient githubWebClient) {
        this.githubWebClient = githubWebClient;
    }

    @Cacheable(value = "repoSearch", key = "#q")
    @GetMapping("/search")
    public Mono<String> searchRepos(@RequestParam String q) {

        System.out.println("Hitting Github API");
        return githubWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/repositories")
                        .queryParam("q", q)
                        .queryParam("per_page", 5)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
