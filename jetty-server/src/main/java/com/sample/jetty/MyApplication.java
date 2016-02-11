package com.sample.jetty;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/root-path")
public class MyApplication extends Application
{

    public MyApplication() {
        super();
    }
}