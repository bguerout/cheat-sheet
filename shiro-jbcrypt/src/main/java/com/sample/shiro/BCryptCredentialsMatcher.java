package com.sample.shiro;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.mindrot.jbcrypt.BCrypt;


public class BCryptCredentialsMatcher implements CredentialsMatcher {

    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        if (token instanceof UsernamePasswordToken) {
            String raw = new String(((UsernamePasswordToken) token).getPassword());
            String credentials = (String) info.getCredentials();
            return BCrypt.checkpw(raw, credentials);
        } else {
            throw new ShiroException("Shiro's configuration seems to be invalid.");
        }
    }
}
