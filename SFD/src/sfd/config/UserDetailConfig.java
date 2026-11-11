/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfd.config;

/**
 *
 * @author afoud
 */
public final class UserDetailConfig {
    private int userId;
    private int currentFolderId;
    private String publicKey;
    private String privateKey;

    public UserDetailConfig(int userId, int currentFolderId, String publicKey, String privateKey) {
        this.userId = userId;
        this.currentFolderId = currentFolderId;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public int getUserId() {
        return userId;
    }

    public int getCurrentFolderId() {
        return currentFolderId;
    }
    
    public String getPublicKey() {
        return publicKey;
    }
    
    public String getPrivateKey() {
        return privateKey;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCurrentFolderId(int currentFolderId) {
        this.currentFolderId = currentFolderId;
    }
    
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    
    
}
