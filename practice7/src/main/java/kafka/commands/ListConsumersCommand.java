package kafka.commands;

import kafka.Client;

/**
 * @author : Alex
 * @created : 01.05.2021, суббота
 **/
public class ListConsumersCommand extends ConsoleCommand {
    public ListConsumersCommand(Client client, String destination, String message){
        super(client, CommandType.LIST,destination,message);
    }
    @Override
    public void execute() {
        System.out.println(CreateConsumerCommand.getConsumersList());
    }
}
