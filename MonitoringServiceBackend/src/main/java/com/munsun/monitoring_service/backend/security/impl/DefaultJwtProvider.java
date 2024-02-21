package com.munsun.monitoring_service.backend.security.impl;

import com.munsun.monitoring_service.backend.security.JwtProvider;
import com.munsun.monitoring_service.commons.dto.in.LoginPasswordDtoIn;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class DefaultJwtProvider implements JwtProvider {
    @Value("${security.jwt.secret.header}")
    private String secretHeader;
    @Value("${security.jwt.secret.expiration}")
    private Integer secretKeyExpiration;
    @Value("${security.jwt.secret.key}")
    private String secretKey;

    private final UserDetailsService userDetailsService;

    @Override
    public String resolveHeader(HttpServletRequest servletRequest) {
        return servletRequest.getHeader(secretHeader);
    }

    @Override
    public boolean validateAccessToken(String token) {
        try{
            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            return false;
        } catch (UnsupportedJwtException unsEx) {
            return false;
        } catch (MalformedJwtException mjEx) {
            return false;
        } catch (SignatureException sEx) {
            return false;
        }
    }

    @Override
    public Authentication getAuthentification(String token) {
        var userLogin = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
        var userDetails = userDetailsService.loadUserByUsername(userLogin);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public String generateAccessToken(LoginPasswordDtoIn loginPassword) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + secretKeyExpiration*1000);
        return Jwts.builder()
                .header().type("JWT")
                .and()
                .subject(loginPassword.login())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
                .compact();
    }
}
