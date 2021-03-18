package net.client;

import car.command.ChangeColorCommand;
import car.command.Command;
import car.command.DownCommand;

/**
 * @author : Alex
 * @created : 18.03.2021, четверг
 **/
public class Main {
    public static void main(String[] args) throws Exception{
        CarClient client = new CarClient("localhost",8080);
        Command command = new DownCommand(5, null);
        client.executeCommand(command);

        command = new ChangeColorCommand("blue", null);
        client.executeCommand(command);
        Thread.currentThread().sleep(5000);
    }
}
