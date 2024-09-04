package com.luetek.academy.publication.repositories;

import com.luetek.academy.publication.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
