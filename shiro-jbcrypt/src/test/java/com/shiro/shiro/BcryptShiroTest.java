package com.shiro.shiro;

import com.sample.shiro.AppSecurity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.Assert.assertTrue;


public class BcryptShiroTest {

    private Subject subject;
    private AppSecurity appSecurity;

    @Before
    public void setUp() throws Exception {

        appSecurity = new AppSecurity();
        String encryptPassword = appSecurity.hashPassword("toto");
        CredentialsMatcher credentialsMatcher = appSecurity.createCredentialsMatcher();

        InMemoryRealm realm = new InMemoryRealm("root", encryptPassword, credentialsMatcher);
        DefaultSecurityManager securityManager = new DefaultSecurityManager(realm);
        SecurityUtils.setSecurityManager(securityManager);

        subject = SecurityUtils.getSubject();
    }

    @After
    public void reset() throws Exception {
        subject.logout();
        SecurityUtils.setSecurityManager(null);
    }

    @Test
    public void shouldEncryptAndLog() {

        subject.login(new UsernamePasswordToken("root", "toto"));

        assertTrue(subject.isAuthenticated());
    }

    @Test(expected = AuthenticationException.class)
    public void shouldNotLoginFakeUser() {

        subject.login(new UsernamePasswordToken("fake", "toto"));
    }

    @Test(expected = AuthenticationException.class)
    public void shouldNotLoginUserWithInvalidPassword() {

        subject.login(new UsernamePasswordToken("root", "invalid"));
    }

    @Test
    public void bCryptHashing() throws Exception {

        String gensalt = BCrypt.gensalt();
        String hashed = BCrypt.hashpw("toto", gensalt);

        System.out.println(hashed);
        System.out.println(gensalt);
    }

}
