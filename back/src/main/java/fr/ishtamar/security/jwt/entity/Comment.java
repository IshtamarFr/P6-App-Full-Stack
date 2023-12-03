package fr.ishtamar.security.jwt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=500)
    private String content;

    @ManyToOne
    @JoinColumn(name="author_id",referencedColumnName = "id")
    private UserInfo author;

    @ManyToOne
    @JoinColumn(name="article_id",referencedColumnName = "id")
    private Article article;

    @CreatedDate
    private Date created_at=new Date();
}
