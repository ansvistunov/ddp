package jms;

import jms.commands.ConsoleCommand;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;

/**
 * @author : Alex
 * @created : 01.05.2021, суббота
 **/
public class Client {
    final ConnectionFactory factory;
    Session session;
    static final String url="tcp://localhost:61616";

    public Client(String url){
        factory = new ActiveMQConnectionFactory(url);
        try {
            Connection connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }

    }

    public static void main(String[] args) {

        Client client = new Client(url);
        Scanner scanner = new Scanner(System.in);
        String line;
        System.out.print(">");
        while( (line = scanner.nextLine())!=null){
            try {
                System.out.print(">");
                ConsoleCommand command = ConsoleCommand.createCommand(client, line, (s) -> System.out.println(s));
                command.execute();
            }catch(Exception e){e.printStackTrace();}
        }

    }

    public MessageProducer createProducer(String destinationName){
        try {
            Destination destination = session.createTopic(destinationName);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            return producer;
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
    }

    public void createMessageConsumer(String destinationName, MessageListener listener){
        try {
            Destination destination = session.createTopic(destinationName);
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(listener);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public TextMessage createMessage(String message){
        try {
            return session.createTextMessage(message);
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
    }


}
