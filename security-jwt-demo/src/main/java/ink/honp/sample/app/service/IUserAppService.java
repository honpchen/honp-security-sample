package ink.honp.sample.app.service;

import ink.honp.sample.app.vo.UserVO;
import ink.honp.sample.common.Response;

/**
 * @author jeffchen
 * @date 2023/11/01 16:55
 */
public interface IUserAppService {

    Response<UserVO> getUser(Long id);
}
