package com.threembank.infrastructure.security.user;

import com.threembank.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class BasicUserDetailsProvider implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public BasicUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new BasicUserDetails(user.getId(),user.getEmail(), user.getPassword(), new HashSet<>(user.getAuthorities()));
    }
}
