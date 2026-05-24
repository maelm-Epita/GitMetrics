package com.mael_minard.gitmetrics.converter;

import com.mael_minard.gitmetrics.dto.LatestReleaseGithubResponse;
import com.mael_minard.gitmetrics.dto.RepoGithubResponse;
import com.mael_minard.gitmetrics.dto.RepoMetricsResponse;

import java.util.Map;

public class RepoMetricsConverter {
    public static RepoMetricsResponse toRepoMetricsResponse(RepoGithubResponse repo,
                                                     Map<String, Integer> languages,
                                                     LatestReleaseGithubResponse latest_release) {
        RepoMetricsResponse resp = new RepoMetricsResponse();
        resp.setFull_name(repo.getFull_name());
        resp.setDescription(repo.getDescription());
        resp.setHtml_url(repo.getHtml_url());
        resp.setCreated_at(repo.getCreated_at());
        resp.setPushed_at(repo.getPushed_at());
        resp.setUpdated_at(repo.getUpdated_at());
        resp.setForks_count(repo.getForks_count());
        resp.setStargazers_count(repo.getStargazers_count());
        resp.setWatchers_count(repo.getWatchers_count());
        resp.setSubscribers_count(repo.getSubscribers_count());
        resp.setOpen_issues_count(repo.getOpen_issues_count());
        resp.setOwner(repo.getOwner());
        resp.setSecurity_and_analysis(repo.getSecurity_and_analysis());
        resp.setLanguages(languages);
        resp.setLatest_release_published_at(latest_release.getPublished_at());
        return resp;
    }
}
