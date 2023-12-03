package fr.ishtamar.security.jwt.service.impl;

import fr.ishtamar.security.jwt.entity.Comment;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.security.jwt.repository.CommentRepository;
import fr.ishtamar.security.jwt.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository repository;

    @Override
    public List<Comment> getAllCommentsWithArticleId(final Long id) {
        return repository.findAllWithArticleId(id);
    }

    @Override
    public void saveComment(Comment comment) throws EntityNotFoundException {repository.save(comment);}
}
