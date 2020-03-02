package com.example.springessentials.service;

import com.example.springessentials.model.APIUser;
import com.example.springessentials.repository.APIUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private APIUserRepository APIUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        APIUser user = Optional.ofNullable(APIUserRepository.findByUsername(username)).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isAdmin() ? authorityListAdmin : authorityListUser);
    }
}
