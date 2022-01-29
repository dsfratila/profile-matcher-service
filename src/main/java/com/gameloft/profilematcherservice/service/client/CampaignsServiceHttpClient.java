package com.gameloft.profilematcherservice.service.client;

import com.gameloft.profilematcherservice.service.client.dto.CampaignDto;
import com.gameloft.profilematcherservice.config.properties.CampaignsServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@Slf4j
public class CampaignsServiceHttpClient implements CampaignsServiceClient {

    private final WebClient webClient;
    private final URI activeCampaignsURI;
    private final CampaignsServiceProperties properties;

    public CampaignsServiceHttpClient(final WebClient webClient, final CampaignsServiceProperties properties) {

        this.webClient = webClient;
        this.properties = properties;
        this.activeCampaignsURI = UriComponentsBuilder.fromHttpUrl(properties.url())
                .path("/v1/campaigns/active")
                .build().toUri();
    }

    @Cacheable("active-campaign")
    public Mono<CampaignDto> getActiveCampaign() {

        return webClient.get()
                .uri(activeCampaignsURI)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(CampaignDto.class)
                .mapNotNull(HttpEntity::getBody)
                .doOnSuccess(__ -> log.debug("Returning active campaign..."))
                .cache(properties.activeCampaignsCacheDuration());
    }

}
