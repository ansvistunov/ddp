package grpc.client;

import car.CarServer;
import java.util.Random;

/**
 * @author : Alex
 * @created : 13.04.2021, вторник
 **/
public class Main {
    public static final String host = "localhost";
    public static final int port = 8080;
    public static int index = 0;

    public static void main(String[] args) {
        class CarMover implements Runnable{

            @Override
            public void run() {
                GrpcClient client = new GrpcClient(host, port);
                int carIndex = client.createCar("Alex"+index++, "green");
                Random random = new Random();
                CarServer.Direction direction = CarServer.Direction.DOWN;
                while(true) {
                   boolean result = client.moveCar(carIndex, direction, 7);
                   if (!result){
                       direction = CarServer.Direction.values()[random.nextInt(4)];
                   }
                }
            }
        }
        new Thread(new CarMover()).start();
        new Thread(new CarMover()).start();
        new Thread(new CarMover()).start();
        new Thread(new CarMover()).start();
    }
}
