package com.jayr33n.repositories;

import com.jayr33n.domain.Exercise;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Executable
    Page<Exercise> findByNameLike(String name, Pageable pageable);
}
