package com.jayr33n.api;

import com.jayr33n.commands.create.MuscleCreateCommand;
import com.jayr33n.domain.Muscle;
import com.jayr33n.exception.EntityNotFoundException;
import com.jayr33n.repositories.MuscleRepository;
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
@Controller("/muscles")
public class MuscleController {
    private final MuscleRepository repository;

    @Get
    public Page<Muscle> get(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Get("/{id}")
    public Muscle get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Muscle.class));
    }

    @Post
    @Status(HttpStatus.CREATED)
    public void post(@Body @Valid MuscleCreateCommand command) {
        repository.save(new Muscle(command.getName()));
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException(id, Muscle.class);
        repository.deleteById(id);
    }
}
