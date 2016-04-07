package com.sample.jetty;

import com.sample.jetty.cache.CachedDAO;
import com.sample.jetty.formdata.FileUploadForm;
import com.sample.jetty.pdf.PDFCreator;
import org.apache.commons.io.output.ByteArrayOutputStream;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Path("/test")
public class TestResource {

    @Inject
    CachedDAO dao;

    @GET
    @Produces("text/plain")
    public Response toto(@Context Data data) throws ExecutionException {
        return Response.ok(dao.getData().getTest()).build();
    }

    @GET
    @Path("/pdf")
    @Produces("application/pdf")
    public Response noData() throws Exception {

        PDFCreator creator = new PDFCreator();
        ByteArrayOutputStream output = creator.createAsByteArray();

        StreamingOutput stream = os -> {
            os.write(output.toByteArray());
            os.flush();
            output.close();
        };

        Response.ResponseBuilder response = Response.ok(output);
        response.header("Content-Disposition", "attachment; filename=new-android-book.pdf");

        return Response.ok(stream).build();
    }



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public Response jackson(Data data) {
        return Response.ok(data.getTest()).build();
    }

    @POST
    @Path("/form")
    @Consumes("multipart/form-data")
    public Response formData(Map<String, FileUploadForm> form) throws ExecutionException {

        return Response.status(200).build();

    }

}
