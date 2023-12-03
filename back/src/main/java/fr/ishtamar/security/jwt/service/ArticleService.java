package fr.ishtamar.security.jwt.service;


import fr.ishtamar.security.jwt.entity.Article;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;

import java.util.List;

public interface ArticleService {
    Article getArticleById(final Long id) throws EntityNotFoundException;

    List<Article> getAllArticlesWithTopicId(final Long id);

    List<Article> getAllArticlesInTopicIds(final List<Long> ids);

    void saveArticle(Article article) throws EntityNotFoundException;
}
