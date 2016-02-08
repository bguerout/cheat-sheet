package com.sample.jetty;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import javax.mail.Session;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class TestServlet extends HttpServlet {


    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {
            Object lookup = new InitialContext().lookup("mail/Session");

            Email email = new SimpleEmail();
            email.setMailSession((Session) lookup);
            email.setFrom("user@gmail.com");
            email.setSubject("TestMail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo("foo@bar.com");
            email.send();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}