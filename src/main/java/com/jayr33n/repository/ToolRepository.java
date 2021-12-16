package com.jayr33n.repository;

import com.jayr33n.domain.Tool;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
    @Query(value = "select * from tools join exercises_tools on id = tool_id where exercise_id = :exerciseId",
            countQuery = "select count(*) from tools join exercises_tools on id = tool_id where exercise_id = :exerciseId",
            nativeQuery = true)
    Page<Tool> findByExerciseId(@NonNull @NotNull Long exerciseId, Pageable pageable);
}
