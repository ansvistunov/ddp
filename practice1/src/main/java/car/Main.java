package car;

import car.command.*;



/**
 * @author : Alex
 * @created : 12.03.2021, пятница
 **/
public class Main {
    public static void main(String[] args) throws Exception{
        FieldMatrix fm = new FieldMatrix(10,10);
        CarPainter p = new CarPainter(fm);
        BasicCarServer carServer = new BasicCarServer(fm, p);
        Car car = carServer.createCar();

        Script script = new Script();
        //Command command = new DownCommand(4,car);
        //script.addCommand(command);
        //command = new ChangeColorCommand("green",car);
        //script.addCommand(command);
        //command = new RightCommand(4,car);
        //script.addCommand(command);
        script.execute();

    }
}
