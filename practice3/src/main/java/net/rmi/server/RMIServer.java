package net.rmi.server;

import car.*;
import car.command.Command;
import net.command.SerializableCommand;
import net.rmi.RemoteCarServer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author : Alex
 * @created : 31.03.2021, среда
 **/
public class RMIServer extends BasicCarServer implements RemoteCarServer {
    public static final int port = 8080;
    public static final String carServerName = "RMICarServer";
    public static final int maxCarSecondsDonNotMove = 10;

    protected RMIServer(FieldMatrix fieldMatrix, CarEventsListener carEventsListener) {
        super(fieldMatrix, carEventsListener);
        TimerTask carCleaner = new TimerTask() {
            @Override
            public void run() {
                //System.out.println("start cleaner...");
                List<Car> carsToRemove = cars.stream()
                        .filter(
                                (car)->car.getLastOperation().isBefore(LocalDateTime.now().minusSeconds(maxCarSecondsDonNotMove))
                        ).collect(Collectors.toList());
                for(Car car:carsToRemove) {
                    System.out.println("destroy "+car);
                    car.destroy();
                }
            }
        };
        Timer timer = new Timer("CarCleaner");
        timer.scheduleAtFixedRate(carCleaner,0,10000);
    }

    public static void main(String[] args) throws Exception{
        InputStream is = CarPainter.class.getClassLoader().getResourceAsStream("Field10x10.txt");
        FieldMatrix fm = FieldMatrix.load(new InputStreamReader(is));
        CarPainter p = new CarPainter(fm);
        RMIServer server = new RMIServer(fm, p);
        Registry registry = LocateRegistry.createRegistry(port);
        RemoteCarServer proxy = (RemoteCarServer) UnicastRemoteObject.exportObject(server,port);
        registry.rebind(carServerName, proxy);
        System.out.println("RMI Car Server started on port "+port);
    }

    @Override
    public <T> T executeCommand(SerializableCommand command) {
        Command cmd = Command.createCommand(getCar(command.carIndex),command.commandName + " " +command.commandparameter);
        return (T)cmd.execute();
    }
}
