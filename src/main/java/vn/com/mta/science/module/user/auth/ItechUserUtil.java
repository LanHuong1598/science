package vn.com.mta.science.module.user.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ItechUserUtil {

    private static Logger logger = LoggerFactory.getLogger(ItechUserUtil.class);

    public static String extractUsername(String authorization) {
        try {
            if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
                String base64Credentials = authorization.substring("Basic".length()).trim();
                byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
                String credentials = new String(credDecoded, StandardCharsets.UTF_8);

                // credentials = username:password
                return credentials.split(":")[0];
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return null;
    }

    public static Long extractUserId(Authentication authentication) {
        ItechUserDetails user = extractUser(authentication);
        if (user != null) return user.getId();
        return null;
    }

    public static ItechUserDetails extractUser(Authentication authentication) {
        try {
            Object object = authentication.getPrincipal();

            if (object instanceof ItechUserDetails) {
                ItechUserDetails user = (ItechUserDetails) object;
                return user;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;

    }
}
