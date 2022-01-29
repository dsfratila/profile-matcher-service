package com.gameloft.profilematcherservice.service;

import com.gameloft.profilematcherservice.service.client.CampaignsServiceClient;
import com.gameloft.profilematcherservice.service.client.dto.CampaignDto;
import com.gameloft.profilematcherservice.domain.model.PlayerProfile;
import com.gameloft.profilematcherservice.domain.repo.PlayerProfileRepository;
import com.gameloft.profilematcherservice.web.dto.ClientConfigDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientConfigService {

    private final CampaignsServiceClient campaignsServiceClient;
    private final PlayerProfileRepository playerProfileRepository;
    private final PlayerCampaignMatchingService playerCampaignMatchingService;

    public Mono<ClientConfigDto> getClientConfigById(final String playerId) {

        log.debug("Fetching client configuration for playerId={}", playerId);
        return playerProfileRepository.findById(playerId)
                .zipWith(campaignsServiceClient.getActiveCampaign())
                .map(t2 -> mapPlayerCampaign(t2.getT1(), t2.getT2()));
    }

    private ClientConfigDto mapPlayerCampaign(final PlayerProfile playerProfile,
                                              final CampaignDto activeCampaign) {

        if (playerCampaignMatchingService.matches(playerProfile, activeCampaign)) {
            log.trace("Player matches active campaign.");
            return ClientConfigDto.from(playerProfile, activeCampaign);
        } else {
            log.trace("Player does not match active campaign.");
            return ClientConfigDto.from(playerProfile);
        }
    }

}
