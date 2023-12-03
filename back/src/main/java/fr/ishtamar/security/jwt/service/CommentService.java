package fr.ishtamar.security.jwt.service;

import fr.ishtamar.security.jwt.entity.Comment;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;

import java.util.List;

public interface CommentService {

    List<Comment> getAllCommentsWithArticleId(final Long id);

    void saveComment(Comment comment) throws EntityNotFoundException;
}
