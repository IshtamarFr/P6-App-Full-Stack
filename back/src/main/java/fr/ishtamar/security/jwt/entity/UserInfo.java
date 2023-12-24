package fr.ishtamar.security.jwt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=30)
    @Column(unique = true)
    private String name;

    @NotNull
    @Size(max=63)
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(max=60)
    private String password;

    private String roles;

    @ManyToMany
    @JoinTable(
            name="user_topic",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="topic_id")
    )
    Set<Topic> subscriptions;
}
