package com.crookedcoder.habitjournal.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceNoSql implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }
    
    // private final UserRepository userRepository;
    
    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     // TODO Auto-generated method stub
    //     User user = userRepository.findByUsername(username);
    //     if (user == null) {
    //         throw new UsernameNotFoundException(username);
    //     }
    //     return User.wtihUsername(user.GetUsername()).password(user.getPassword()).roles("USER");
    // }
    
    
}