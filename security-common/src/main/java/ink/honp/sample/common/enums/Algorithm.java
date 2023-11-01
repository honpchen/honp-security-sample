package ink.honp.sample.common.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @author jeff chen
 * @since 2023-07-31 16:26
 */
@Getter
@ToString
public enum Algorithm {

    MD5("MD5"),
    RSA("RSA"),
    AES("AES");

    private final String code;

    Algorithm(String code) {
        this.code = code;
    }
}
