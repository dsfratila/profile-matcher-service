package com.gameloft.profilematcherservice.service;

import com.gameloft.profilematcherservice.service.client.dto.CampaignDto;
import com.gameloft.profilematcherservice.domain.model.PlayerProfile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PlayerCampaignMatchingService {

    private final Clock clock;

    public boolean matches(final PlayerProfile profile, final CampaignDto campaign) {


        return isCampaignActive(campaign) && matchesProfile(profile, campaign);
    }

    private boolean matchesProfile(final PlayerProfile profile, final CampaignDto campaign) {

        return matchesLevel(profile.level(), campaign.matchers().level())
                || matchesHas(profile, campaign.matchers().has())
                || matchesDoesNotHave(profile, campaign.matchers().doesNotHave());
    }

    private boolean matchesDoesNotHave(final PlayerProfile profile, final CampaignDto.DoesNotHaveMatcher doesNotHave) {

        final Set<String> itemShouldNotHave = doesNotHave.items();
        return profile.inventory().keySet().stream().anyMatch(itemShouldNotHave::contains);
    }

    private boolean matchesLevel(final long playerLevel, final CampaignDto.LevelMatcher levelMatcher) {

        return playerLevel >= levelMatcher.min() && playerLevel <= levelMatcher.max();
    }

    private boolean matchesHas(final PlayerProfile playerProfile, final CampaignDto.HasMatcher hasMatcher) {

        return hasMatcher.country().contains(playerProfile.country()) ||
                hasAnyItem(playerProfile.inventory(), hasMatcher.items());
    }

    private boolean hasAnyItem(final Map<String, Object> inventory, final Set<String> items) {

        return inventory.keySet().stream().anyMatch(items::contains);
    }

    private boolean isCampaignActive(final CampaignDto activeCampaignDto) {

        return activeCampaignDto.enabled() &&
                Range.between(activeCampaignDto.start(), activeCampaignDto.end()).contains(clock.now());
    }

}
