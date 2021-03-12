package car.command;

import car.Car;
import car.CarServer;

/**
 * @author : Alex
 * @created : 12.03.2021, пятница
 **/
public class RightCommand extends Command<Integer>{
    public RightCommand(Integer parameter, Car car){
        super(parameter, car);
    }

    @Override
    public void execute() {
        for (int i = 0; i < parameter; i++) car.moveTo(CarServer.Direction.RIGHT);
        System.out.println("Down Command exit");
    }
}
