package fr.ishtamar.security.jwt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Topic {
    /*
        Topic won't get a DTO as it could be like an ENUM, but we want to be able to change
        topic's name without problem, thus the independent ID
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=60)
    @Column(unique = true)
    private String name;

    @Size(max=200)
    private String description;
}
