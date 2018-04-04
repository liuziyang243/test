package com.crscd.framework.restful.server;

import com.codahale.metrics.health.HealthCheck;

/**
 * @author lzy
 * Date: 2017/6/23
 * Time: 16:09
 */
public class TemplateHealthCheck extends HealthCheck {
    private final String template;

    public TemplateHealthCheck(String template) {
        this.template = template;
    }

    @Override
    protected Result check() {
        final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return Result.unhealthy("template doesn't include a name");
        }
        return Result.healthy();
    }
}
