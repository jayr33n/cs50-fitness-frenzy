package com.jayr33n.api;

import com.jayr33n.commands.create.ExerciseCreateCommand;
import com.jayr33n.commands.read.ExerciseReadCommand;
import com.jayr33n.commands.update.ExerciseUpdateCommand;
import com.jayr33n.domain.Exercise;
import com.jayr33n.domain.Muscle;
import com.jayr33n.domain.Tool;
import com.jayr33n.exception.EntityLinkageException;
import com.jayr33n.exception.EntityNotFoundException;
import com.jayr33n.repositories.ExerciseRepository;
import com.jayr33n.repositories.ExerciseWorkoutRepository;
import com.jayr33n.repositories.MuscleRepository;
import com.jayr33n.repositories.ToolRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RequiredArgsConstructor
@ExecuteOn(TaskExecutors.IO)
@Controller("/exercises")
public class ExerciseController {
    private final ExerciseRepository exerciseRepository;
    private final MuscleRepository muscleRepository;
    private final ToolRepository toolRepository;
    private final ExerciseWorkoutRepository exerciseWorkoutRepository;

    @Get
    public Page<ExerciseReadCommand> get(Pageable pageable) {
        return exerciseRepository.findAll(pageable)
                .map(Exercise::filter);
    }

    @Get("/{id}")
    public ExerciseReadCommand get(Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Exercise.class))
                .filter();
    }

    @Get("/{exerciseId}/tools")
    public Page<Tool> getTools(Long exerciseId, Pageable pageable) {
        if (!exerciseRepository.existsById(exerciseId))
            throw new EntityNotFoundException(exerciseId, Exercise.class);
        return toolRepository.findByExerciseId(exerciseId, pageable);
    }

    @Get("/{exerciseId}/muscles")
    public Page<Muscle> getMuscles(Long exerciseId, Pageable pageable) {
        if (!exerciseRepository.existsById(exerciseId))
            throw new EntityNotFoundException(exerciseId, Exercise.class);
        return muscleRepository.findByExerciseId(exerciseId, pageable);
    }

    @Get("/search")
    public Page<ExerciseReadCommand> byNameLike(@QueryValue String name, Pageable pageable) {
        return exerciseRepository.findByNameLike("%" + name + "%", pageable)
                .map(Exercise::filter);
    }

    @Post
    @Status(HttpStatus.CREATED)
    public void post(@Body @Valid ExerciseCreateCommand command) {
        exerciseRepository.save(new Exercise(command.getName(), command.getDifficulty()));
    }

    @Put("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void put(Long id, @Body ExerciseUpdateCommand command) {
        var exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Exercise.class));
        exerciseRepository.update(exercise.map(command));
    }

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
        if (!exerciseRepository.existsById(id))
            throw new EntityNotFoundException(id, Exercise.class);
        exerciseWorkoutRepository.deleteByExerciseId(id);
        exerciseRepository.deleteById(id);
    }
}
