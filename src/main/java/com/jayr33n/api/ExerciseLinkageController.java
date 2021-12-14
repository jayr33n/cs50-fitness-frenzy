package com.jayr33n.api;

import com.jayr33n.domain.Exercise;
import com.jayr33n.domain.Muscle;
import com.jayr33n.domain.Tool;
import com.jayr33n.exception.EntityLinkageException;
import com.jayr33n.exception.EntityNotFoundException;
import com.jayr33n.repository.ExerciseRepository;
import com.jayr33n.repository.ExerciseWorkoutRepository;
import com.jayr33n.repository.MuscleRepository;
import com.jayr33n.repository.ToolRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;
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
    private final ToolRepository toolRepository;
    private final ExerciseWorkoutRepository exerciseWorkoutRepository;

    @Put("/{exerciseId}/muscles/{muscleId}")
    @Status(HttpStatus.NO_CONTENT)
    @Transactional
    public void addMuscle(Long exerciseId, Long muscleId) {
        var exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(exerciseId, Exercise.class));
        var muscle = muscleRepository.findById(muscleId)
                .orElseThrow(() -> new EntityNotFoundException(muscleId, Muscle.class));
        exercise.getMuscles().add(muscle);
    }

    @Delete("/{exerciseId}/muscles/{muscleId}")
    @Status(HttpStatus.NO_CONTENT)
    @Transactional
    public void removeMuscle(Long exerciseId, Long muscleId) {
        var exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(exerciseId, Exercise.class));
        var muscle = muscleRepository.findById(muscleId)
                .orElseThrow(() -> new EntityNotFoundException(muscleId, Muscle.class));
        if (!exercise.getMuscles().contains(muscle))
            throw new EntityLinkageException();
        exercise.getMuscles().remove(muscle);
    }

    @Put("/{exerciseId}/tools/{toolsId}")
    @Status(HttpStatus.NO_CONTENT)
    @Transactional
    public void addEquipment(Long exerciseId, Long toolsId) {
        var exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(exerciseId, Exercise.class));
        var equipment = toolRepository.findById(toolsId)
                .orElseThrow(() -> new EntityNotFoundException(toolsId, Tool.class));
        exercise.getTools().add(equipment);
    }

    @Delete("/{exerciseId}/tools/{toolsId}")
    @Status(HttpStatus.NO_CONTENT)
    @Transactional
    public void removeEquipment(Long exerciseId, Long toolsId) {
        var exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(exerciseId, Exercise.class));
        var equipment = toolRepository.findById(toolsId)
                .orElseThrow(() -> new EntityNotFoundException(toolsId, Muscle.class));
        if (!exercise.getTools().contains(equipment))
            throw new EntityLinkageException();
        exercise.getTools().remove(equipment);
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        exerciseWorkoutRepository.deleteByExerciseId(id);
        exerciseRepository.deleteById(id);
    }
}
