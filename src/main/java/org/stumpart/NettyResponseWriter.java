package org.stumpart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import org.glassfish.jersey.server.ContainerException;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.spi.ContainerResponseWriter;

import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by barringtonhenry on 11/1/15.
 */
public class NettyResponseWriter implements ContainerResponseWriter {

    private final HttpServerResponse<ByteBuf> serverResponse;
    private ByteBuf contentBuffer;

    public NettyResponseWriter(final HttpServerResponse<ByteBuf> serverResponse){
        this.serverResponse = serverResponse;
    }

    @Override
    public OutputStream writeResponseStatusAndHeaders(long contentLength, ContainerResponse responseContext) throws ContainerException {
        contentBuffer = serverResponse.getChannel().alloc().buffer();
        return new ByteBufOutputStream(contentBuffer);
    }

    @Override
    public boolean suspend(long timeOut, TimeUnit timeUnit, TimeoutHandler timeoutHandler) {
        return false;
    }

    @Override
    public void setSuspendTimeout(long timeOut, TimeUnit timeUnit) throws IllegalStateException {

    }

    @Override
    public void commit() {
        System.out.println("flushing...");
        System.out.println("Thread Id Netty : " + Thread.currentThread().getId() + " - " + Thread.currentThread().getName());
        serverResponse.writeAndFlush(contentBuffer);
    }

    @Override
    public void failure(Throwable error) {

    }

    @Override
    public boolean enableResponseBuffering() {
        return true;
    }
}
