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

import javax.transaction.Transactional;
import java.util.List;

/**
 * @deprecated This class is only for testing purposes
 */
@Deprecated(forRemoval = true)
@RequiredArgsConstructor
@Singleton
public class MockDataInitializer {
    private final ExerciseRepository exerciseRepository;
    private final MuscleRepository muscleRepository;
    private final ToolRepository toolRepository;

    /**
     * @see <a href="https://www.acefitness.org">https://www.acefitness.org</a>
     */
    @EventListener
    @Transactional
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
                new Tool("Stability Ball"),
                new Tool("Weight Machines")));

        // Ugly code since Java does not support object destructuring
        var russianTwist = getExercise(1L);
        var trxSingleArmRow = getExercise(2L);
        var chestPress = getExercise(3L);
        var seatedOverheadPress = getExercise(4L);
        var bicepCurl = getExercise(5L);
        var abs = getMuscle(1L);
        var back = getMuscle(2L);
        var chest = getMuscle(3L);
        var shoulders = getMuscle(4L);
        var arms = getMuscle(5L);
        var legs = getMuscle(6L);
        var barbell = getTool(1L);
        var dumbbells = getTool(2L);
        var bench = getTool(4L);
        var trx = getTool(5L);
        var stabilityBall = getTool(6L);

        russianTwist.getMuscles().addAll(List.of(abs, legs));
        russianTwist.getTools().add(stabilityBall);

        trxSingleArmRow.getMuscles().addAll(List.of(abs, back, arms));
        trxSingleArmRow.getTools().add(trx);

        chestPress.getMuscles().addAll(List.of(arms, chest, shoulders));
        chestPress.getTools().addAll(List.of(barbell, bench));

        seatedOverheadPress.getMuscles().addAll(List.of(arms, shoulders));
        seatedOverheadPress.getTools().addAll(List.of(bench, dumbbells));

        bicepCurl.getMuscles().add(arms);
        bicepCurl.getTools().add(barbell);
    }

    private Exercise getExercise(Long id) {
        return exerciseRepository.findById(id).orElseThrow();
    }

    private Muscle getMuscle(Long id) {
        return muscleRepository.findById(id).orElseThrow();
    }

    private Tool getTool(Long id) {
        return toolRepository.findById(id).orElseThrow();
    }
}
