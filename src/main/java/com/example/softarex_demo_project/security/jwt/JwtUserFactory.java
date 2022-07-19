package com.example.softarex_demo_project.security.jwt;

import com.example.softarex_demo_project.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is a factory of JwtUser objects.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getPhoneNumber(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())),
                true,
                user.getUpdated()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> userRoles) {
        return userRoles.stream()
                .map(SimpleGrantedAuthority::new).
                collect(Collectors.toList());
    }
}
