package com.gameloft.profilematcherservice.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameloft.profilematcherservice.service.client.dto.CampaignDto;
import com.gameloft.profilematcherservice.domain.model.PlayerProfile;
import com.gameloft.profilematcherservice.domain.model.PlayerProfileClan;
import com.gameloft.profilematcherservice.domain.model.PlayerProfileDevice;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public record ClientConfigDto(
        @JsonProperty("player_id") String id,
        String credential,
        ZonedDateTime created,
        ZonedDateTime modified,
        @JsonProperty("last_session") ZonedDateTime lastSession,
        @JsonProperty("total_spent") BigDecimal totalSpent,
        @JsonProperty("total_refund") BigDecimal totalRefund,
        @JsonProperty("total_transactions") long totalTransactions,
        @JsonProperty("last_purchase") ZonedDateTime lastPurchase,
        @JsonProperty("active_campaigns") List<String> activeCampaigns,
        List<PlayerProfileDevice> devices,
        long level,
        long xp,
        @JsonProperty("total_playtime") long totalPlaytime,
        String country,
        String language,
        ZonedDateTime birthdate,
        String genre,
        Map<String, Object> inventory,
        PlayerProfileClan clan) {

    @Builder
    public ClientConfigDto {
    }

    private static ClientConfigDto.ClientConfigDtoBuilder fromInternal(final PlayerProfile playerProfile) {
        return ClientConfigDto.builder()
                .id(playerProfile.id())
                .credential(playerProfile.credential())
                .created(playerProfile.created())
                .modified(playerProfile.modified())
                .modified(playerProfile.modified())
                .lastSession(playerProfile.lastSession())
                .totalSpent(playerProfile.totalSpent())
                .totalRefund(playerProfile.totalRefund())
                .totalTransactions(playerProfile.totalTransactions())
                .totalTransactions(playerProfile.totalTransactions())
                .lastPurchase(playerProfile.lastPurchase())
                .devices(playerProfile.devices())
                .level(playerProfile.level())
                .xp(playerProfile.xp())
                .totalPlaytime(playerProfile.totalPlaytime())
                .country(playerProfile.country())
                .language(playerProfile.language())
                .birthdate(playerProfile.birthdate())
                .genre(playerProfile.genre())
                .inventory(playerProfile.inventory())
                .clan(playerProfile.clan())
                ;
    }

    public static ClientConfigDto from(final PlayerProfile playerProfile, final CampaignDto activeCampaign) {
        return fromInternal(playerProfile)
                .activeCampaigns(List.of(activeCampaign.name()))
                .build();
    }

    public static ClientConfigDto from(final PlayerProfile playerProfile) {
        return fromInternal(playerProfile)
                .activeCampaigns(List.of())
                .build();
    }

}
