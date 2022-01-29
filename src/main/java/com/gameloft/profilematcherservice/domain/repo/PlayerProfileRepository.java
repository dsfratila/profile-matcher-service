package com.gameloft.profilematcherservice.domain.repo;

import com.gameloft.profilematcherservice.domain.model.PlayerProfile;
import reactor.core.publisher.Mono;

public interface PlayerProfileRepository {

    Mono<PlayerProfile> findById(String id);

    Mono<Boolean> save(PlayerProfile playerProfile);

}
