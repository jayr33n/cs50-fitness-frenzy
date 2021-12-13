package com.jayr33n.exercise;

import com.jayr33n.domain.Difficulty;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Introspected
public class ExerciseUpdateCommand {
    @NotBlank
    private String name;

    @NotNull
    private Difficulty difficulty;
}
