package com.jayr33n.commands.update;

import com.jayr33n.domain.Difficulty;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Introspected
public class ExerciseUpdateCommand {
    @NotBlank private String name;

    @NotNull private Difficulty difficulty;
}
