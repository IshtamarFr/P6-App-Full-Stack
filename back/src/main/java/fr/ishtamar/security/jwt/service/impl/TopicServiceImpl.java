package fr.ishtamar.security.jwt.service.impl;

import fr.ishtamar.security.jwt.entity.Topic;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.security.jwt.repository.TopicRepository;
import fr.ishtamar.security.jwt.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository repository;

    @Override
    public List<Topic> getAllTopics() {return repository.findAll();}

    @Override
    public Topic getTopicById(final Long id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Topic.class,"id",id.toString()));
    }
}
