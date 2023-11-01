package ink.honp.sample.controller;

import ink.honp.sample.app.vo.UserVO;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{userId}")
    public ResponseEntity<UserVO> getUser(@PathVariable Long userId) {

        return null;
    }
}
