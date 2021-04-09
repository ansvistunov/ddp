package hello;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class Hello {
    @WebMethod
    public String sayHello(){
        return "Hello!";
    }

    public static void main(String[] args) {
        Hello hello = new Hello();
        Endpoint.publish("http://localhost:8080/Hello", hello);
    }
}
