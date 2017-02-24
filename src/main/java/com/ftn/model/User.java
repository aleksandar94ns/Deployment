package com.ftn.model;

import javax.persistence.*;

/**
 * Created by Alex on 10/27/2016.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseModel {

    public enum Role {
        ADMIN,
        GUEST,
        MANAGER,
        WAITER,
        CHEF,
        BARTENDER,
        SYSTEM_MANAGER,
        SELLER
    }

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    private String confirmationCode;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(){
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
