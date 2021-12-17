package com.jayr33n.domain.keys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Embeddable
public class ExerciseWorkoutKey implements Serializable {
    @NotNull
    @Column(name = "workout_id", nullable = false)
    private Long workoutId;

    @NotNull
    @Column(name = "exercise_id", nullable = false)
    private Long exerciseId;
}
