package com.mael_minard.gitmetrics.service;

import com.mael_minard.gitmetrics.dto.ReposSearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ReposService {
    private final WebClient githubWebClient;

    public ReposService(WebClient githubWebClient) {
        this.githubWebClient = githubWebClient;
    }

    public Mono<ReposSearchResponse> searchRepos(String query) {
        return githubWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/repositories")
                        .queryParam("q", query)
                        .queryParam("per_page", 5)
                        .build())
                .retrieve()
                .bodyToMono(ReposSearchResponse.class);
    }
}
