package jms.server;

import car.BasicCarServer;
import car.CarEventsListener;
import car.CarPainter;
import car.FieldMatrix;
import car.command.Command;
import jms.command.SerializableCommand;
import jms.command.SerializableReturn;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : Alex
 * @created : 01.05.2021, суббота
 **/
public class JmsServer implements MessageListener {
    public static final String url = "tcp://localhost:8080";
    public static final String commandChannel = "COMMANDS";
    public static final String retChannel = "RETS";
    private final BasicCarServer carServer;
    private MessageProducer producer;
    private Session session;
    private final ExecutorService singleThreadExecutorService;


    public static void main(String[] args) {
        InputStream is = CarPainter.class.getClassLoader().getResourceAsStream("Field10x10.txt");
        FieldMatrix fm = FieldMatrix.load(new InputStreamReader(is));
        CarPainter p = new CarPainter(fm);
        JmsServer server = new JmsServer(fm, p);
        server.startEventProcessing();
        System.out.println("JMS Server started");
    }

    private void startEventProcessing() {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination source = session.createTopic(commandChannel);

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(source);

            // Wait for a message
            consumer.setMessageListener(this);
            connection.start();


            Destination destination = session.createTopic(retChannel);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
    }

    public JmsServer(FieldMatrix fieldMatrix, CarEventsListener carEventsListener) {
        carServer = BasicCarServer.createCarServer(fieldMatrix, carEventsListener);
        singleThreadExecutorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onMessage(Message message) {

        if (message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage) message;
            try {
                SerializableCommand messageCommand = (SerializableCommand) om.getObject();
                Command command = Command.createCommand(carServer.getCar(messageCommand.carIndex), messageCommand.commandName + " " + messageCommand.commandparameter);
                System.out.println(command);

                CompletableFuture.supplyAsync(() ->
                    (Serializable) command.execute()
                )
                        .thenAcceptAsync((obj) -> {
                            SerializableReturn serializableReturn = new SerializableReturn(messageCommand.carIndex, obj);
                            try {
                                ObjectMessage returnMessage = session.createObjectMessage(serializableReturn);
                                producer.send(returnMessage);
                            } catch (JMSException e) {
                                e.printStackTrace();
                            }

                        }, singleThreadExecutorService);
            } catch (JMSException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getCause());
            }

        } else throw new RuntimeException("bad message type: " + message);
    }
}
