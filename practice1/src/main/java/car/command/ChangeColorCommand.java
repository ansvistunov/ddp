package car.command;

import car.Car;
import car.CarServer;
import car.util.ColorFactory;

/**
 * @author : Alex
 * @created : 12.03.2021, пятница
 **/
public class ChangeColorCommand extends Command<String> {
    public ChangeColorCommand(String parameter, Car car){
        super(parameter, car);
    }

    @Override
    public void execute() {
        car.setColor(ColorFactory.getColor(parameter));
    }
}
