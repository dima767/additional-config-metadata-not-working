package org.example.additional.metadata;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "my")
public class MyConfigProps {

    /**
     * int-prop is a test property for testing Spring Boot configuration annotation processor.
     *
     * Additional hits metedata should work, but it does not.
     */
    private int intProp = 42;

    public int getIntProp() {
        return intProp;
    }

    public void setIntProp(int intProp) {
        this.intProp = intProp;
    }
}
