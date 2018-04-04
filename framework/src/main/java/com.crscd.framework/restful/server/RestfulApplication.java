package com.crscd.framework.restful.server;

import com.crscd.framework.restful.server.resource.TestResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author lzy
 * @date 2017/9/30
 */
public class RestfulApplication extends Application<RestfulConfiguration> {

    @Override
    protected void bootstrapLogging() {
    }

    @Override
    public String getName() {
        return "interface machine";
    }

    @Override
    public void initialize(Bootstrap<RestfulConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(RestfulConfiguration IMPConfiguration, Environment environment) {

        //添加healthCheck
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(IMPConfiguration.getTemplate());
        // register resource to server
        environment.healthChecks().register("template", healthCheck);
        final TestResource testResource = new TestResource();
        environment.jersey().register(testResource);
    }
}
