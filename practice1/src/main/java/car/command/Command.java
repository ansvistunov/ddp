package car.command;

import car.Car;
import java.util.HashMap;


/**
 * @author : Alex
 * @created : 11.03.2021, четверг
 **/
public abstract class Command<T> {
    protected final T parameter;
    protected final Car car;

    public Command(T parameter, Car car){
        this.parameter = parameter;
        this.car = car;
    }

    public static Command createCommand(Car car, String nextLine) {
        String[] tokens = nextLine.split("\\s+");
        switch(tokens[0].toUpperCase()){
            case "UP": return new UpCommand(Integer.parseInt(tokens[1]), car);
            case "DOWN": return new DownCommand(Integer.parseInt(tokens[1]), car);
            case "RIGHT": return new RightCommand(Integer.parseInt(tokens[1]), car);
            case "LEFT": return new LeftCommand(Integer.parseInt(tokens[1]), car);
            case "CHANGECOLOR": return new ChangeColorCommand(tokens[1], car);
            default: throw new RuntimeException("Bad command:"+tokens[0]);
        }
    }
    public abstract void execute();
}
