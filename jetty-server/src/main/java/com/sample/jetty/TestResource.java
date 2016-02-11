package com.sample.jetty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/test")
public class TestResource {

    @Inject
    InjectedDAO dao;

    @GET
    public Response toto(){
        dao.toString();
        return Response.ok().build();
    }
}
