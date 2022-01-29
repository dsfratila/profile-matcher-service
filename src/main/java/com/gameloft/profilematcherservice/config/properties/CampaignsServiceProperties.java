package com.gameloft.profilematcherservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

@ConstructorBinding
@ConfigurationProperties(prefix = "services.campaigns")
public record CampaignsServiceProperties(String url,
                                         Duration activeCampaignsCacheDuration) {

}
