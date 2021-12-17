package com.jayr33n.commands.create;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@Introspected
public class ExerciseWorkoutCreateCommand {
    @Positive private Byte sets;

    @Positive private Byte repetitions;

    @Positive private Short restInterval;

    @Nullable private Long exerciseId;
}
