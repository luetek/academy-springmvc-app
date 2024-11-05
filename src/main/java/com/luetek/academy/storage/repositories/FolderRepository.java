package com.luetek.academy.storage.repositories;

import com.luetek.academy.storage.entities.Folder;

import java.util.Optional;

import com.luetek.academy.storage.entities.StoragePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    @Query("SELECT f FROM StoragePath f WHERE f.parentId = ?1 and f.name = ?2")
    Optional<StoragePath> findFileByParentIdAndName(Long parentId, String name);
}
