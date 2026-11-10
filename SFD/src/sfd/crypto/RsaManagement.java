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
import javax.crypto.SecretKey;

/**
 *
 * @author afoud
 */
public class RsaManagement {
    
    // Encrypt AES Key with user's RSA Public Key
    public String encryptAesKeyWithRsaPublicKey(SecretKey aesKey, String rsaPublicKey) {
        String encodedEncryptedAesKey = null;
        
        try {
            // Decode key that are encoded with Base64
            byte[] rsaPublicKeyDecoded = Base64.getDecoder().decode(rsaPublicKey);
            
            // Convert public key bytes into PublicKey Object
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(rsaPublicKeyDecoded);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            
            // Encrypt AES Key with RSA public Key
            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            
            // Convert AES Key into raw bytes and encrypt the key with RSA public key
            byte[] encryptedAesKey = encryptCipher.doFinal(aesKey.getEncoded());
        
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
    
    
    Public SecretKey dencryptAesKeyWithRsaPrivateKey() {

    }
    
}
