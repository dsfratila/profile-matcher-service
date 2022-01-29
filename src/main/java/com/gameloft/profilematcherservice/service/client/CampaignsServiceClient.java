package com.gameloft.profilematcherservice.service.client;

import com.gameloft.profilematcherservice.service.client.dto.CampaignDto;
import reactor.core.publisher.Mono;

public interface CampaignsServiceClient {

    Mono<CampaignDto> getActiveCampaign();

}
