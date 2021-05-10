package kafka;

import car.CarServer;
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

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

import static car.BasicCarServer.maxCarSecondsDonNotMove;

/**
 * @author : Alex
 * @created : 02.05.2021, воскресенье
 **/
public class KafkaClient implements Runnable{

    final KafkaProducer<Integer, String> producer;
    final KafkaConsumer<Integer, String> consumer;

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

    public KafkaClient(){
        Properties properties = prepareProperties(KafkaServer.user, KafkaServer.password, KafkaServer.broker);
        producer = new KafkaProducer<>(properties);
        consumer = new KafkaConsumer<>(properties);

    }

    public String sendCommand(int carIndex, String command){
        producer.send(new ProducerRecord<Integer,String>(KafkaServer.commandChannel, carIndex, command),
                (recordMetadata, e) -> System.out.println("message send. offset is "+recordMetadata.offset()));

        consumer.subscribe(Arrays.asList(KafkaServer.retChannel+"-"+carIndex));

        while(true) {
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(maxCarSecondsDonNotMove * 1000));
            for (ConsumerRecord<Integer, String> record : records) {
                System.out.println("received " + record.value() + " from " + record.topic() + " offset "+record.offset());
                if (carIndex == record.key()) return record.value();
            }
            consumer.commitSync();
        }


    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new KafkaClient()).start();
        //new Thread(new KafkaClient()).start();
    }

    @Override
    public void run() {
        Random random = new Random();

        String ret = sendCommand(random.nextInt(Integer.MAX_VALUE), "CREATECAR ");
        int carIndex = Integer.parseInt(ret);
        CarServer.Direction direction = CarServer.Direction.DOWN;

        while(true){
            ret = sendCommand(carIndex, direction.name().toUpperCase()+" 1");
            Boolean success = Boolean.valueOf(ret);
            if (!success) direction = CarServer.Direction.values()[random.nextInt(4)];
        }
    }
}
