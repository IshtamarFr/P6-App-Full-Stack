package fr.ishtamar.security.jwt.repository;

import fr.ishtamar.security.jwt.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTopicIdOrderByCreatedAtDesc(Long topic_id);

    List<Article> findByTopicIdInOrderByCreatedAtDesc(List<Long> topic_ids);
}
