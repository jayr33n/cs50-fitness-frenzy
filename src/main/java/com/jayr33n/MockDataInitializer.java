package com.jayr33n;

import com.jayr33n.domain.*;
import com.jayr33n.repository.ExerciseRepository;
import com.jayr33n.repository.MuscleRepository;
import com.jayr33n.repository.ToolRepository;
import com.jayr33n.repository.WorkoutRepository;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Singleton
public class MockDataInitializer {
    private final ExerciseRepository exerciseRepository;
    private final MuscleRepository muscleRepository;
    private final ToolRepository toolRepository;
    private final WorkoutRepository workoutRepository;

    @EventListener
    public void onStartupEvent(StartupEvent event) {
        exerciseRepository.saveAll(List.of(
                new Exercise("Russian Twist", Difficulty.ADVANCED),
                new Exercise("TRX Single-arm Row", Difficulty.ADVANCED),
                new Exercise("Chest Press", Difficulty.BEGINNER),
                new Exercise("Seated Overhead Press", Difficulty.INTERMEDIATE),
                new Exercise("Bicep Curl", Difficulty.INTERMEDIATE)));
        muscleRepository.saveAll(List.of(
                new Muscle("Abs"),
                new Muscle("Back"),
                new Muscle("Chest"),
                new Muscle("Shoulders"),
                new Muscle("Arms"),
                new Muscle("Legs")));
        toolRepository.saveAll(List.of(
                new Tool("Barbell"),
                new Tool("Dumbbells"),
                new Tool("Cables"),
                new Tool("Bench"),
                new Tool("TRX"),
                new Tool("Weight Machines")));
        workoutRepository.saveAll(List.of(
                new Workout("Power 90 Schedule"),
                new Workout("Hit Workout"),
                new Workout("Spell Your Name Workout")));
    }
}
