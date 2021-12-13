package com.jayr33n.repository;

import com.jayr33n.domain.Muscle;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface MuscleRepository extends JpaRepository<Muscle, Long> {}
