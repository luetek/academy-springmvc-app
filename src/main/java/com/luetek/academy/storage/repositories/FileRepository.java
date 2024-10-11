package com.luetek.academy.storage.repositories;

import com.luetek.academy.storage.entities.StoragePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<StoragePath, Long> {

    @Query("SELECT f FROM File f WHERE f.parentId = ?1 and f.name = ?2")
    Optional<StoragePath> findFileByParentIdAndName(Long parentId, String name);
}
