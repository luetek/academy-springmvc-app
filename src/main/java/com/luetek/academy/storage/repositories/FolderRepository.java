package com.luetek.academy.storage.repositories;

import com.luetek.academy.storage.entities.Folder;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    Optional<Folder> findByName(String name);
}
