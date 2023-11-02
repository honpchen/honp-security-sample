package ink.honp.sample.controller;

import ink.honp.sample.app.service.IUserAppService;
import ink.honp.sample.app.vo.UserVO;
import ink.honp.sample.common.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jeffchen
 * @date 2023/11/01 16:51
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserAppService userAppService;

    public UserController(IUserAppService userAppService) {
        this.userAppService = userAppService;
    }

    @GetMapping("/{userId}")
    public Response<UserVO> getUser(@PathVariable Long userId) {

        return userAppService.getUser(userId);
    }
}
