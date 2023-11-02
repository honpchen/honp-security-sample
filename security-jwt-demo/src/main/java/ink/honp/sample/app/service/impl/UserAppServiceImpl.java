package ink.honp.sample.app.service.impl;

import ink.honp.sample.app.service.IUserAppService;
import ink.honp.sample.app.vo.UserVO;
import ink.honp.sample.common.Response;
import org.springframework.stereotype.Service;

/**
 * @author jeffchen
 * @date 2023/11/01 16:56
 */
@Service
public class UserAppServiceImpl implements IUserAppService {


    @Override
    public Response<UserVO> getUser(Long id) {
        UserVO userVO = new UserVO();
        userVO.setId(id);
        userVO.setUsername("Test" + id);
        userVO.setAccount(userVO.getUsername() + "@qq.com");

        return Response.of(userVO);
    }
}
