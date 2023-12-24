package fr.ishtamar.security.jwt.service;


import fr.ishtamar.security.jwt.entity.Article;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;

import java.util.List;

public interface ArticleService {
    /**
     * Tries to find article corresponding to id
     * @param id Long id for article
     * @return Article corresponding
     * @throws EntityNotFoundException Article id not found
     */
    Article getArticleById(final Long id) throws EntityNotFoundException;

    /**
     * Gets all articles corresponding to given topic id
     * @param id Long id for topic
     * @return List of all corresponding articles
     */
    List<Article> getAllArticlesWithTopicId(final Long id);

    /**
     * Gets all articles corresponding to given topic ids
     * @param ids List of Long id for topics
     * @return List of all corresponding articles
     */
    List<Article> getAllArticlesInTopicIds(final List<Long> ids);

    /**
     * Saves article for create or modify
     * @param article Article to be saved
     * @throws EntityNotFoundException Article Id not found
     */
    void saveArticle(Article article) throws EntityNotFoundException;
}
