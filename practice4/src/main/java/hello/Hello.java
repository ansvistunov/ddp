package hello;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class Hello {
    public static final String url = "http://0.0.0.0:8080/Hello"; //bind all IPv4 interfaces

    @WebMethod
    public String sayHello(){
        return "Hello!";
    }

    public static void main(String[] args) {
        Hello hello = new Hello();
        Endpoint.publish(url, hello);
        System.out.println("WebService started");
    }
}
