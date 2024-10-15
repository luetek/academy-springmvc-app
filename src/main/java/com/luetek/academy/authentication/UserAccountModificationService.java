package com.luetek.academy.authentication;

import com.luetek.academy.authentication.dtos.CreateUserDto;
import com.luetek.academy.authentication.dtos.UserDto;
import com.luetek.academy.authentication.entities.Account;
import com.luetek.academy.authentication.entities.UsernamePasswordCredential;
import com.luetek.academy.authentication.repositories.AccountRepository;
import com.luetek.academy.authentication.repositories.UsernamePasswordCredentialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAccountModificationService {

    private final UsernamePasswordCredentialRepository usernamePasswordCredentialRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserAccountModificationService(UsernamePasswordCredentialRepository usernamePasswordCredentialRepository,
                                          AccountRepository accountRepository,
                                          PasswordEncoder passwordEncoder) {
        this.usernamePasswordCredentialRepository = usernamePasswordCredentialRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUserAccount(CreateUserDto userDto) {
        var cred =  new UsernamePasswordCredential();
        cred.setUsername(userDto.getUsername());
        cred.setPassword(passwordEncoder.encode(userDto.getPassword()));
        var account = new Account();
        account.setDisplayName(userDto.getFullName());
        account.setEmail(userDto.getEmail());
        account.setEnabled("admin".equals(userDto.getUsername()));
        account.setLocked(false);
        account.setExpired(false);
        account.setName(userDto.getUsername());
        var savedAccount = this.accountRepository.save(account);
        cred.setAccount(savedAccount);

        var resEntity = this.usernamePasswordCredentialRepository.save(cred);
        return modelMapper.map(resEntity, UserDto.class);
    }
}
