package com.jayr33n.equipment;

import com.jayr33n.domain.Equipment;
import com.jayr33n.exception.EntityNotFoundException;
import com.jayr33n.repository.EquipmentRepository;
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
@Controller("/equipment")
public class EquipmentController {
    private final EquipmentRepository repository;

    @Get
    public Page<Equipment> get(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Post
    public HttpResponse<Equipment> post(@Body @Valid EquipmentCreateCommand command) {
        var equipment = repository.save(new Equipment(command.getName()));
        return HttpResponse.created(equipment)
                .header(HttpHeaders.LOCATION, "/equipment/" + equipment.getId());
    }

    @Get("/{id}")
    public Equipment get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Equipment.class));
    }

    @Delete("/{id}")
    public HttpResponse<?> delete(Long id) {
        repository.deleteById(id);
        return HttpResponse.noContent();
    }
}
