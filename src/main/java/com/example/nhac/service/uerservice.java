package com.example.nhac.service;

import com.example.nhac.Model.user;
import com.example.nhac.dbo.request.usercreaterequest;
import com.example.nhac.repository.rolerepository;
import com.example.nhac.repository.userrepository;
import com.example.nhac.role;
import groovy.util.logging.Slf4j;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class uerservice  implements UserDetailsService {
    @Autowired
    private userrepository  userrepository;
    @Autowired
    private rolerepository rolerepository;

    public void save(@NotNull user user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userrepository.save(user);
    }
    // Gán vai trò mặc định cho người dùng dựa trên tên người dùng.
    public void setDefaultRole(String username) {
        userrepository.findByUsername(username).ifPresentOrElse(
                user -> {
                    user.getRoles().add(rolerepository.findRoleById(role.USER.value));
                    userrepository.save(user);
                },
                () -> { throw new UsernameNotFoundException("User not found"); }
        );

    }
    // Tải thông tin chi tiết người dùng để xác thực.
    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        var user = userrepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired())
                .disabled(!user.isEnabled())
                .build();
    }
    // Tìm kiếm người dùng dựa trên tên đăng nhập.
    public Optional<user> findByUsername(String username) throws
            UsernameNotFoundException {
        return userrepository.findByUsername(username);
    }
}
