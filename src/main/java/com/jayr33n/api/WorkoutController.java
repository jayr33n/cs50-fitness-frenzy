package com.jayr33n.api;

import com.jayr33n.commands.create.ExerciseWorkoutCreateCommand;
import com.jayr33n.commands.create.WorkoutCreateCommand;
import com.jayr33n.commands.read.WorkoutReadCommand;
import com.jayr33n.domain.Exercise;
import com.jayr33n.domain.ExerciseWorkout;
import com.jayr33n.domain.Workout;
import com.jayr33n.exception.EntityNotFoundException;
import com.jayr33n.repositories.ExerciseRepository;
import com.jayr33n.repositories.ExerciseWorkoutRepository;
import com.jayr33n.repositories.WorkoutRepository;
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
@Controller("/workouts")
public class WorkoutController {
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseWorkoutRepository linkageRepository;

    @Get
    public Page<WorkoutReadCommand> get(Pageable pageable) {
        return workoutRepository.findAll(pageable)
                .map(Workout::filter);
    }

    @Get("/{id}")
    public WorkoutReadCommand get(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Workout.class))
                .filter();
    }

    @Get("/{workoutId}/exercises")
    public Page<Exercise> get(Long workoutId, Pageable pageable) {
        if (!workoutRepository.existsById(workoutId))
            throw new EntityNotFoundException(workoutId, Workout.class);
        return exerciseRepository.findByWorkoutId(workoutId, pageable);
    }

    @Post
    @Status(HttpStatus.CREATED)
    @Transactional
    public void post(@Body @Valid WorkoutCreateCommand command) {
        var workout = workoutRepository.saveAndFlush(new Workout(command.getName()));
        for (var exerciseWorkoutCreateCommand : command.getExercises()) {
            assert exerciseWorkoutCreateCommand.getExerciseId() != null;
            var exercise = exerciseRepository.findById(exerciseWorkoutCreateCommand.getExerciseId())
                    .orElseThrow(() ->
                            new EntityNotFoundException(exerciseWorkoutCreateCommand.getExerciseId(), Exercise.class));
            linkageRepository.save(new ExerciseWorkout(workout, exercise, exerciseWorkoutCreateCommand));
        }
    }

    @Put("/{workoutId}/exercises/{exerciseId}")
    @Status(HttpStatus.NO_CONTENT)
    @Transactional
    public void addExercise(Long workoutId, Long exerciseId, @Body @Valid ExerciseWorkoutCreateCommand command) {
        var workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException(workoutId, Workout.class));
        var exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(exerciseId, Exercise.class));
        linkageRepository.save(new ExerciseWorkout(workout, exercise, command));
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        if (!workoutRepository.existsById(id))
            throw new EntityNotFoundException(id, Workout.class);
        linkageRepository.deleteByWorkoutId(id);
        workoutRepository.deleteById(id);
    }
}
