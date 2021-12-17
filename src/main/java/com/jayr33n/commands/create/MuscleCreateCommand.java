package com.jayr33n.commands.create;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Introspected
public class MuscleCreateCommand {
    @NotBlank private String name;
}
