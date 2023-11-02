package ink.honp.sample.infra.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jeffchen
 * @date 2023/11/02 10:11
 */
@Component
public class CustomUserDetailServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        if (StringUtils.isBlank(username) || !"admin".equals(username)) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ADMIN"));
        String password = "$2a$10$FGw9Yx7Krs77HWJ2rZQeP.BMHd8Spqley8S3oCuQxz91E/OcpNF3G"; // 12345678

        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setId(1L);
        userDetails.setUsername(username);
        userDetails.setPassword(password);
        userDetails.setAuthorities(authorities);

        return userDetails;
    }

}
