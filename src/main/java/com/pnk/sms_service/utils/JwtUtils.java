package com.pnk.sms_service.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor // injected by Constructor, no longer need of @Autowire
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JwtUtils {

    /*
     * Get the JWT from the SecurityContextHolder
     * */
    public static Object getJwtPayload(String key) {
        Map<String, Object> extractedData = new HashMap<>();

        var context = SecurityContextHolder.getContext();
        extractedData.put("context", context.getAuthentication());

        var authentication = context.getAuthentication();
        extractedData.put("authentication", authentication);

        String name = authentication.getName();
        extractedData.put("name", name);

        var jwtToken = authentication.getCredentials().toString();
        extractedData.put("jwtToken", jwtToken);

        log.info(">> extractDataFromJWT::extractedData: {}", extractedData);
        return extractedData.get(key);

    }

}
