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

    public UserDetailConfig(int userId, int currentFolderId) {
        this.userId = userId;
        this.currentFolderId = currentFolderId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCurrentFolderId() {
        return currentFolderId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCurrentFolderId(int currentFolderId) {
        this.currentFolderId = currentFolderId;
    }
    
    
}
