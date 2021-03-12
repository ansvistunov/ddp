package car;

import car.command.*;

import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @author : Alex
 * @created : 12.03.2021, пятница
 **/
public class Main {
    public static void main(String[] args) throws Exception{
        InputStream is = CarPainter.class.getClassLoader().getResourceAsStream("Field10x10.txt");
        FieldMatrix fm = FieldMatrix.load(new InputStreamReader(is));

        CarPainter p = new CarPainter(fm);
        BasicCarServer carServer = new BasicCarServer(fm, p);
        Car car = carServer.createCar();

        Script script = new Script();
        Command command = new DownCommand(4,car);
        script.addCommand(command);
        command = new ChangeColorCommand("green",car);
        script.addCommand(command);
        command = new RightCommand(6,car);
        script.addCommand(command);
        command = new LeftCommand(3,car);
        script.addCommand(command);
        command = new ChangeColorCommand("blue",car);
        script.addCommand(command);
        command = new UpCommand(2,car);
        script.addCommand(command);
        script.execute();

    }
}
