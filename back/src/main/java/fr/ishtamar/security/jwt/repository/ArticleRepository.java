package fr.ishtamar.security.jwt.repository;

import fr.ishtamar.security.jwt.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query(value = "SELECT * FROM Article WHERE topic_id = ?1 ORDER BY createdAt DESC", nativeQuery = true)
    List<Article> findAllWithTopicId(Long topic_id);

    @Query(value = "SELECT * FROM Article WHERE topic_id IN ?1 ORDER BY createdAt DESC", nativeQuery = true)
    List<Article> findAllInTopicIds(List<Long> topic_ids);
}
