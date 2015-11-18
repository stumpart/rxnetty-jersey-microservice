package org.stumpart.result;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import org.stumpart.operators.ByteBufManual;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

import java.nio.charset.Charset;

/**
 * Created by barringtonhenry on 11/7/15.
 */
public class SyntheticResult {
    public final Observable<HttpClientResponse<ByteBuf>>[] res;

    public SyntheticResult(Observable<HttpClientResponse<ByteBuf>>... res){
        this.res = res;
    }

    public Observable<ByteBuf> run(){

        return Observable.merge(res)
                .flatMap((d) -> d.getContent().lift(ByteBufManual.refBuffer()));
        /*return Observable.merge(res).doOnNext(new Action1<HttpClientResponse<ByteBuf>>() {
            @Override
            public void call(HttpClientResponse<ByteBuf> byteBufHttpClientResponse) {
                System.out.println("do on next....");
            }
        });*/
    }
}
