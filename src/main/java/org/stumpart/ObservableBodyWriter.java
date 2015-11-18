package org.stumpart;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import org.stumpart.result.SyntheticResult;
import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Created by barringtonhenry on 11/1/15.
 */
@Provider
@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
public class ObservableBodyWriter implements MessageBodyWriter<SyntheticResult> {
    @Override
    public boolean isWriteable(Class<?> type,
                               Type genericType,
                               Annotation[] annotations,
                               MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(SyntheticResult stringObservable,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType) {
        return -1L;
    }

    @Override
    public void writeTo(SyntheticResult stringObservable,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {

        System.out.println("in the writeto....");
        //entityStream.write(( "Barrington out stream..  ").getBytes());
        stringObservable.run().subscribe(new Subscriber<ByteBuf>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(ByteBuf byteBuf) {
                ByteBuf myBuf = byteBuf.retain();
                System.out.println("into next...");
                System.out.println("Thread Id Netty : " + Thread.currentThread().getId() + " - " + Thread.currentThread().getName());

                try{
                    //System.out.println(byteBufHttpClientResponse);
                   //entityStream.write("barrington...".getBytes());
                   //ObjectOutputStream ob = new ObjectOutputStream(entityStream);
                   // ob.writeObject(myBuf);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        /*stringObservable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                try{
                    entityStream.write((s + "..  ").getBytes());
                }catch(Exception e){}
            }
        });*/
    }
}
