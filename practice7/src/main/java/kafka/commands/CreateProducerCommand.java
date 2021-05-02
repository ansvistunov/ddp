package kafka.commands;

import kafka.Client;

/**
 * @author : Alex
 * @created : 01.05.2021, суббота
 **/
public class CreateProducerCommand extends ConsoleCommand {

    public CreateProducerCommand(Client client, String destination, String message){
        super(client,CommandType.SEND, destination, message);
    }

    @Override
    public void execute() {
        client.sendMessage(destination,message);
    }
}
