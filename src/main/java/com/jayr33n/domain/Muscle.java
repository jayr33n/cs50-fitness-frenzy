package com.jayr33n.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.core.annotation.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "muscles")
public class Muscle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "muscles")
    @ToString.Exclude
    @JsonIgnore
    private Set<Exercise> exercises = new HashSet<>();

    public Muscle(@NonNull String name) {
        this.name = name;
    }
}
