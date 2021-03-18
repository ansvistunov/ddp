package car.command;

import car.Car;
import car.CarServer;

/**
 * @author : Alex
 * @created : 18.03.2021, четверг
 **/
public class MoveCommand extends Command<Integer> {
    private final CarServer.Direction direction;

    static {
        System.out.println("load MoveCommand");
    }

    public MoveCommand(Integer parameter, Car car, CarServer.Direction direction){
        super(parameter, car);
        this.direction = direction;
    }

    @Override
    public void execute() {
        for (int i = 0; i < parameter; i++) car.moveTo(direction);
    }


}
