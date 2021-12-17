package com.jayr33n.commands.create;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Getter
@Setter
@Introspected
public class WorkoutCreateCommand {
    @NotBlank private String name;

    @NotNull private Collection<ExerciseWorkoutCreateCommand> exercises;
}
