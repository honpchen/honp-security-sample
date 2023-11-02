package ink.honp.sample.infra.auth;

import lombok.Data;

import java.util.List;

/**
 * @author jeffchen
 * @date 2023/11/02 16:07
 */
@Data
public class UserInfo {

    private Long id;

    private String userName;

    private List<String> roles;
}
