package org.example;

import rx.Observable;
import rx.Subscriber;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    private String[] strs = {"Service 1", "Service 2", "Service  3"};
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Observable<String> getIt() {
        return Observable.create(getSubscribeFunction());
    }

    private Observable.OnSubscribe<String> getSubscribeFunction(){
        return (s) -> {
            Subscriber subscriber = (Subscriber)s;
              for(String st : strs){
                  if (!subscriber.isUnsubscribed()) {
                      subscriber.onNext(st);
                  }
              }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        };
    }

}