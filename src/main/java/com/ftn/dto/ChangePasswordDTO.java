package com.ftn.dto;

/**
 * Created by Alex on 12/5/16.
 */
public class ChangePasswordDTO {

    private String oldPassword;

    private String password;

    public ChangePasswordDTO() {
    }

    public ChangePasswordDTO(String oldPassword, String password) {
        this.oldPassword = oldPassword;
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ChangePasswordDTO{" +
                "oldPassword='" + oldPassword + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
