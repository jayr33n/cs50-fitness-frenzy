package com.jayr33n.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jayr33n.commands.read.WorkoutReadCommand;
import io.micronaut.core.annotation.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "workouts")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "author", nullable = false)
    private String author;


    /**
     * Forced to eager fetch since {@link Transactional} does not work for some reason
     */
    @OneToMany(mappedBy = "workout", fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private Set<ExerciseWorkout> exercises = new HashSet<>();

    public Workout(@NonNull String name, @NonNull String author) {
        this.name = name;
        this.author = author;
    }

    public WorkoutReadCommand filter() {
        return new WorkoutReadCommand(id, name, author, exercises.stream()
                .map(ExerciseWorkout::filter)
                .collect(Collectors.toSet()));
    }
}
