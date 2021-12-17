package com.jayr33n;

import com.jayr33n.domain.Difficulty;
import com.jayr33n.domain.Exercise;
import com.jayr33n.domain.Muscle;
import com.jayr33n.domain.Tool;
import com.jayr33n.repositories.ExerciseRepository;
import com.jayr33n.repositories.MuscleRepository;
import com.jayr33n.repositories.ToolRepository;
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
    }
}
