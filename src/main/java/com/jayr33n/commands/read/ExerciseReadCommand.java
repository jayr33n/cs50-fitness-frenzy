package com.jayr33n.commands.read;

import com.jayr33n.domain.Difficulty;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Introspected
@AllArgsConstructor
public class ExerciseReadCommand {
    @NotNull private long id;

    @NotNull private String name;

    @NotNull private Difficulty difficulty;
}
