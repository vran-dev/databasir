package com.databasir.common.codec;

import lombok.SneakyThrows;
import org.apache.shiro.codec.Hex;

import java.security.MessageDigest;

public class Sha {

    @SneakyThrows
    public static String sha256(String data) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(data.getBytes());
        byte[] bytes = messageDigest.digest();
        return Hex.encodeToString(bytes);
    }

}
