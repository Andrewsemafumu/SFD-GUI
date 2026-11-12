/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfd.dto;

/**
 *
 * @author afoud
 */
public class FileUploadDto {

    public int getOwnerId() {
        return ownerId;
    }

    public String getFileName() {
        return fileName;
    }

    public int getFolderId() {
        return folderId;
    }

    public String getEncryptedAesKey() {
        return encryptedAesKey;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public void setEncryptedAesKey(String encryptedAesKey) {
        this.encryptedAesKey = encryptedAesKey;
    }
    private int ownerId;
    private String fileName;
    private int folderId;
    private String encryptedAesKey;
    
}
