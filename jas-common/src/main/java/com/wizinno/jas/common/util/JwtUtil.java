package com.wizinno.jas.common.util;

import com.google.gson.Gson;
import com.wizinno.jas.common.config.Config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

/**
 * Created by LiuMei on 2017-07-26.
 */
@Component
public class JwtUtil {

    private static Gson gson = new Gson();

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(Config.getConfig("jwt.secret"));
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     *
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public String createJWT(String id, String subject, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 生成subject信息
     *
     * @param info
     * @return
     */
    public static String generalSubject(Map<String, String> info) {
        return gson.toJson(info);
    }
}
