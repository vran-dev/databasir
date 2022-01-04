package com.databasir.common.codec;

import org.apache.shiro.crypto.AesCipherService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class Aes {

    public static String encryptToBase64Data(String data, String key) {
        return new AesCipherService()
                .encrypt(toBytes(data), toBytes(key))
                .toBase64();
    }

    public static String decryptFromBase64Data(String encryptData, String key) {
        byte[] originEncrypted = Base64.getDecoder().decode(toBytes(encryptData));
        byte[] originKey = toBytes(key);
        return toString(new AesCipherService().decrypt(originEncrypted, originKey).getBytes());
    }

    public static String randomBase64Key() {
        Key key = new AesCipherService().generateNewKey();
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    private static byte[] toBytes(String data) {
        return data.getBytes(StandardCharsets.UTF_8);
    }

    private static String toString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
