package org.stumpart;

import org.glassfish.jersey.server.ApplicationHandler;

import javax.ws.rs.core.Application;

/**
 * Created by barringtonhenry on 10/31/15.
 */
public class NettyContainer {
    private Application app;
    private final ApplicationHandler applicationHandler;
    private final NettyToJerseyBridge nettyToJerseyBridge;

    public NettyContainer(Application application){
            app = application;
            applicationHandler = new ApplicationHandler(app);
            nettyToJerseyBridge = new NettyToJerseyBridge(application);
    }

    public ApplicationHandler getApphandler(){
        return applicationHandler;
    }

    public NettyToJerseyBridge getNettyToJerseyBridge(){
        return nettyToJerseyBridge;
    }

}
