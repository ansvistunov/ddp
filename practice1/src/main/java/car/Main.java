package car;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


/**
 * @author : Alex
 * @created : 12.03.2021, пятница
 **/
public class Main {
    public static void main(String[] args) throws Exception{
        InputStream is = CarPainter.class.getClassLoader().getResourceAsStream("Field10x10.txt");
        System.out.println(is);
        FieldMatrix fm = FieldMatrix.load(new InputStreamReader(is));
        CarPainter p = new CarPainter(fm);
        BasicCarServer carServer = new BasicCarServer(fm, p);


        class CarMover implements Runnable{
            private final Car car;
            CarMover(Car car){
                this.car = car;
            }
            @Override
            public void run() {
                CarServer.Direction direction = CarServer.Direction.RIGHT;
                Random random = new Random();
                while (true) {
                    try {
                        if (!car.moveTo(direction)){
                            direction = CarServer.Direction.values()[random.nextInt(4)];
                        }
                    } catch (Exception e) {
                        direction = CarServer.Direction.values()[random.nextInt(4)];
                    }
                }
            }
        }


        Car car = carServer.createCar();
        new Thread(new CarMover(car)).start();

        car = carServer.createCar();
        new Thread(new CarMover(car)).start();

        car = carServer.createCar();
        new Thread(new CarMover(car)).start();

        car = carServer.createCar();
        new Thread(new CarMover(car)).start();

    }
}
