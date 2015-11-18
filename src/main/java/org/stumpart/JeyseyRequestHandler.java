package org.stumpart;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import org.glassfish.jersey.server.ContainerRequest;
import rx.Observable;
import rx.Subscriber;

import java.util.Map;

/**
 * Created by barringtonhenry on 10/31/15.
 */
public class JeyseyRequestHandler implements RequestHandler<ByteBuf, ByteBuf> {


    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
        return printRequestHeader(request, response);
    }

    public Observable<Void> printRequestHeader(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {


        JerseyApplication resourceConfig = new JerseyApplication();
        //NettyContainer container = ContainerFactory.createContainer(NettyContainer.class, resourceConfig);
        NettyContainer container  = (new NettyContainerProvider()).createContainer(NettyContainer.class, resourceConfig);

        final ContainerRequest containerRequest = container.getNettyToJerseyBridge().bridgeRequest(request, new HttpContentInputStream(response.getAllocator(),request.getContent()));
        containerRequest.setWriter(new NettyResponseWriter(response));

        System.out.println(request.getHttpMethod() + " " + request.getUri() + ' ' + request.getHttpVersion());
        for (Map.Entry<String, String> header : request.getHeaders().entries()) {
            System.out.println(header.getKey() + ": " + header.getValue());
        }

        return Observable.create(new Observable.OnSubscribe<Void>(){
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                container.getApphandler().handle(containerRequest);
                subscriber.onCompleted();
            }
        });
    }
}
