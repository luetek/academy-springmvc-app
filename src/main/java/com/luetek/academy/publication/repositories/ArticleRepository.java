package com.luetek.academy.publication.repositories;

import com.luetek.academy.publication.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT f FROM Article f WHERE f.parentId = ?1 and f.name = ?2")
    Optional<Article> findByParentIdAndName(Long parentId, String name);
}
