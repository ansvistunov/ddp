package hello;

import hello.webservice.Hello;
import hello.webservice.HelloService;

public class HelloClient {
    public static void main(String[] args) {
        HelloService helloService = new HelloService();
        Hello hello = helloService.getHelloPort();
        System.out.println(hello.sayHello());
    }


}
