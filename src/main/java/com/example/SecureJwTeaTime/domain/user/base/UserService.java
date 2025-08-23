package com.example.SecureJwTeaTime.domain.user.base;

import com.example.SecureJwTeaTime.exceptions.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByEmailIgnoreCase(username)
        .orElseThrow(
            () -> new UserNotFoundException(String.format("Username:%s not found", username)));
  }
}
