/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfd.crypto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 *
 * @author afoud
 */
public class AesCipherService {
    
    public byte[] encryptFileWithAesKey(File file, SecretKey aesKey) throws GeneralSecurityException, IOException {
        byte[] encryptedFile;
        
        // Convert file contents into bytes
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        
        // Encrypt fileBytes with AES key
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        encryptedFile = cipher.doFinal(fileBytes);
        
        return encryptedFile;
    }
    
}
