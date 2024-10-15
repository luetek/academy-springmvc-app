package com.luetek.academy.authentication.repositories;

import com.luetek.academy.authentication.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<Account, Long> {

}
