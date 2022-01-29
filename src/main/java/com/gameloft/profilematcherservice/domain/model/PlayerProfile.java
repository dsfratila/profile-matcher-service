package com.gameloft.profilematcherservice.domain.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public record PlayerProfile(
        String id,
        String credential,
        ZonedDateTime created,
        ZonedDateTime modified,
        ZonedDateTime lastSession,
        BigDecimal totalSpent,
        BigDecimal totalRefund,
        long totalTransactions,
        ZonedDateTime lastPurchase,
        List<PlayerProfileDevice> devices,
        long level,
        long xp,
        long totalPlaytime,
        String country,
        String language,
        ZonedDateTime birthdate,
        String genre,
        Map<String, Object> inventory,
        PlayerProfileClan clan) {

    @Builder
    public PlayerProfile {
    }

}
