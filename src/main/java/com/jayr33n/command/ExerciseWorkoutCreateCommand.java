package com.jayr33n.command;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Introspected
public class ExerciseWorkoutCreateCommand {
    @NotNull
    private Byte sets;

    @NotNull
    private Byte repetitions;

    @NotNull
    private Short restInterval;
}
