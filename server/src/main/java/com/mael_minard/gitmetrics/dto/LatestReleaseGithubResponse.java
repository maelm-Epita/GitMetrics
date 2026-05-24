package com.mael_minard.gitmetrics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LatestReleaseGithubResponse {
    @JsonProperty
    private String published_at;
}
