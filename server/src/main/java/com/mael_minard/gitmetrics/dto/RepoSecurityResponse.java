package com.mael_minard.gitmetrics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepoSecurityResponse {
    @JsonProperty
    private AdvancedSecurity advanced_security;

    @Getter
    @Setter
    private static class AdvancedSecurity {
        @JsonProperty
        private String status;
    }
}
