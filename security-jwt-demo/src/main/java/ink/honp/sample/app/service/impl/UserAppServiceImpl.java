package ink.honp.sample.app.service.impl;

import ink.honp.sample.app.service.IUserAppService;
import ink.honp.sample.app.vo.UserVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author jeffchen
 * @date 2023/11/01 16:56
 */
@Service
public class UserAppServiceImpl implements IUserAppService {


    @Override
    public ResponseEntity<UserVO> getUser(Long id) {
        UserVO userVO = new UserVO();
        userVO.setId(id);
        userVO.setUsername("Test" + id);
        userVO.setAccount(userVO.getUsername() + "@qq.com");

        return ResponseEntity.ok(userVO);
    }
}
