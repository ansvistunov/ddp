package net.rmi.client;

import car.CarServer;
import net.command.SerializableCommand;
import net.rmi.RemoteCarServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

/**
 * @author : Alex
 * @created : 31.03.2021, среда
 **/
public class RMIClient {
    public static final int port = 8080;
    public static final String carServerName = "RMICarServer";
    public static final String host = "132.145.228.39";
    static int index = 0;

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry(host, port);
        RemoteCarServer server = (RemoteCarServer) registry.lookup(carServerName);
        System.out.println(String.format("Connected to registry on host %s and port %d",host, port));

        class CarMover implements Runnable {

            @Override
            public void run() {
                try {
                    CarServer.Direction direction = CarServer.Direction.RIGHT;
                    Random random = new Random();
                    SerializableCommand command = new SerializableCommand(0, "CREATECAR", "");
                    Integer carIndex = server.executeCommand(command);

                    command = new SerializableCommand(carIndex, "SETNAME", "car" + index++);
                    server.executeCommand(command);

                    while (true) {
                        try {
                            command = new SerializableCommand(carIndex, direction.name(), "1");
                            boolean moved = server.executeCommand(command);
                            if (!moved) {
                                direction = CarServer.Direction.values()[random.nextInt(4)];
                            }
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                            return;
                        } catch (Exception e) {
                            //e.printStackTrace();
                            direction = CarServer.Direction.values()[random.nextInt(4)];
                        }
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        new Thread(new CarMover()).start();
        new Thread(new CarMover()).start();
        new Thread(new CarMover()).start();
        new Thread(new CarMover()).start();

    }
}
