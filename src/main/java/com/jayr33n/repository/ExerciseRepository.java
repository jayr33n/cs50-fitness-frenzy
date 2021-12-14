package com.jayr33n.repository;

import com.jayr33n.domain.Exercise;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Executable
    Page<Exercise> findByNameLike(String name, Pageable pageable);

    @Query(value = "select * from exercises join exercises_workouts on id = exercise_id where workout_id = :workoutId",
            countQuery = "select count(*) from exercises join exercises_workouts on id = exercise_id where workout_id = :workoutId",
            nativeQuery = true)
    Page<Exercise> findByWorkoutId(@NonNull @NotNull Long workoutId, Pageable pageable);
}
