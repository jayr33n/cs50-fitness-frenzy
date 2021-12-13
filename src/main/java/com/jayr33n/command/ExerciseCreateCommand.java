package com.jayr33n.command;

import com.jayr33n.domain.Difficulty;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Introspected
public class ExerciseCreateCommand {
    @NotBlank private String name;

    @NotNull private Difficulty difficulty;
}
