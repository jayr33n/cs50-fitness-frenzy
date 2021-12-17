package com.jayr33n.commands.read;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Introspected
@AllArgsConstructor
public class ExerciseWorkoutReadCommand {
    @Positive private Byte sets;

    @Positive private Byte repetitions;

    @Positive private Short restInterval;

    @NotNull private ExerciseReadCommand exercise;
}
