package org.example;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import org.stumpart.result.SyntheticResult;
import rx.Observable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by barringtonhenry on 11/2/15.
 */
@Path("homepage")
public class HomePage {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public SyntheticResult home()  throws InterruptedException, ExecutionException, TimeoutException {
        Observable<HttpClientResponse<ByteBuf>> swapi =  RxNetty.createHttpGet("http://swapi.co/api/people/3/");
        Observable<HttpClientResponse<ByteBuf>> users =  RxNetty.createHttpGet("http://jsonplaceholder.typicode.com/users");

        return new SyntheticResult(swapi, users);

                /*.flatMap(response -> {
                    return response.getContent().<String>map(content -> {
                        System.out.println(content.toString(Charset.defaultCharset()));
                        return content.toString(Charset.defaultCharset());
                    });
                }).toBlocking().toFuture().get(1, TimeUnit.MINUTES);*/
    }
}
