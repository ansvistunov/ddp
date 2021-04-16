package hello;

//import hello.webservice.Hello;
//import hello.webservice.HelloService;

import java.net.URL;

public class HelloClient {
    public static final String url = "http://localhost:8080/Hello?wsdl";
    //http://132.145.228.39:8080/Hello?wsdl
    public static void main(String[] args) throws Exception{
        hello.webservice.HelloService helloService = new hello.webservice.HelloService();
        hello.webservice.Hello hello = helloService.getHelloPort();
        System.out.println(hello);
        System.out.println(hello.sayHello());
    }


}
