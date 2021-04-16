package webservice.server;

import car.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.InputStream;
import java.io.InputStreamReader;

@WebService
public class Server {
    BasicCarServer carServer;
    //public static final String url = "http://10.66.66.2:8080/CarServer";
    public static final String url = "http://0.0.0.0:8080/CarServer";

    protected Server(FieldMatrix fieldMatrix, CarEventsListener carEventsListener) {
        carServer = BasicCarServer.createCarServer(fieldMatrix, carEventsListener);
    }

    @WebMethod
    public int createCar() {
        Car car = carServer.createCar();
        return car.getIndex();
    }

    @WebMethod
    public void destroyCar(int carIndex) {
        Car car = carServer.getCar(carIndex);
        carServer.destroyCar(car);
    }

    @WebMethod
    public boolean moveCarTo(int carIndex, CarServer.Direction direction) {
        Car car = carServer.getCar(carIndex);
        boolean ret = false;
        try {
            ret = car.moveTo(direction);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return ret;
    }

    @WebMethod
    public void setCarName(int carIndex, String name){
        carServer.getCar(carIndex).setName(name);
    }

    public static void main(String[] args) {
        InputStream is = CarPainter.class.getClassLoader().getResourceAsStream("Field10x10.txt");
        FieldMatrix fm = FieldMatrix.load(new InputStreamReader(is));
        CarPainter p = new CarPainter(fm);

        Server server = new Server(fm,p);
        Endpoint.publish(url, server);
    }
}
