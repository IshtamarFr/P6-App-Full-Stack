package fr.ishtamar.security.jwt.service;

import fr.ishtamar.security.jwt.entity.Topic;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;

import java.util.List;

public interface TopicService {
    Topic getTopicById(final Long id) throws EntityNotFoundException;

    List<Topic> getAllTopics();
}
