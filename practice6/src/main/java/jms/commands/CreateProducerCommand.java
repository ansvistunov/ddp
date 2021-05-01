package jms.commands;

import jms.Client;

import javax.jms.JMSException;
import javax.jms.MessageProducer;

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
        MessageProducer producer = client.createProducer(destination);
        try {
            producer.send(client.createMessage(message));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
