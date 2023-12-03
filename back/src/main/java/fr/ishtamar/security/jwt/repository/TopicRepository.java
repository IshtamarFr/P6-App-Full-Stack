package fr.ishtamar.security.jwt.repository;

import fr.ishtamar.security.jwt.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic,Long> {
}
