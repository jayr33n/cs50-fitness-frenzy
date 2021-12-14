package com.jayr33n.api;

import com.jayr33n.command.ExerciseCreateCommand;
import com.jayr33n.command.ExerciseUpdateCommand;
import com.jayr33n.domain.Exercise;
import com.jayr33n.exception.EntityNotFoundException;
import com.jayr33n.repository.ExerciseRepository;
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
@Controller("/exercises")
public class ExerciseController {
    private final ExerciseRepository repository;

    @Get
    public Page<Exercise> get(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Post
    @Status(HttpStatus.CREATED)
    public void post(@Body @Valid ExerciseCreateCommand command) {
        repository.save(new Exercise(command.getName(), command.getDifficulty()));
    }

    @Get("/{id}")
    public Exercise get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Exercise.class));
    }

    @Put("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void put(Long id, @Body ExerciseUpdateCommand command) {
        var exercise = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Exercise.class));
        repository.update(exercise.map(command));
    }
}
