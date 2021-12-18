package com.jayr33n.commands.read;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Getter
@Setter
@Introspected
@AllArgsConstructor
public class WorkoutReadCommand {
    @NotNull private Long id;

    @NotBlank private String name;

    @NotBlank private String author;

    @NotNull private Collection<ExerciseWorkoutReadCommand> exercises;
}
