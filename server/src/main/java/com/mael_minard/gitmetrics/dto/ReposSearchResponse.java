package com.mael_minard.gitmetrics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ReposSearchResponse {
    @JsonProperty
    private ArrayList<ReposSearchRepo> items;

    private static class ReposSearchRepo {
        @JsonProperty
        private String id;
        @JsonProperty
        private String full_name;
        @JsonProperty
        private int stargazers_count;
    }
}
