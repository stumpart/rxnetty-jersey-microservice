package org.stumpart;

import org.glassfish.jersey.server.spi.ContainerProvider;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Application;

/**
 * Created by barringtonhenry on 10/31/15.
 */
public class NettyContainerProvider implements ContainerProvider{

    @Override
    public <T> T createContainer(Class<T> type, Application application) throws ProcessingException {
        if (type != NettyContainer.class) {
            return null;
        }

        return type.cast(new NettyContainer(application));
    }
}
