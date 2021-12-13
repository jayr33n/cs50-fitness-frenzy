package com.jayr33n.exercise;

import com.jayr33n.domain.Equipment;
import com.jayr33n.domain.Exercise;
import com.jayr33n.domain.Muscle;
import com.jayr33n.exception.EntityLinkageException;
import com.jayr33n.exception.EntityNotFoundException;
import com.jayr33n.repository.EquipmentRepository;
import com.jayr33n.repository.ExerciseRepository;
import com.jayr33n.repository.MuscleRepository;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Put;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor

@ExecuteOn(TaskExecutors.IO)
@Controller("/exercises")
public class ExerciseLinkageController {
    private final ExerciseRepository exerciseRepository;
    private final MuscleRepository muscleRepository;
    private final EquipmentRepository equipmentRepository;

    @Put("/{exerciseId}/muscles/{muscleId}")
    @Transactional
    public HttpResponse<?> addMuscle(Long exerciseId, Long muscleId) {
        var exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(exerciseId, Exercise.class));
        var muscle = muscleRepository.findById(muscleId)
                .orElseThrow(() -> new EntityNotFoundException(muscleId, Muscle.class));
        exercise.getMuscles().add(muscle);
        return HttpResponse.noContent()
                .header(HttpHeaders.LOCATION, "/exercises/" + exercise.getId());
    }

    @Delete("/{exerciseId}/muscles/{muscleId}")
    @Transactional
    public HttpResponse<?> removeMuscle(Long exerciseId, Long muscleId) {
        var exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(exerciseId, Exercise.class));
        var muscle = muscleRepository.findById(muscleId)
                .orElseThrow(() -> new EntityNotFoundException(muscleId, Muscle.class));
        if (!exercise.getMuscles().contains(muscle))
            throw new EntityLinkageException();
        exercise.getMuscles().remove(muscle);
        return HttpResponse.noContent();
    }

    @Put("/{exerciseId}/equipment/{equipmentId}")
    @Transactional
    public HttpResponse<?> addEquipment(Long exerciseId, Long equipmentId) {
        var exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(exerciseId, Exercise.class));
        var equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EntityNotFoundException(equipmentId, Equipment.class));
        exercise.getEquipment().add(equipment);
        return HttpResponse.noContent()
                .header(HttpHeaders.LOCATION, "/exercises/" + exercise.getId());
    }

    @Delete("/{exerciseId}/equipment/{equipmentId}")
    @Transactional
    public HttpResponse<?> removeEquipment(Long exerciseId, Long equipmentId) {
        var exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(exerciseId, Exercise.class));
        var equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new EntityNotFoundException(equipmentId, Muscle.class));
        if (!exercise.getEquipment().contains(equipment))
            throw new EntityLinkageException();
        exercise.getEquipment().remove(equipment);
        return HttpResponse.noContent();
    }
}
