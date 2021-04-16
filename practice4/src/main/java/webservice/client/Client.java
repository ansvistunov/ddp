package webservice.client;

import webservice.service.Direction;
import webservice.service.Server;
import webservice.service.ServerService;

import java.net.URL;
import java.util.Random;

public class Client {
    //static final String wsdl = "http://132.145.228.39:8080/CarServer?wsdl";
    static final String wsdl = "http://localhost:8080/CarServer?wsdl";

    static class CarMover implements Runnable {
        private  Server server;
        public CarMover(String url) throws Exception{
            ServerService service = new ServerService(new URL(url));
            server = service.getServerPort();
            System.out.println(server);

        }

        @Override
        public void run() {
            int carIndex = server.createCar();
            server.setCarName(carIndex,"car"+carIndex);
            Direction direction = Direction.DOWN;
            Random random = new Random();
            System.out.println("carIndex="+carIndex);
            while(true){
                if (!server.moveCarTo(carIndex, direction)){
                    direction = Direction.values()[random.nextInt(4)];

                }
            }

        }
    }

    public static void main(String[] args) throws Exception{
        new Thread(new CarMover(wsdl)).start();
        new Thread(new CarMover(wsdl)).start();
        new Thread(new CarMover(wsdl)).start();
        new Thread(new CarMover(wsdl)).start();
    }
}
