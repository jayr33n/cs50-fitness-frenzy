package com.jayr33n.muscle;

import com.jayr33n.domain.Muscle;
import com.jayr33n.exception.EntityNotFoundException;
import com.jayr33n.repository.MuscleRepository;
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
@Controller("/muscles")
public class MuscleController {
    private final MuscleRepository repository;

    @Get
    public Page<Muscle> get(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Post
    public HttpResponse<Muscle> post(@Body @Valid MuscleCreateCommand command) {
        var muscle = repository.save(new Muscle(command.getName()));
        return HttpResponse.created(muscle)
                .header(HttpHeaders.LOCATION, "/muscles/" + muscle.getId());
    }

    @Get("/{id}")
    public Muscle get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Muscle.class));
    }

    @Delete("/{id}")
    public HttpResponse<?> delete(Long id) {
        repository.deleteById(id);
        return HttpResponse.noContent();
    }
}
