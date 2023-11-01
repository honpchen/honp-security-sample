package ink.honp.sample.infra.config;

import ink.honp.sample.common.util.RsaUtil;

/**
 * @author jeffchen
 * @date 2023/11/01 17:48
 */
public class JwtKeys {

    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmAS+n1Zy2O8dEgRbnvpiYjZjt11gh1ewIfnJDRLlQLDrb4FR0QTu+pbzqlGsW0byyOYMu4pSD/Q/t84/Hy43wzJNcdWPIrAXPfF8d3mZL+CEOwx7UUCIRmtpd71kQ3f+nb5wndkr5gWPzhoI8Gi1CXMQQZmkTuFrNqOzvpVcvSQIDAQAB";

    public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKYBL6fVnLY7x0SBFue+mJiNmO3XWCHV7Ah+ckNEuVAsOtvgVHRBO76lvOqUaxbRvLI5gy7ilIP9D+3zj8fLjfDMk1x1Y8isBc98Xx3eZkv4IQ7DHtRQIhGa2l3vWRDd/6dvnCd2SvmBY/OGgjwaLUJcxBBmaRO4Ws2o7O+lVy9JAgMBAAECgYBJxfssxkTHw9cxvYC1asGcAkl1YZ88IjqsJ62V8YO9enqJ3q8Jnzfy6OsFAQJ6eKk4N1DyVCWWZq9em0BJlU+1fUERMxmFD31ffg8e2CiSWRQn+v0rlRnFTxFWSs2AGxfAEWLEmefPcV4IynXrGZJ7sE2HkMKo3f23Q+XIXxgjZQJBANxq2kw1kNWHKIgSl6krsZOdYNQRRlbCJmvciuRfwl+t0GZyahVOF6EgVgOH1oSUMBmUzmAWZRvjX9llAuYyOqcCQQDAzaC0Hy1yx9OaJ7j+CySfXaMYRnr+qbjhq++2sPWbVfPAI8EMCmFXusIdyyxybxtllee5vsE0WO9rDjPh/bSPAkBP0e4tzPQfL2vxIvKDE8yhzUtEYOEdCAAL7XjqzPbB2Vrvzp02C+qoseIUMzvvFQ76JJY728BPwh5yxo6vOB9fAkEAr+brB2qqfF3zoGE9Vgm0j/FOK4eiD1WpZ7sGKRep/N6eVF5KckHGLdQl5ijHlF/qDvSFReHTQ38gb/u1OinN4QJAONXw2J2V3p43uiqXZXR7uHoNMDSOD1DFBe2R9vIpu6/1onQ/MhjFi3IlAVKpO7TfzlJcYB/0GO1LTHJtBsCYBQ==";

    public static void main(String[] args) {

        RsaUtil.generateKeyPair(1024);
    }
}
