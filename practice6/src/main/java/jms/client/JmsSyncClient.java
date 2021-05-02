package jms.client;

import car.CarServer;
import car.Position;
import jms.command.SerializableCommand;
import jms.command.SerializableReturn;
import jms.server.JmsServer;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static jms.server.JmsServer.commandChannel;
import static jms.server.JmsServer.retChannel;

/**
 * @author : Alex
 * @created : 02.05.2021, воскресенье
 **/
public class JmsSyncClient implements Runnable{
    Session session;
    MessageProducer producer;
    MessageConsumer consumer;
    int carIndex;
    ConcurrentHashMap<Integer, CompletableFuture<SerializableReturn>> hash = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        new Thread(new JmsClient()).start();
        new Thread(new JmsClient()).start();
        new Thread(new JmsClient()).start();
        new Thread(new JmsClient()).start();
        new Thread(new JmsClient()).start();
    }

    SerializableReturn sendCommand(SerializableCommand command){
        try {
            ObjectMessage messageToSend = session.createObjectMessage(command);
            producer.send(messageToSend);
            while(true) { //ждем ответ по своему carIndex
                Message message = consumer.receive();
                if (message instanceof ObjectMessage) {
                    ObjectMessage om = (ObjectMessage) message;
                    SerializableReturn ret = (SerializableReturn) om.getObject();
                    if (ret.carIndex == this.carIndex) return ret;
                } else throw new RuntimeException("Bad message type:" + message);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
    }

    void createConnection(){
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JmsServer.url);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createTopic(commandChannel);

            // Create a MessageConsumer from the Session to the Topic or Queue
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            // Wait for a message
            connection.start();


            Destination source = session.createTopic(retChannel);
            consumer = session.createConsumer(source);

        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }

    }


    @Override
    public void run() {
        createConnection();
        Random random = new Random();
        this.carIndex = random.nextInt(Integer.MAX_VALUE);
        SerializableCommand command = new SerializableCommand(carIndex, "CREATECAR", "");
        SerializableReturn ret = sendCommand(command);
        this.carIndex = (Integer)ret.ret;
        System.out.println("create car. CarIndex="+carIndex);
        command = new SerializableCommand(carIndex, "SETNAME", "Alex");
        sendCommand(command);
        CarServer.Direction direction = CarServer.Direction.DOWN;

        while(true) {
            command = new SerializableCommand(carIndex, direction.name(), "1");
            ret = sendCommand(command);
            System.out.println("return move =" + ret);
            if ((Boolean) ret.ret != true){
                direction = CarServer.Direction.values()[random.nextInt(4)];
                System.out.println("="+carIndex+" change direction to "+direction);
            }
            command = new SerializableCommand(carIndex, "GETPOSITION", "1");
            ret = sendCommand(command);
            System.out.println("return get position =" + ret);
            Position position = (Position)ret.ret;
            command = new SerializableCommand(carIndex, "SETNAME", position.row+"x"+position.col);
            sendCommand(command);
            System.out.println("return set name =" + ret);


        }
    }
}
