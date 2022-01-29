package com.gameloft.profilematcherservice.domain.model;

public record PlayerProfileDevice(long id,
                                  String model,
                                  String carrier,
                                  String firmware) {
}