package com.test.cloud.custom.loadbalancer.annotation;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.cloud.context.named.NamedContextFactory;

public class CustomBalancerClientSpecification implements NamedContextFactory.Specification {
    private final String name;

    private final Class<?>[] configuration;

    public CustomBalancerClientSpecification(String name, Class<?>[] configuration) {
        this.name = name;
        this.configuration = configuration;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<?>[] getConfiguration() {
        return configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomBalancerClientSpecification)) {
            return false;
        }
        CustomBalancerClientSpecification that = (CustomBalancerClientSpecification) o;
        return Objects.equals(name, that.name) && Arrays.equals(configuration, that.configuration);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(configuration);
        return result;
    }
}
