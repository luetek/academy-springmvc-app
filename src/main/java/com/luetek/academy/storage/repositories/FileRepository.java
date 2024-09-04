package com.luetek.academy.storage.repositories;

import com.luetek.academy.storage.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
