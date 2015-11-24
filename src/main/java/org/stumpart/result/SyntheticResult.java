package org.stumpart.result;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import org.stumpart.operators.ByteBufManual;
import rx.Observable;


public class SyntheticResult {
    public final Observable<HttpClientResponse<ByteBuf>>[] res;

    public SyntheticResult(Observable<HttpClientResponse<ByteBuf>>... res){
        this.res = res;
    }

    public Observable<ByteBuf> run(){

        return Observable.merge(res)
                .flatMap((d) -> d.getContent().lift(ByteBufManual.refBuffer()));
    }
}
