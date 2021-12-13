package com.jayr33n.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jayr33n.exercise.ExerciseUpdateCommand;
import io.micronaut.core.annotation.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "exercises")
public class Exercise extends AbstractEntity {
    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "difficulty", nullable = false)
    @Enumerated
    private Difficulty difficulty;

    @ManyToMany
    @JoinTable(name = "exercise_muscle",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_id"))
    @ToString.Exclude
    @JsonIgnore
    private Set<Muscle> muscles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "exercise_equipment",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    @ToString.Exclude
    @JsonIgnore
    private Set<Equipment> equipment = new HashSet<>();

    public Exercise(@NonNull String name, @NonNull Difficulty difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public Exercise map(@NonNull ExerciseUpdateCommand command) {
        this.name = command.getName();
        this.difficulty = command.getDifficulty();
        return this;
    }
}
