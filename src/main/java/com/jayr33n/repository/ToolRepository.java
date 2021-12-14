package com.jayr33n.repository;

import com.jayr33n.domain.Tool;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {}
