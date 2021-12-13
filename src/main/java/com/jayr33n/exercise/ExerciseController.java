package com.jayr33n.exercise;

import com.jayr33n.domain.Exercise;
import com.jayr33n.exception.EntityNotFoundException;
import com.jayr33n.repository.ExerciseRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
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
    public HttpResponse<Exercise> post(@Body @Valid ExerciseCreateCommand command) {
        var exercise = repository.save(new Exercise(command.getName(), command.getDifficulty()));
        return HttpResponse.created(exercise)
                .header(HttpHeaders.LOCATION, "/exercises/" + exercise.getId());
    }

    @Get("/{id}")
    public Exercise get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Exercise.class));
    }

    @Put("/{id}")
    public HttpResponse<?> put(Long id, @Body ExerciseUpdateCommand command) {
        var exercise = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Exercise.class));
        repository.update(exercise.map(command));
        return HttpResponse.noContent()
                .header(HttpHeaders.LOCATION, "/exercises/" + exercise.getId());
    }

    @Delete("/{id}")
    public HttpResponse<?> delete(Long id) {
        repository.deleteById(id);
        return HttpResponse.noContent();
    }
}
