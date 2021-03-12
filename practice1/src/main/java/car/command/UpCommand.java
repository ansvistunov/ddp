package car.command;

import car.Car;
import car.CarServer;

/**
 * @author : Alex
 * @created : 12.03.2021, пятница
 **/
public class UpCommand extends Command<Integer>{
    public UpCommand(Integer parameter, Car car){
        super(parameter, car);
    }

    @Override
    public void execute() {
        for (int i = 0; i < parameter; i++) car.moveTo(CarServer.Direction.UP);
    }
    public static Command loadCommand(String parameter, Car car){
        return new UpCommand(Integer.parseInt(parameter), car);
    }
}
