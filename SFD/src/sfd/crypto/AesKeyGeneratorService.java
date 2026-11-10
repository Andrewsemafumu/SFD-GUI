/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfd.crypto;

import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author afoud
 * 
 * Code adapted from:
 * https://howtodoinjava.com/java/java-security/aes-256-encryption-decryption/
 */
public class AesKeyGeneratorService {
    
    public SecretKey aesKeyGenerator() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256);
        SecretKey key = generator.generateKey();
        
        return key;
    }
    
}
