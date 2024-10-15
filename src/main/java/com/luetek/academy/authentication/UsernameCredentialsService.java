package com.luetek.academy.authentication;

import com.luetek.academy.authentication.repositories.AccountRepository;
import com.luetek.academy.authentication.repositories.UsernamePasswordCredentialRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Implement UserDetailsService as per spring security mechanism
 * */
public class UsernameCredentialsService implements UserDetailsService {

    private final UsernamePasswordCredentialRepository usernamePasswordCredentialRepository;

    public UsernameCredentialsService(UsernamePasswordCredentialRepository usernamePasswordCredentialRepository) {
        this.usernamePasswordCredentialRepository = usernamePasswordCredentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userOpt = usernamePasswordCredentialRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException(username + "not found");
        }
        return userOpt.get();
    }
}
