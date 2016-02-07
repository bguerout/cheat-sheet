package com.shiro.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;

public class InMemoryRealm extends AuthenticatingRealm {

    private final String username;
    private final String saltedPassword;

    public InMemoryRealm(String username, String saltedPassword, CredentialsMatcher credentialsMatcher) {
        this.username = username;
        this.saltedPassword = saltedPassword;
        setCredentialsMatcher(credentialsMatcher);
    }

    public String getName() {
        return getClass().getName();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken t) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) t;
        if (username.equals(token.getUsername())) {
            return new SimpleAuthenticationInfo(username, saltedPassword, getName());
        }
        throw new CredentialsException("User not found");

    }
}
