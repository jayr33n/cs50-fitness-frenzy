package com.jayr33n.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jayr33n.DifficultyDeserializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(using = DifficultyDeserializer.class)
public enum Difficulty {
    BEGINNER("beg"),
    INTERMEDIATE("int"),
    ADVANCED("adv");

    @Getter @NotBlank private final String name;
}
