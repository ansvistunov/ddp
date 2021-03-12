package car.command;

import car.Car;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;
import java.util.function.Function;


/**
 * @author : Alex
 * @created : 11.03.2021, четверг
 **/
public abstract class Command<T> {
    protected final T parameter;
    protected final Car car;
    protected static Properties commandfactory;

    static{
        try {
            InputStream is = Command.class.getClassLoader().getResourceAsStream("commands.txt");
            commandfactory = new Properties();
            commandfactory.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Command(T parameter, Car car){
        this.parameter = parameter;
        this.car = car;
    }

    public static Command loadCommand(String parameter, Car car){return null;}

    public static Command createCommand(Car car, String nextLine) {
        String[] tokens = nextLine.split("\\s+");
        DownCommand cmd = new DownCommand(null,null);
        try {
            String className = commandfactory.getProperty(tokens[0].toUpperCase());
            Class c = Class.forName(className);
            Method method = c.getMethod("loadCommand",String.class, Car.class);
            Command command = (Command)method.invoke(null, tokens[1], car);
            return command;
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); throw new RuntimeException(e.getException());
        } catch (NoSuchMethodException e) {
            e.printStackTrace(); throw new RuntimeException(e.getCause());
        } catch (IllegalAccessException e) {
            e.printStackTrace(); throw new RuntimeException(e.getCause());
        } catch (InvocationTargetException e) {
            e.printStackTrace(); throw new RuntimeException(e.getCause());
        }

    }
    public abstract void execute();
}
