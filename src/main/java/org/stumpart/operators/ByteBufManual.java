package org.stumpart.operators;

import io.netty.buffer.ByteBuf;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by barringtonhenry on 11/14/15.
 */
public class ByteBufManual {

    private static final Observable.Operator<ByteBuf, ByteBuf> REF_BUFFER = ByteBufManual::refBuffer;

    public static Observable.Operator<ByteBuf, ByteBuf> refBuffer() {
        return REF_BUFFER;
    }

    private static Subscriber<ByteBuf> refBuffer(final Subscriber<? super ByteBuf> child) {
        return new Subscriber<ByteBuf>() {
            @Override
            public void onCompleted() {
                child.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                child.onError(e);
            }

            @Override
            public void onNext(ByteBuf buffer) {
                if (!child.isUnsubscribed()) {
                    buffer.retain();
                    add(new BufferSubscription(buffer));
                    System.out.println("Byte Buff Manual....");
                    child.onNext(buffer);
                }
            }
        };
    }

    private static class BufferSubscription implements Subscription {
        private final ByteBuf buffer;
        private final AtomicBoolean unsubscibed = new AtomicBoolean(false);

        public BufferSubscription(ByteBuf buffer) {
            this.buffer = buffer;
        }

        @Override
        public void unsubscribe() {
            if (unsubscibed.compareAndSet(false, true)) {
                buffer.release();
            }
        }

        @Override
        public boolean isUnsubscribed() {
            return unsubscibed.get();
        }
    }
}
