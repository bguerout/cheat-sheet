package com.sample.jetty.utils;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.runner.Description;


public class MailServer extends AbstractRule {

    private GreenMail greenMail;

    public MailServer(int port) {
        greenMail = new GreenMail(new ServerSetup(port, null, ServerSetup.PROTOCOL_SMTP));
    }

    @Override
    protected void before(Description description) throws Throwable {
        greenMail.start();
    }

    @Override
    protected void after(Description description) throws Throwable {
        greenMail.stop();
    }

    public boolean hasReceivedEmails() {
        return greenMail.getReceivedMessages().length > 0;
    }

    public void reset() {
        greenMail.reset();
    }

}
