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
public class CommentDto {
    private Long id;

    @NotNull
    @Size(max=500)
    private String content;

    @NotNull
    private Long author_id;
    private String author_name;

    @NotNull
    private Long article_id;

    private Date createdAt=new Date();
}
