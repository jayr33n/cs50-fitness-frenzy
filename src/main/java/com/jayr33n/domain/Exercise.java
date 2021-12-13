package com.jayr33n.domain;

import com.jayr33n.ExerciseUpdateCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "exercise")
public class Exercise extends DomainEntity {
    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "difficulty", nullable = false)
    @Enumerated
    private Difficulty difficulty;

    public Exercise(@NotBlank String name,
                    @NotNull Difficulty difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public Exercise map(ExerciseUpdateCommand command) {
        this.name = command.getName();
        this.difficulty = command.getDifficulty();
        return this;
    }
}
