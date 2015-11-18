package org.stumpart;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import org.glassfish.jersey.internal.MapPropertiesDelegate;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.core.Application;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by barringtonhenry on 10/31/15.
 */
final  class NettyToJerseyBridge {

    private final Application application;

    NettyToJerseyBridge(Application  jerseyApplication){
        this.application  = jerseyApplication;
    }


    ContainerRequest bridgeRequest(final HttpServerRequest<ByteBuf> nettyRequest, InputStream requestData){
        try {
            URI baseUri = new URI("/"); // Since the netty server does not have a context path element as such, so base uri is always /
            URI uri = new URI(nettyRequest.getUri());

            DefaultSecurityContextFactory contextFactory = new DefaultSecurityContextFactory();
            SecurityContext securityContext = contextFactory.create();
            ContainerRequest containerRequest = new ContainerRequest(
                    baseUri,
                    uri,
                    nettyRequest.getHttpMethod().name(),
                    securityContext,
                    new MapPropertiesDelegate()
            );
            containerRequest.setEntityStream(requestData);

            return containerRequest;

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }




}
