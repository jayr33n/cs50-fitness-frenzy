package com.jayr33n.domain;

import com.jayr33n.command.ExerciseWorkoutCreateCommand;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "exercises_workouts")
public class ExerciseWorkout {
    @EmbeddedId
    private ExerciseWorkoutKey id = new ExerciseWorkoutKey();

    @NotNull
    @ManyToOne
    @MapsId("workoutId")
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @NotNull
    @ManyToOne
    @MapsId("exerciseId")
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @NotNull
    @Column(name = "sets", nullable = false)
    private Byte sets;

    @NotNull
    @Column(name = "repetitions", nullable = false)
    private Byte repetitions;

    @NotNull
    @Column(name = "rest_interval", nullable = false)
    private Short restInterval;

    public ExerciseWorkout(@NonNull Workout workout,
                           @NonNull Exercise exercise,
                           @NonNull Byte sets,
                           @NonNull Byte repetitions,
                           @NonNull Short restInterval) {
        this.workout = workout;
        this.exercise = exercise;
        this.sets = sets;
        this.repetitions = repetitions;
        this.restInterval = restInterval;
    }

    public ExerciseWorkout(@NonNull Workout workout,
                           @NonNull Exercise exercise,
                           @NonNull ExerciseWorkoutCreateCommand command) {
        this(workout, exercise, command.getSets(), command.getRepetitions(), command.getRestInterval());
    }
}
