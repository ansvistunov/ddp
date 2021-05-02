package kafka;

import kafka.commands.ConsoleCommand;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.*;

/**
 * @author : Alex
 * @created : 01.05.2021, суббота
 **/
public class Client implements Runnable{
    final KafkaProducer<String, String> producer;
    final KafkaConsumer<String, String> consumer;
    final List<String> listenTokens;

    static final String user = "admin";
    static final String password = "admin-secret";
    static final String broker = "localhost:9092";
    static final String topic = "test";


    public Client(String username, String password,String brokers, String topicName){
        listenTokens = new ArrayList<>();

        String jaasTemplate = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";";
        String jaasCfg = String.format(jaasTemplate, username, password);
        String serializer = StringSerializer.class.getName();
        String deserializer = StringDeserializer.class.getName();

        Properties properties = new Properties();
        properties.put("bootstrap.servers", brokers);
        properties.put("group.id", username + "-consumer");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", deserializer);
        properties.put("value.deserializer", deserializer);
        properties.put("key.serializer", serializer);
        properties.put("value.serializer", serializer);
        properties.put("security.protocol", "SASL_PLAINTEXT");
        properties.put("sasl.mechanism", "PLAIN");
        properties.put("sasl.jaas.config", jaasCfg);

        producer = new KafkaProducer<>(properties);

        consumer = new KafkaConsumer<>(properties);


    }

    public static void main(String[] args) {

        Client client = new Client(user, password, broker, topic);
        client.subscribe(topic);
        new Thread(client).start();
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


    public void sendMessage(String topic, String message){
        producer.send(new ProducerRecord<String, String>(topic,message));
    }

    public void subscribe(String topicName){
        listenTokens.add(topicName);
        consumer.subscribe(listenTokens);
    }


    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("received " + record.value() + " from " + record.topic());
                }
            }
        }

    }
}
