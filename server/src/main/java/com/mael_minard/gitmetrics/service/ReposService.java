package com.mael_minard.gitmetrics.service;

import com.mael_minard.gitmetrics.converter.RepoMetricsConverter;
import com.mael_minard.gitmetrics.dto.LatestReleaseGithubResponse;
import com.mael_minard.gitmetrics.dto.RepoGithubResponse;
import com.mael_minard.gitmetrics.dto.RepoMetricsResponse;
import com.mael_minard.gitmetrics.dto.ReposSearchResponse;
import org.apache.coyote.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

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

    public Mono<ResponseEntity<RepoMetricsResponse>> getRepoMetrics(String owner, String repo) {
        Mono<ResponseEntity<RepoGithubResponse>> repo_resp = githubWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/repos/" + owner + "/" + repo)
                        .build())
                .retrieve()
                .bodyToMono(RepoGithubResponse.class)
                .map(ResponseEntity::ok)
                .onErrorResume(WebClientResponseException.class, ex ->
                        Mono.just(
                                ResponseEntity
                                        .status(ex.getStatusCode())
                                        .body(null)
                        )
                );
        Mono<ResponseEntity<Map<String, Integer>>> languages_resp = githubWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/repos/" + owner + "/" + repo + "/languages")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Integer>>() {})
                .map(ResponseEntity::ok)
                .onErrorResume(WebClientResponseException.class, ex ->
                        Mono.just(
                                ResponseEntity
                                        .status(ex.getStatusCode())
                                        .body(null)
                        )
                );
        Mono<ResponseEntity<LatestReleaseGithubResponse>> latest_release_resp = githubWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/repos/" + owner + "/" + repo + "/releases/latest")
                        .build())
                .retrieve()
                .bodyToMono(LatestReleaseGithubResponse.class)
                .map(ResponseEntity::ok)
                .onErrorResume(WebClientResponseException.class, ex ->
                        Mono.just(
                                ResponseEntity
                                        .status(ex.getStatusCode())
                                        .body(null)
                        )
                );
        return Mono.zip(repo_resp, languages_resp, latest_release_resp)
                .map(tuple -> {
                    ResponseEntity<RepoGithubResponse> repoData = tuple.getT1();
                    ResponseEntity<Map<String, Integer>> languages = tuple.getT2();
                    ResponseEntity<LatestReleaseGithubResponse> latestRelease = tuple.getT3();

                    if (repoData.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    }

                    assert repoData.getBody() != null;

                    return ResponseEntity.ok(RepoMetricsConverter.toRepoMetricsResponse(
                            repoData.getBody(), languages.getBody(), latestRelease.getBody()));
                });
    }
}
