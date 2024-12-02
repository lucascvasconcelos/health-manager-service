package com.healthmanagerservice.healthmanagerservice.infrastructure.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;


public class CryptoUtil {
    private static final String AES = "AES";
    private static final String AES_GCM_NOPADDING = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 16;
    private static final int IV_SIZE = 12;
    private final SecretKeySpec secretKeySpec;

    private static final String FIXED_KEY_STRING = "SuaChaveSecreta";

    public CryptoUtil() throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(FIXED_KEY_STRING.getBytes("UTF-8"));
        byte[] key = Arrays.copyOf(keyBytes, 32);
        this.secretKeySpec = new SecretKeySpec(key, AES);
    }

    public String encrypt(String plainText) throws Exception {
        byte[] iv = new byte[IV_SIZE];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);

        Cipher cipher = Cipher.getInstance(AES_GCM_NOPADDING);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, parameterSpec);

        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        byte[] encryptedIVAndText = new byte[IV_SIZE + encrypted.length];
        System.arraycopy(iv, 0, encryptedIVAndText, 0, IV_SIZE);
        System.arraycopy(encrypted, 0, encryptedIVAndText, IV_SIZE, encrypted.length);
        return Base64.getEncoder().encodeToString(encryptedIVAndText);
    }

    public String decrypt(String cipherText) throws Exception {
        byte[] decodedCipherText = Base64.getDecoder().decode(cipherText);
        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(decodedCipherText, 0, iv, 0, IV_SIZE);
        byte[] encryptedText = new byte[decodedCipherText.length - IV_SIZE];
        System.arraycopy(decodedCipherText, IV_SIZE, encryptedText, 0, encryptedText.length);

        Cipher cipher = Cipher.getInstance(AES_GCM_NOPADDING);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, parameterSpec);

        byte[] decryptedText = cipher.doFinal(encryptedText);
        return new String(decryptedText);
    }
}