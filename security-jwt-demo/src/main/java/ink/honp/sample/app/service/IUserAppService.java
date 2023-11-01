package ink.honp.sample.app.service;

import ink.honp.sample.app.vo.UserVO;
import org.springframework.http.ResponseEntity;

/**
 * @author jeffchen
 * @date 2023/11/01 16:55
 */
public interface IUserAppService {

    ResponseEntity<UserVO> getUser(Long id);
}
