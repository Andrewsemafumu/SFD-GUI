/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfd.crypto;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author afoud
 */
public class RsaManagement {
    
    // Encrypt AES Key with user's RSA Public Key
    // May change method later - current implementation presumes I encode AES Key with Base64, I however will probably not
    // It is just a placeholder as I have yet to implement AES Key Generation and Encryption/Dencryption
    public String encryptAesKeyWithRsaPublicKey(String aesKey, String rsaPublicKey) {
        String encodedEncryptedAesKey = null;
        
        try {
            // Decode keys that are encoded with Base64
            byte[] aesKeyDecoded = Base64.getDecoder().decode(aesKey);
            byte[] rsaPublicKeyDecoded = Base64.getDecoder().decode(rsaPublicKey);
            
            // Convert public key bytes into PublicKey Object
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(rsaPublicKeyDecoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            
            // Encrypt AES Key with RSA public Key
            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
            byte[] encryptedAesKey = encryptCipher.doFinal(aesKeyDecoded);
        
            // Encode encryptedAesKey with Base64 to convert it to string object
            encodedEncryptedAesKey = Base64.getEncoder().encodeToString(encryptedAesKey);
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch(NoSuchPaddingException e) {
            e.printStackTrace();
        } catch(InvalidKeySpecException e) {
            e.printStackTrace();
        } catch(InvalidKeyException e) {
            e.printStackTrace();
        } catch(IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch(BadPaddingException e) {
            e.printStackTrace();
        }
        
        return encodedEncryptedAesKey;
    }
    
}
