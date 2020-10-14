package com.test.cloud.config.user.config;

import java.util.UUID;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Prototype scope bean，like @RefreshScope bean,可以更好的理解@RefreshScope实现原理
 * <br/>
 * singleton scope bean 也可以，注意 proxyMode = ScopedProxyMode.TARGET_CLASS
 */
@Scope(scopeName= ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class PrototypeScopeProperties {

    private String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
