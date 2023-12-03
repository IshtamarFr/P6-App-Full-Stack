package fr.ishtamar.security.jwt.service.impl;

import fr.ishtamar.security.jwt.entity.Article;
import fr.ishtamar.security.jwt.exceptionhandler.EntityNotFoundException;
import fr.ishtamar.security.jwt.repository.ArticleRepository;
import fr.ishtamar.security.jwt.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository repository;

    @Override
    public Article getArticleById(final Long id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Article.class,"id",id.toString()));
    }

    @Override
    public List<Article> getAllArticlesWithTopicId(final Long id) {
        return repository.findAllWithTopicId(id);
    }

    @Override
    public List<Article> getAllArticlesInTopicIds(final List<Long> ids) {
        return repository.findAllInTopicIds(ids);
    }

    @Override
    public void saveArticle(Article article) throws EntityNotFoundException {repository.save(article);}
}
