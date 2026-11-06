/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfd;

/**
 *
 * @author HP
 */
public abstract class Person {
    protected String email;

    public Person(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    // Abstract method (Polymorphism)
    public abstract String getRole();
}