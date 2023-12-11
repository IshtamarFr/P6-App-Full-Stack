package fr.ishtamar.security.jwt.service;

import fr.ishtamar.security.jwt.entity.Topic;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;

import java.util.List;

public interface TopicService {
    /**
     * Tries to get topic by its id
     * @param id Long id for topic
     * @return Given topic
     * @throws EntityNotFoundException
     */
    Topic getTopicById(final Long id) throws EntityNotFoundException;

    /**
     * Fetchs all existing topics
     * @return List of all Topics
     */
    List<Topic> getAllTopics();
}
