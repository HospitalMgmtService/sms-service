package com.pnk.sms_service.configuration.authorization;

import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.text.ParseException;


@Slf4j
@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            log.info(">> decode::token: {}", token);

            SignedJWT signedJWT = SignedJWT.parse(token);

            log.info(">> decode::signedJWT.getJWTClaimsSet: {}", signedJWT.getJWTClaimsSet());

            return new Jwt(
                    token,
                    signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
                    signedJWT.getHeader().toJSONObject(),
                    signedJWT.getJWTClaimsSet().getClaims()
            );
        } catch (ParseException e) {
            log.info(">> decode >> Invalid token");
            throw new JwtException("Invalid token");
        }
    }

}
