package com.internship.tmontica.service;

import com.internship.tmontica.dto.User;
import com.internship.tmontica.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public int addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.addUser(user);
    }

    @Transactional(readOnly = true)
    public User getUserByUserId(String id){
        return userDao.getUserByUserId(id);
    }

    @Transactional(readOnly = true)
    public String getUserPasswordByUserId(String id){ return userDao.getPasswordByUserId(id);}

    @Transactional
    public int updateUserPassword(User user){
        return userDao.updateUserPassword(user);
    }

    @Transactional
    public int deleteUser(String id){
        return userDao.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userDao.getUserByUserId(userId);
        List<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority("user"));
        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(),authorities);
//        UserDetails userDetails = new UserDetails() {
//            @Override
//            public Collection<? extends GrantedAuthority> getAuthorities() {
//                List<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority("USER"));
//                return authorities;
//            }
//
//            @Override
//            public String getPassword() {
//                return user.getPassword();
//            }
//
//            @Override
//            public String getUsername() {
//                return user.getId();
//            }
//
//            @Override
//            public boolean isAccountNonExpired() {
//                return true;
//            }
//
//            @Override
//            public boolean isAccountNonLocked() {
//                return true;
//            }
//
//            @Override
//            public boolean isCredentialsNonExpired() {
//                return true;
//            }
//
//            @Override
//            public boolean isEnabled() {
//                return true;
//            }
//        };
//
//        return userDetails;
    }
}
