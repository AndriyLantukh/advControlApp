package com.al.advControlApp.auth;

import com.al.advControlApp.model.User;
import io.jsonwebtoken.*;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;


@Component
public class JwtTokenUtil implements Serializable {

    private static Logger LOGGER = Logger.getLogger(JwtTokenUtil.class);


    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
    public static final String SIGNING_KEY = "al3537";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public String getUserEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getClimeToken(HttpServletRequest req, String parName) {
        String header = req.getHeader(HEADER_STRING);
        String strResult = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            String authToken = header.replace(TOKEN_PREFIX, "");
            try {
                strResult = getAllClaimsFromToken(authToken).get(parName, String.class);
            } catch (IllegalArgumentException e) {
                LOGGER.error("an error occured during getting " + parName + " from token", e);
            }
        } else {
            LOGGER.warn("couldn't find bearer string, will ignore the header");
        }
        return strResult;
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getEmail(), user.getRole().toString(), user.getId().toString());
    }

    private String doGenerateToken(String email, String role, String userID) {

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userRole", role);
        claims.put("userId", userID);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("al")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = getUserEmailFromToken(token);
        return (
                email.equals(userDetails.getUsername())
                        && !isTokenExpired(token));
    }

}