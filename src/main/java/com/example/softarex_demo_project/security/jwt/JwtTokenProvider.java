package com.example.softarex_demo_project.security.jwt;

import com.example.softarex_demo_project.dto.AuthenticationDto;
import com.example.softarex_demo_project.model.exceptions.security.AccessDeniedException;
import com.example.softarex_demo_project.model.user.User;
import com.example.softarex_demo_project.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * This class is a token provider.
 *
 * @author Ilya Lisov
 * @version 1.0
 */
@Component
public class JwtTokenProvider {
    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.accessToken}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.token.refreshToken}")
    private long refreshTokenValidityInMilliseconds;

    private UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final Map<UUID, String> refreshTokensStorage;

    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.refreshTokensStorage = new HashMap<>();
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(UUID userId, String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        claims.put("userId", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String createRefreshToken(UUID userId, String username) {
        if (refreshTokensStorage.containsKey(userId) && validateToken(refreshTokensStorage.get(userId))) {
            return refreshTokensStorage.get(userId);
        }
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userId", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        refreshTokensStorage.put(userId, token);
        return token;
    }

    public AuthenticationDto refreshTokens(String refreshToken) {
        AuthenticationDto authenticationDto = new AuthenticationDto();
        if (validateToken(refreshToken)) {
            UUID userId = UUID.fromString(getUserId(refreshToken));
            Optional<User> user = userRepository.findById(userId);
            user
                    .map((u) -> {
                        authenticationDto.setUserId(u.getId());
                        authenticationDto.setUsername(u.getUsername());
                        authenticationDto.setToken(createToken(u.getId(), u.getUsername(), u.getRoles()));
                        authenticationDto.setRefreshToken(refreshToken);
                        return user;
                    })
                    .orElseThrow(AccessDeniedException::new);
            return authenticationDto;
        } else {
            throw new AccessDeniedException();
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("userId").toString();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7);
        }
        return "";
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
