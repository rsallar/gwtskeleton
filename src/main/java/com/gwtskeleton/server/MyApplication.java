package com.gwtskeleton.server;
import org.glassfish.jersey.server.ResourceConfig;


public class MyApplication extends ResourceConfig {
    public MyApplication() {
        packages("com.gwtskeleton.server");
    }
}