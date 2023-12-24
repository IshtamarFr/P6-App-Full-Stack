package fr.ishtamar.security.jwt.service;

import fr.ishtamar.security.jwt.entity.Comment;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;

import java.util.List;

public interface CommentService {

    /**
     * Fetches all comments for article with given id
     * @param id Long id for article
     * @return List of all comments for given article
     */
    List<Comment> getAllCommentsWithArticleId(final Long id);

    /**
     * Tries to create and save a comment
     * @param comment Comment to be created
     * @throws EntityNotFoundException article Id not found
     */
    void saveComment(Comment comment) throws EntityNotFoundException;
}
