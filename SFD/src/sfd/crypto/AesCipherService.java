/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfd.crypto;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 *
 * @author afoud
 */
public class AesCipherService {
    
    // Encrypt user's File with AES Key
    public byte[] encryptFileWithAesKey(File file, SecretKey aesKey) throws GeneralSecurityException, IOException {
        byte[] encryptedFile;
        
        // Convert file contents into bytes
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        
        // Encrypt fileBytes with AES key
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        encryptedFile = cipher.doFinal(fileBytes);
        
        // retrive the IV from cipher to use it to decrypt later on
        byte[] iv = cipher.getIV();
        
        // add iv to enryptedFile
        encryptedFile = ByteBuffer.allocate(iv.length + encryptedFile.length)
                .put(iv)
                .put(encryptedFile)
                .array();
        
        return encryptedFile;
    }
    
    // Decreypt user's File with AES Key
    public byte[] decryptFileWithAesKey(byte[] encryptedFile, SecretKey aesKey) throws GeneralSecurityException {
        byte[] file;
        
        // Initialize ByteBuffer and wrap encryptedFile to extract iv
        ByteBuffer buffer = ByteBuffer.wrap(encryptedFile);
        byte[] iv = new byte[12];
        buffer.get(iv);
        
        // get the remaining bytes
        byte[] encryptedFileBytes = new byte[buffer.remaining()];
        buffer.get(encryptedFileBytes);
        
        // Decrypt File
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, aesKey, gcmSpec);
        file = cipher.doFinal(encryptedFileBytes);
        
        return file;
    }
    
}
