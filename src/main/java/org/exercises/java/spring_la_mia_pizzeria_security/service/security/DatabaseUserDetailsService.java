package org.exercises.java.spring_la_mia_pizzeria_security.service.security;

import org.exercises.java.spring_la_mia_pizzeria_security.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;

    public DatabaseUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(DatabaseUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

}
