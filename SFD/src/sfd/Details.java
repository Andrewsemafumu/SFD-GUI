/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfd;

/**
 *
 * @author HP
 */
public class Details extends Person {
    private String password;
    private String role;
    private String sector;

    public Details(String email, String password, String role, String sector) {
        super(email); // Pass email to the parent class
        this.password = password;
        this.role = role;
        this.sector = sector;
    }

    public String getPassword() { return password; }
    public String getSector() { return sector; }

    @Override
    public String getRole() { // Polymorphism: Implementing the abstract method
        return role;
    }

    // YOUR HASHING LOGIC GOES HERE
    public String hashPassword() {
        return Integer.toHexString(password.hashCode());
    }
}