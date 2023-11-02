package ink.honp.sample.common.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author jeff chen
 * @since 1.0.0
 */
@Slf4j
public abstract class RsaUtil {

    private static final Logger LOG = LoggerFactory.getLogger(RsaUtil.class);

    private RsaUtil() {

    }

    /**
     * RSA公钥加密
     * @param plaintext 明文
     * @param publicKey 公钥
     * @return 密文
     */
    public static String encrypt(String plaintext, String publicKey)  {
        try {
            RSAPublicKey pubKey = (RSAPublicKey) getPublicKey(publicKey);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            LOG.warn("RSA公钥加密错误:{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * RSA私钥解密
     *
     * @param ciphertext 密文
     * @param privateKey 私钥
     * @return 明文
     */
    public static String decrypt(String ciphertext, String privateKey) {
        try {
            byte[] inputByte = Base64.getDecoder().decode(ciphertext);
            //base64编码的私钥
            RSAPrivateKey priKey = (RSAPrivateKey) getPrivateKey(privateKey);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);

            return new String(cipher.doFinal(inputByte));
        }catch (Exception e) {
            LOG.warn("RSA私钥解密错误:{}", e.getMessage(), e);
        }
        return null;
    }


    public static PrivateKey getPrivateKey(String privateKey){

        byte[] encodedKey = Base64.getDecoder().decode(privateKey);

        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(encodedKey);

        try {
            return KeyFactory.getInstance("RSA").generatePrivate(priPKCS8);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.error("{}", e.getMessage(), e);
        }
        return null;
    }


    /**
     * <h2>根据本地存储的公钥获取到 PublicKey 对象</h2>
     * */
    public static PublicKey getPublicKey(String publicKey) {
        byte[] encodedKey = Base64.getDecoder().decode(publicKey);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
        try {
            return KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.error("{}", e.getMessage(), e);
        }

        return null;
    }

    /**
     * 随机生成密钥对
     * @param keySize 密码长度，越长速度越慢
     */
    public static void generateKeyPair(Integer keySize) {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥对生成器
            keyPairGen.initialize(keySize, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();

            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

            String publicKeyHex = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyHex = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            LOG.info("公钥：{}", publicKeyHex);
            LOG.info("密钥：{}", privateKeyHex);
        } catch (NoSuchAlgorithmException e) {
            log.error("{}", e.getMessage(), e);
        }

    }


}
