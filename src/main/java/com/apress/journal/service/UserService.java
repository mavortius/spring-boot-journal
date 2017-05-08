package com.apress.journal.service;

import com.apress.journal.domain.Role;
import com.apress.journal.domain.RoleAuthority;
import com.apress.journal.domain.User;
import com.apress.journal.repository.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                        String.format("Login failed for username %s",
                                                username)
                                ));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(rolesToStringArray(user.getRoles()))
        );
    }

    public void save(User user) {
        if(user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        repository.save(user);
    }

    public String[] rolesToStringArray(List<Role> roles) {
        List<String> authorities = new ArrayList<>();

        for(Role r : roles) {
            authorities.add(r.getAuthority().name());
        }

        return authorities.toArray(new String[authorities.size()]);
    }
}
