package com.mael_minard.gitmetrics.service;

import com.mael_minard.gitmetrics.converter.RepoMetricsConverter;
import com.mael_minard.gitmetrics.dto.LatestReleaseGithubResponse;
import com.mael_minard.gitmetrics.dto.RepoGithubResponse;
import com.mael_minard.gitmetrics.dto.RepoMetricsResponse;
import com.mael_minard.gitmetrics.dto.ReposSearchResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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

    public Mono<RepoMetricsResponse> getRepoMetrics(String owner, String repo) {
        Mono<RepoGithubResponse> repo_resp = githubWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/repos/" + owner + "/" + repo)
                        .build())
                .retrieve()
                .bodyToMono(RepoGithubResponse.class);
        Mono<Map<String, Integer>> languages_resp = githubWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/repos/" + owner + "/" + repo + "/languages")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Integer>>() {});
        Mono<LatestReleaseGithubResponse> latest_release_resp = githubWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/repos/" + owner + "/" + repo + "/releases/latest")
                        .build())
                .retrieve()
                .bodyToMono(LatestReleaseGithubResponse.class);
        return Mono.zip(repo_resp, languages_resp, latest_release_resp)
                .map(tuple -> {
                    RepoGithubResponse repoData = tuple.getT1();
                    Map<String, Integer> languages = tuple.getT2();
                    LatestReleaseGithubResponse latestRelase = tuple.getT3();
                    return RepoMetricsConverter.toRepoMetricsResponse(repoData, languages, latestRelase);
                });
    }
}
