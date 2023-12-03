package fr.ishtamar.security.jwt.repository;

import fr.ishtamar.security.jwt.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM Comment WHERE article_id = ?1 ORDER BY created_at DESC", nativeQuery = true)
    List<Comment> findAllWithArticleId(Long id);
}
