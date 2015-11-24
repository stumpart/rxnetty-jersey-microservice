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
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

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


        /**
         * TODO: Need to make non blocking. But there is an issue when
         * when moving bytebuf across threads. Race condition issue, stream might be closing
         * before data comes
         */
        stringObservable.run().toBlocking().forEach((b) -> {
            try {
                String res = b.toString(Charset.defaultCharset());
                entityStream.write(res.getBytes());
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }
}
