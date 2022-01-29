package com.gameloft.profilematcherservice.web.controller;

import com.gameloft.profilematcherservice.exception.ResourceNotFoundException;
import com.gameloft.profilematcherservice.service.ClientConfigService;
import com.gameloft.profilematcherservice.web.dto.ClientConfigDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ClientConfigController {

    private final ClientConfigService clientConfigFacade;

    @GetMapping("/get_client_config/{playerId}")
    public Mono<ClientConfigDto> getClientConfigById(@PathVariable("playerId") final String playerId) {

        return clientConfigFacade.getClientConfigById(playerId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()));
    }

}
