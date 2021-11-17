package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenUtil {
    /**
     * 生成token
     * @param tokenId 對應sessionId
     * @param audienceId 對應audienceId
     * @param secret 對應password
     * @return
     */
    public static String getToken(String tokenId, String audienceId, String secret) {
        return JWT.create().withJWTId(tokenId).withAudience(audienceId).sign(Algorithm.HMAC256(secret));
    }
}
