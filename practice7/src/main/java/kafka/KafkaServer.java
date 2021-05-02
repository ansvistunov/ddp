package kafka;

import car.BasicCarServer;
import car.CarEventsListener;
import car.CarPainter;
import car.FieldMatrix;
import car.command.Command;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author : Alex
 * @created : 02.05.2021, воскресенье
 **/
public class KafkaServer {
    final BasicCarServer carServer;
    public static final String commandChannel = "COMMANDS";
    public static final String retChannel = "RETS";

    static final String user = "admin";
    static final String password = "admin-secret";
    static final String broker = "localhost:9092";

    KafkaProducer<Integer, String> producer;
    KafkaConsumer<Integer, String> consumer;

    public static void main(String[] args) {
        InputStream is = CarPainter.class.getClassLoader().getResourceAsStream("Field10x10.txt");
        FieldMatrix fm = FieldMatrix.load(new InputStreamReader(is));
        CarPainter p = new CarPainter(fm);
        KafkaServer server = new KafkaServer(fm, p);
        System.out.println("Kafka Server starting...");
        server.startEventProcessing();

    }

    private Properties prepareProperties(String username, String password, String brokers) {
        String jaasTemplate = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";";
        String jaasCfg = String.format(jaasTemplate, username, password);
        String valueSerializer = StringSerializer.class.getName();
        String valueDeserializer = StringDeserializer.class.getName();

        String keySerializer = IntegerSerializer.class.getName();
        String keyDeserializer = IntegerDeserializer.class.getName();


        Properties properties = new Properties();
        properties.put("bootstrap.servers", brokers);
        properties.put("group.id", username + "-consumer");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", keyDeserializer);
        properties.put("value.deserializer", valueDeserializer);
        properties.put("key.serializer", keySerializer);
        properties.put("value.serializer", valueSerializer);
        properties.put("security.protocol", "SASL_PLAINTEXT");
        properties.put("sasl.mechanism", "PLAIN");
        properties.put("sasl.jaas.config", jaasCfg);
        return properties;
    }


    private void startEventProcessing() {
        Properties properties = prepareProperties(user, password, broker);
        producer = new KafkaProducer<>(properties);
        consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Arrays.asList(commandChannel));

        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<Integer, String> record : records) {
                System.out.println("received " + record.key()+":"+record.value() + " from " + record.topic() + " offset:"+record.offset());
                try {
                    Command command = Command.createCommand(carServer.getCar(record.key()), record.value());
                    Object ret = command.execute();
                    System.out.println("ret=" + ret);
                    producer.send(new ProducerRecord<Integer, String>(retChannel, record.key(), ret.toString()));
                }catch(Exception e){e.printStackTrace();}

            }
            consumer.commitSync();
        }
    }

    public KafkaServer(FieldMatrix fieldMatrix, CarEventsListener carEventsListener) {
        carServer = BasicCarServer.createCarServer(fieldMatrix, carEventsListener);
    }


}
