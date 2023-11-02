# Spring Security 简单的应用示例

## 模块示例说明(详情请查看各个模块的README.md)
- `security-auth-demo` 认证 demo 示例，临时体验
- `security-jwt-demo` JWT 认证实现

## 日志调式说明

开启`spring security` 调试日志

- 启动 debug
    ```java
    @EnableWebSecurity(debug = true)
    ```
- 配置日志路径
    ```yml
    logging:
      level:
        org.springframework.security: debug
    ```



