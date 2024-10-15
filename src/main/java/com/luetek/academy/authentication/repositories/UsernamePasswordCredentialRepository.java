package com.luetek.academy.authentication.repositories;

import com.luetek.academy.authentication.entities.UsernamePasswordCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsernamePasswordCredentialRepository  extends JpaRepository<UsernamePasswordCredential, Long> {

    @Query("SELECT u FROM UsernamePasswordCredential u WHERE u.username = ?1")
    Optional<UsernamePasswordCredential> findByUsername(String username);
}
