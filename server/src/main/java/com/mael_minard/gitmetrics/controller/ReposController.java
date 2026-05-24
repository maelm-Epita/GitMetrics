package com.mael_minard.gitmetrics.controller;

import com.mael_minard.gitmetrics.dto.RepoMetricsResponse;
import com.mael_minard.gitmetrics.dto.ReposSearchResponse;
import com.mael_minard.gitmetrics.service.ReposService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/repos")
public class ReposController {

    private final ReposService reposService;

    public ReposController(
            ReposService reposService
    ) {
        this.reposService = reposService;
    }

    @GetMapping("/search")
    public Mono<ReposSearchResponse> searchRepos(@RequestParam String q) {
        return reposService.searchRepos(q);
    }

    @Cacheable("repoMetrics")
    @GetMapping("/{owner}/{repo}")
    public Mono<RepoMetricsResponse> getRepoMetrics(@PathVariable String owner, @PathVariable String repo) {
        return reposService.getRepoMetrics(owner, repo);
    }
}
