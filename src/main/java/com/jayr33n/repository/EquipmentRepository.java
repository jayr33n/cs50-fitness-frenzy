package com.jayr33n.repository;

import com.jayr33n.domain.Equipment;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {}
