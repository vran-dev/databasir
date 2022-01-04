package com.databasir.common.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CodecTest {

    @Test
    public void testAes() {
        String key = Aes.randomBase64Key();
        Assertions.assertNotNull(key);

        String data = "hello world!";
        String encryptedData = Aes.encryptToBase64Data(data, key);
        Assertions.assertNotNull(encryptedData);

        String decryptedData = Aes.decryptFromBase64Data(encryptedData, key);
        Assertions.assertEquals(data, decryptedData);
    }

    @Test
    public void testRsa() {
        Rsa.RsaBase64Key key = Rsa.generateBase64Key();
        Assertions.assertNotNull(key);
        Assertions.assertNotNull(key.getPrivateBase64Key());
        Assertions.assertNotNull(key.getPublicBase64Key());

        String data = "Hello world!";
        String encrypted = Rsa.encryptToBase64DataByPublicKey(data, key.getPublicBase64Key());
        Assertions.assertNotNull(encrypted);

        String decrypted = Rsa.decryptFromBase64DataByPrivateKey(encrypted, key.getPrivateBase64Key());
        Assertions.assertEquals(data, decrypted);
    }
}
