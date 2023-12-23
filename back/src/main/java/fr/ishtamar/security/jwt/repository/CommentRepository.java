package fr.ishtamar.security.jwt.repository;

import fr.ishtamar.security.jwt.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleIdOrderByCreatedAtDesc(Long id);
}
