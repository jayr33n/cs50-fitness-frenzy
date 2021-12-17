package com.jayr33n.repositories;

import com.jayr33n.domain.Workout;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {}
