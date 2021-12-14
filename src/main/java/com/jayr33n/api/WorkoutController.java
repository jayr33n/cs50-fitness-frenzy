package com.jayr33n.api;

import com.jayr33n.command.WorkoutCreateCommand;
import com.jayr33n.domain.Workout;
import com.jayr33n.exception.EntityNotFoundException;
import com.jayr33n.repository.WorkoutRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RequiredArgsConstructor
@ExecuteOn(TaskExecutors.IO)
@Controller("/workouts")
public class WorkoutController {
    private final WorkoutRepository repository;

    @Get
    public Page<Workout> get(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Post
    @Status(HttpStatus.CREATED)
    public void post(@Body @Valid WorkoutCreateCommand command) {
        repository.save(new Workout(command.getName()));
    }

    @Get("/{id}")
    public Workout get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Workout.class));
    }
}
