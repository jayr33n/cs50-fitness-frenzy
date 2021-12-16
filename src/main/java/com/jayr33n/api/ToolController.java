package com.jayr33n.api;

import com.jayr33n.command.ToolCreateCommand;
import com.jayr33n.domain.Tool;
import com.jayr33n.exception.EntityNotFoundException;
import com.jayr33n.repository.ToolRepository;
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
@Controller("/tools")
public class ToolController {
    private final ToolRepository repository;

    @Get
    public Page<Tool> get(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Post
    @Status(HttpStatus.CREATED)
    public void post(@Body @Valid ToolCreateCommand command) {
        repository.save(new Tool(command.getName()));
    }

    @Get("/{id}")
    public Tool get(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Tool.class));
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
