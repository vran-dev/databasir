package com.databasir.common.codec;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Rsa {

    private static final String RSA = "RSA";

    @SneakyThrows
    public static String encryptToBase64DataByPublicKey(String data, String publicBase64Key) {
        Cipher encryptCipher = Cipher.getInstance(RSA);
        encryptCipher.init(Cipher.ENCRYPT_MODE, decodeBase64PublicKey(publicBase64Key));
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedDataBytes = encryptCipher.doFinal(dataBytes);
        return Base64.getEncoder().encodeToString(encryptedDataBytes);
    }

    @SneakyThrows
    public static String decryptFromBase64DataByPrivateKey(String data, String privateKey) {
        Cipher decryptCipher = Cipher.getInstance(RSA);
        decryptCipher.init(Cipher.DECRYPT_MODE, decodeBase64PrivateKey(privateKey));
        byte[] dataBytes = Base64.getDecoder().decode(data);
        byte[] decryptedBytes = decryptCipher.doFinal(dataBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    private static PublicKey decodeBase64PublicKey(String base64PublicKey) {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyBytes);
        return keyFactory.generatePublic(publicKeySpec);
    }

    @SneakyThrows
    private static PrivateKey decodeBase64PrivateKey(String base64PrivateKey) {
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        return keyFactory.generatePrivate(privateKeySpec);
    }

    @SneakyThrows
    public static RsaBase64Key generateBase64Key() {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        return new RsaBase64Key(privateKey, publicKey);
    }

    @Getter
    @RequiredArgsConstructor
    public static class RsaBase64Key {

        private final String privateBase64Key;

        private final String publicBase64Key;
    }

}
