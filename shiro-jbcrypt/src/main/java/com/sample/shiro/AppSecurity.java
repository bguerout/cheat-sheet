package com.sample.shiro;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordService;


public class AppSecurity {

    private final PasswordService passwordService;

    public AppSecurity() {
        passwordService = new BCryptPasswordService();
    }

    public String hashPassword(String rawPassword) {
        return passwordService.encryptPassword("toto");
    }

    public CredentialsMatcher createCredentialsMatcher() {
        return new BCryptCredentialsMatcher();
    }
}
