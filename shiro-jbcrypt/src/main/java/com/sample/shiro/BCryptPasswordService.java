package com.sample.shiro;

import org.apache.shiro.authc.credential.PasswordService;
import org.mindrot.jbcrypt.BCrypt;


public class BCryptPasswordService implements PasswordService {

    public String encryptPassword(Object plaintextPassword) throws IllegalArgumentException {
        String gensalt = BCrypt.gensalt();
        return BCrypt.hashpw((String) plaintextPassword, gensalt);
    }

    public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
        return BCrypt.checkpw((String) submittedPlaintext, encrypted);
    }
}
