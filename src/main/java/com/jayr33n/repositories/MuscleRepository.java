package com.jayr33n.repositories;

import com.jayr33n.domain.Muscle;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Repository
public interface MuscleRepository extends JpaRepository<Muscle, Long> {
    @Query(value = "select * from muscles join exercises_muscles on id = muscle_id where exercise_id = :exerciseId",
            countQuery = "select count(*) from muscles join exercises_muscles on id = muscle_id where exercise_id = :exerciseId",
            nativeQuery = true)
    Page<Muscle> findByExerciseId(@NonNull @NotNull Long exerciseId, Pageable pageable);
}
