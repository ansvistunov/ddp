package kafka.commands;

import kafka.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author : Alex
 * @created : 01.05.2021, суббота
 **/
public class CreateConsumerCommand extends ConsoleCommand{

    private final Consumer<String> consumer;
    private static List<String> consumers = new ArrayList<>();

    public CreateConsumerCommand(Client client, String destinationName, String message, Consumer<String> consumer){
        super(client,CommandType.LISTEN,destinationName, message);
        this.consumer = consumer;
    }

    @Override
    public  void execute() {
        synchronized (client) {
            client.subscribe(destination);
            consumers.add(destination);
        }
    }

    public static String getConsumersList(){
        return consumers.toString();
    }
}
