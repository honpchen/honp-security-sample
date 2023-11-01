package ink.honp.sample.common.util;

import ink.honp.sample.common.enums.CommonErrorCode;
import ink.honp.sample.common.exception.BizException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author jeff chen
 * @since 1.0.0
 */
@Slf4j
public abstract class JwtUtil {

    private static final Integer EXPIRES_IN = 2 * 60 *60;
    private static final String DATA_KEY = "data";

    /**
     * 从 JWT Token 中解析授权信息
     * @param accessToken accessToken
     * @param clz jwt 解析返回的类型
     * @return 若解析失败抛出未认证异常
     */
    public static <T> T parseToken(String publicKey, String accessToken, Class<T> clz) {
        if (StringUtils.isBlank(accessToken)) {
            throw new BizException(CommonErrorCode.NOT_AUTH);
        }

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(RsaUtil.getPublicKey(publicKey))
                    .parseClaimsJws(accessToken);

            Claims body = claimsJws.getBody();
            // 如果 Token 已经过期了, 返回 null
            if (body.getExpiration().before(Calendar.getInstance().getTime())) {
                log.warn("AccessToken 已过期");
                throw new BizException(CommonErrorCode.NOT_AUTH);
            }

            // 返回 Token 中保存的用户信息
            return JsonUtil.toBean(body.get(DATA_KEY).toString(), clz);
        } catch (Exception e) {
            log.warn("AccessToken 解析失败：{}", e.getMessage());
            throw new BizException(CommonErrorCode.NOT_AUTH);
        }
    }


    public static String generateToken(String privateKey, Object userData) {
        try {
            return Jwts.builder()
                    // jwt payload --> KV
                    .claim(DATA_KEY, JsonUtil.toStr(userData))
                    // jwt id
                    .setId(UUID.randomUUID().toString())
                    // jwt 过期时间
                    .setExpiration(DateUtils.addSeconds(new Date(), EXPIRES_IN))
                    // jwt 签名 --> 加密
                    .signWith(SignatureAlgorithm.RS256, RsaUtil.getPrivateKey(privateKey))
                    .compact();

        } catch (Exception e) {
            log.error("生成AccessToken出错：{}", e.getMessage(), e);
            throw new BizException(CommonErrorCode.SYSTEM_ERROR);
        }
    }

}
