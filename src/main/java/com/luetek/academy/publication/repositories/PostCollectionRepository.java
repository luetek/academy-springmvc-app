package com.luetek.academy.publication.repositories;

import com.luetek.academy.publication.entities.PostCollection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCollectionRepository extends JpaRepository<PostCollection, Long> {
}
