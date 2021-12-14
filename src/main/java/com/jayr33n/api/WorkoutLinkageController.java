package com.jayr33n.api;

import com.jayr33n.command.ExerciseWorkoutCreateCommand;
import com.jayr33n.domain.Exercise;
import com.jayr33n.domain.ExerciseWorkout;
import com.jayr33n.domain.Workout;
import com.jayr33n.exception.EntityNotFoundException;
import com.jayr33n.repository.ExerciseRepository;
import com.jayr33n.repository.ExerciseWorkoutRepository;
import com.jayr33n.repository.WorkoutRepository;
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
public class WorkoutLinkageController {
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseWorkoutRepository linkageRepository;

    @Get("/{workoutId}/exercises")
    public Page<Exercise> get(Long workoutId, Pageable pageable) {
        return exerciseRepository.findByWorkoutId(workoutId, pageable);
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
        linkageRepository.deleteByWorkoutId(id);
        workoutRepository.deleteById(id);
    }
}
