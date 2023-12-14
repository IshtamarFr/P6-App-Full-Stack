package fr.ishtamar.security.jwt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {
    private Long id;

    @NotNull
    @Size(max=60)
    private String title;

    @NotNull
    @Size(max=500)
    private String content;

    private Long author_id;
    private String author_name;

    private Long topic_id;
    private String topic_name;

    private Date createdAt;
}
