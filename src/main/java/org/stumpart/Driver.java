package org.stumpart;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;

import javax.ws.rs.core.Application;

class Driver {
    static final int DEFAULT_PORT = 8090;

    private final int port;

    private Application application;

    public Driver(int port){
        this.port = port;
    }

    public HttpServer<ByteBuf, ByteBuf> createServer() {
        return RxNetty.createHttpServer(port, new JeyseyRequestHandler());
    }

    public static void main(String[] args) {
        System.out.println("HTTP hello world server starting ...");
        new Driver(DEFAULT_PORT).createServer().startAndWait();
    }
}
