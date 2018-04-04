package com.crscd.framework.restful.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * Created by Administrator on 2017/9/30.
 */
public class RestfulConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultVersion = "v0.0.0";

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultVersion() {
        return defaultVersion;
    }

    @JsonProperty
    public void setDefaultVersion(String name) {
        this.defaultVersion = name;
    }
}
