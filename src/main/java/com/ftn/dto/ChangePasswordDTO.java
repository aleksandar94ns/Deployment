package com.ftn.dto;

/**
 * Created by Alex on 12/5/16.
 */
public class ChangePasswordDTO {

    private String oldPassword;

    private String password;

    public String getOldPassword() {
        return oldPassword;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ChangePasswordDTO{" +
                "oldPassword='" + oldPassword + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
