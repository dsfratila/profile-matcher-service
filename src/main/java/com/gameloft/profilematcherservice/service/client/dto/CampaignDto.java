package com.gameloft.profilematcherservice.service.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

public record CampaignDto(String game,
                          String name,
                          BigDecimal priority,
                          Matchers matchers,
                          @JsonProperty("start_date") ZonedDateTime start,
                          @JsonProperty("end_date") ZonedDateTime end,
                          boolean enabled,
                          @JsonProperty("last_updated") ZonedDateTime lastUpdated) {

    public record Matchers(LevelMatcher level,
                           HasMatcher has,
                           @JsonProperty("does_not_have") DoesNotHaveMatcher doesNotHave) {
    }

    public record LevelMatcher(long min,
                               long max) {
    }

    public record HasMatcher(Set<String> country,
                             Set<String> items) {
    }

    public record DoesNotHaveMatcher(Set<String> items) {
    }

}
