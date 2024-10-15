package com.luetek.academy.publication.repositories;

import com.luetek.academy.publication.entities.PostCollection;
import com.luetek.academy.storage.entities.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostCollectionRepository extends JpaRepository<PostCollection, Long> {

    Optional<PostCollection> findByName(String name);
}
