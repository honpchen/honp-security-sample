package ink.honp.sample.infra.auth;

import lombok.Data;

/**
 * @author jeffchen
 * @date 2023/11/01 18:14
 */
@Data
public class TokenInfo {

    private String username;

    private String accessToken;
}
