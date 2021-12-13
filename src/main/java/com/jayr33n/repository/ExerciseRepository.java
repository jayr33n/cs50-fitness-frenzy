package com.jayr33n.repository;

import com.jayr33n.domain.Exercise;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Page<Exercise> findByNameLike(String name, Pageable pageable);
}
