package com.jayr33n.repository;

import com.jayr33n.domain.ExerciseWorkout;
import com.jayr33n.domain.ExerciseWorkoutKey;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Repository
public interface ExerciseWorkoutRepository extends JpaRepository<ExerciseWorkout, ExerciseWorkoutKey> {
    @Query("delete from ExerciseWorkout where workout.id = :workoutId")
    void deleteByWorkoutId(@NonNull @NotNull Long workoutId);

    @Query("delete from ExerciseWorkout where exercise.id = :exerciseId")
    void deleteByExerciseId(@NonNull @NotNull Long exerciseId);
}
