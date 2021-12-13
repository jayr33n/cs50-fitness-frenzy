package com.jayr33n.command;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Introspected
public class EquipmentCreateCommand {
    @NotBlank private String name;
}
