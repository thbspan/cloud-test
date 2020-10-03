package com.test.cloud.netflix.zuul.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ZuulRouteRefreshListener implements ApplicationListener<EnvironmentChangeEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZuulRouteRefreshListener.class);

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteLocator routeLocator;
    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        boolean configUpdated = false;
        for (String key : event.getKeys()) {
            if (key.startsWith("zuul.")) {
                configUpdated = true;
                break;
            }
        }

        if (!configUpdated) {
            return;
        }
        publisher.publishEvent(new RoutesRefreshedEvent(routeLocator));
        LOGGER.info("publish RoutesRefreshedEvent finished. refresh zuul route config");
    }

}
