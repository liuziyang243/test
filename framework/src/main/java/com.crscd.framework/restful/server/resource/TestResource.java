package com.crscd.framework.restful.server.resource;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2017/9/30.
 */
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {
    private static final Logger logger = LoggerFactory.getLogger(TestResource.class);

    @POST
    @Path("/test1")
    @Timed
    public String postTest1(String name) {
        logger.warn("postTest1　name is {}", name);
        return "hello " + name;
    }

    @GET
    @Path("/test2")
    @Timed
    public String getTest2() {
        logger.warn("getTest2!!!");
        return "Yeah!";
    }
}
