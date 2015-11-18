package org.stumpart;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by barringtonhenry on 10/31/15.
 */
public class JerseyApplication extends ResourceConfig {
    public JerseyApplication(){
        packages("org.example");
        register(NettyContainerProvider.class);
        register(ObservableBodyWriter.class);
    }
}
