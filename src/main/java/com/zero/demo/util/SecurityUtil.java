package com.zero.demo.util;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * SecurityUtil
 * 
 * @author Louisling
 * @since 2018-07-01
 */
@SuppressWarnings("restriction")
public class SecurityUtil {
    private static Logger log = LoggerFactory.getLogger(SecurityUtil.class);

    private static final String SEED = "appsecretappsecr";
    private static String ALGORITHM_NAME = "DESede";
    private static String TRANSFORMATION = "DESede/ECB/PKCS5Padding";
    private static String CHARSET_NAME = "UTF-8";

    public static String encode(String content) {
        return doCipher(Cipher.ENCRYPT_MODE, content);
    }

    public static String decode(String content) {
        return doCipher(Cipher.DECRYPT_MODE, content);
    }

    /**
    * Do cipher
    * Encode: first encode(), then base64Encode()
    * Decode: first base64Decode(), then decode()
    * @param opMode
    * @param content
    * @return
    * @throws GeneralSecurityException
    * @throws IOException
    */    
    private static String doCipher(int opMode, String content) {
        String result = "";

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION); //"DES/ECB/NoPadding", "DES/CBC/PKCS5Padding"
            cipher.init(opMode, getKey());

            if (opMode == Cipher.ENCRYPT_MODE) {
                byte[] encodedBytes = cipher.doFinal(content.getBytes(CHARSET_NAME));
                result = new String(new BASE64Encoder().encode(encodedBytes));
            } else if (opMode == Cipher.DECRYPT_MODE) {
                byte[] decodedBytes = cipher.doFinal(new BASE64Decoder().decodeBuffer(content));
                result = new String(decodedBytes, CHARSET_NAME);
            }
        } catch (GeneralSecurityException e) {
            log.error(e.getMessage());
            result = content;
        } catch (IOException e) {
            log.error(e.getMessage());
            result = content;
        }

        return result;
    }

    /**
    * Get key
    * @return
    * @throws GeneralSecurityException
    * @throws IOException
    */
    private static Key getKey() throws GeneralSecurityException, IOException {
        /*
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM_NAME); 
        byte[] DESkey = SEED.getBytes(CHARSET_NAME); 
        DESKeySpec keySpec = new DESKeySpec(DESkey); 
        Key key = keyFactory.generateSecret(keySpec);
        */

        //Using the following code, will can not be decoded in another JVM
        KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM_NAME);
        keygen.init(new SecureRandom(SEED.getBytes())); //DES-56, AES-128, DESede-112/168
        //keygen.init(168, new SecureRandom(SEED.getBytes())); //can be [168, 112], default is 168 
        SecretKey secretKey = keygen.generateKey();
        SecretKey key = new SecretKeySpec(secretKey.getEncoded(), ALGORITHM_NAME);

        return key;
    }
}
