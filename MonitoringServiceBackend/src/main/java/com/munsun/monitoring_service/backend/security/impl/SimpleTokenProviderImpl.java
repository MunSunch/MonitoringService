package com.munsun.monitoring_service.backend.security.impl;

import com.munsun.monitoring_service.backend.dao.AccountDao;
import com.munsun.monitoring_service.backend.exceptions.AuthenticationException;
import com.munsun.monitoring_service.backend.models.Account;
import com.munsun.monitoring_service.backend.security.JwtProvider;
import com.munsun.monitoring_service.backend.security.model.SecurityUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Provider;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtTokenProviderImpl implements JwtProvider {
    private static final String SECURITY_HEADER = "auth";
    private static final String secretKey = "secret";
    private static final Long secretKeyExpiration = 100500L;
    private final AccountDao accountDao;

    @Override
    public String generateToken(Account account) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + secretKeyExpiration * 1000);
        return Jwts.builder()
                .setSubject(String.valueOf(account.getId()))
                .claim("Role", account.getRole())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
                .compact();
    }

    @Override
    public boolean validateToken(String token) throws AuthenticationException {
        try {
            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
                    .parse(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            throw new AuthenticationException("token is expired");
        } catch (UnsupportedJwtException unsEx) {
            throw new AuthenticationException("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            throw new AuthenticationException("Malformed jwt");
        } catch (SignatureException sEx) {
            throw new AuthenticationException("Invalid signature");
        }
    }

    @Override
    public SecurityUser getSecurityUser(String token) throws SQLException {
        var userLogin = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
                .parse(token)
                .getBody();
//        var user = accountDao.getById(Long.valueOf(userLogin.getSubject())).get();
//        return SecurityUser.toSecurityUser(user);
        return null;
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(SECURITY_HEADER);
    }
}
