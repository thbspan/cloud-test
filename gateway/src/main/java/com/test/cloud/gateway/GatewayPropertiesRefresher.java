package com.test.cloud.gateway;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;

@Component
public class GatewayPropertiesRefresher implements ApplicationContextAware, ApplicationEventPublisherAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayPropertiesRefresher.class);

    private ApplicationContext applicationContext;
    private ApplicationEventPublisher publisher;

    private static final String ID_PATTERN = "spring\\.cloud\\.gateway\\.routes\\[\\d+\\]\\.id";
    private static final String DEFAULT_FILTER_PATTERN = "spring\\.cloud\\.gateway\\.default-filters\\[\\d+\\]\\.name";

    @Autowired
    private GatewayProperties gatewayProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @ApolloConfigChangeListener(interestedKeyPrefixes = "spring.cloud.gateway.") // <1>
    public void onChange(ConfigChangeEvent changeEvent) {
        refreshGatewayProperties(changeEvent);
    }

    private void refreshGatewayProperties(ConfigChangeEvent changeEvent) {
        LOGGER.info("Refreshing GatewayProperties!");
        preDestroyGatewayProperties(changeEvent);
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
        refreshGatewayRouteDefinition();
        LOGGER.info("GatewayProperties refreshed!");
    }

    private synchronized void preDestroyGatewayProperties(ConfigChangeEvent changeEvent) {
        LOGGER.info("Pre Destroy GatewayProperties!");

        boolean clearRoutes = this.checkNeedClear(changeEvent, ID_PATTERN, this.gatewayProperties.getRoutes().size());

        if (clearRoutes) {
            this.gatewayProperties.setRoutes(new ArrayList<>());
        }

        // 判断 `spring.cloud.gateway.default-filters` 配置项，是否被全部删除。如果是，则置空 GatewayProperties 的 `defaultFilters` 属性
        boolean needClearDefaultFilters = this.checkNeedClear(changeEvent, DEFAULT_FILTER_PATTERN, this.gatewayProperties.getDefaultFilters().size());
        if (needClearDefaultFilters) {
            this.gatewayProperties.setRoutes(new ArrayList<>());
        }
        LOGGER.info("Pre Destroy GatewayProperties finished!");
    }

    /**
     * 判断是否清除的标准，是通过指定配置项被删除的数量，是否和内存中的该配置项的数量一样。
     * 如果相同，说明数据被清空了
     */
    private boolean checkNeedClear(ConfigChangeEvent changeEvent, String pattern, int existSize) {
        return changeEvent.changedKeys().stream().filter(key -> key.matches(pattern))
                .filter(key -> {
                    ConfigChange change = changeEvent.getChange(key);
                    return PropertyChangeType.DELETED.equals(change.getChangeType());
                }).count() == existSize;
    }

    private void refreshGatewayRouteDefinition() {
        LOGGER.info("Refreshing Gateway RouteDefinition!");
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        LOGGER.info("Gateway RouteDefinition refreshed!");
    }
}
