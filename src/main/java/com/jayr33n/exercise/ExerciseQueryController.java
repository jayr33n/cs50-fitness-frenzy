package com.jayr33n.exercise;

import com.jayr33n.domain.Exercise;
import com.jayr33n.repository.ExerciseRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@ExecuteOn(TaskExecutors.IO)
@Controller("/exercises/search")
public class ExerciseQueryController {
    private final ExerciseRepository repository;

    @Get
    public Page<Exercise> byNameLike(@QueryValue String name, Pageable pageable) {
        return repository.findByNameLike("%" + name + "%", pageable);
    }
}
