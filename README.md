# RxNetty and Jersey base example implementation

Currently being developed...

This is a small activator project demonstrating the use of RxNetty and Jersey to create a reactive based microservice
startup application that. Here is whats included in the service
 * A self contained Non Blocking Asynchrounous HTTP Service
 * Support for WADL
 * Service Composition with RxJava Operators
 * Typesafe configurations
 * Issuing service requests to external services in a non-blocking fashion


Running the application is just like a typical activator project, just do 
```
$ activator run

```
The running port is defaulted to 8090

Accessing the service you do

```
$ curl localhost:8090/homepage

```


