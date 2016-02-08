package com.sample.jetty;

import com.sample.jetty.utils.MailServer;
import com.sample.jetty.utils.WebServer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


public class MailSendingTest {

    @ClassRule
    public static WebServer WEB_SERVER = new WebServer(9898);

    @ClassRule
    public static MailServer MAIL_SERVER = new MailServer(3025);

    private OkHttpClient client;

    @Before
    public void setUp() throws Exception {
        client = new OkHttpClient();
    }

    @After
    public void tearDown() throws Exception {
        MAIL_SERVER.reset();
    }

    @Test
    public void testName() throws Exception {

        Request request = new Request.Builder()
                .url("http://localhost:9898/app/test")
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(response.code(), 200);
        assertThat(MAIL_SERVER.hasReceivedEmails()).isTrue();
    }


}
