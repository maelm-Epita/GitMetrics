package com.mael_minard.gitmetrics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepoOwnerResponse {
    @JsonProperty
    private String avatar_url;
}
