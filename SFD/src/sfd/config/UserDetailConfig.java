/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfd.config;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author afoud
 */
public final class UserDetailConfig {
    private int userId;
    private int currentFolderId;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public UserDetailConfig(int userId, int currentFolderId, PublicKey publicKey, PrivateKey privateKey) {
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
    
    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCurrentFolderId(int currentFolderId) {
        this.currentFolderId = currentFolderId;
    }
    
    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
    
    public void setPrivateKey(PrivateKey privateKey){
        this.privateKey = privateKey;
    }
    
    
}
