package com.mael_minard.gitmetrics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.util.Map;

@Setter
public class RepoMetricsResponse {
    @JsonProperty
    private String full_name;
    @JsonProperty
    private String description;
    @JsonProperty
    private String html_url;
    @JsonProperty
    private String created_at;
    @JsonProperty
    private String pushed_at;
    @JsonProperty
    private String updated_at;

    @JsonProperty
    private int forks_count;
    @JsonProperty
    private int stargazers_count;
    @JsonProperty
    private int watchers_count;
    @JsonProperty
    private int subscribers_count;
    @JsonProperty
    private int open_issues_count;

    @JsonProperty
    private RepoOwnerResponse owner;

    @JsonProperty
    private RepoSecurityResponse security_and_analysis;

    @JsonProperty
    private Map<String, Integer> languages;

    @JsonProperty
    private String latest_release_published_at;
}
