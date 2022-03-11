package challenge.RHO;

import challenge.RHO.Model.JSONDeserializing;
import challenge.RHO.Model.JSONSerializer;
import challenge.RHO.Model.SensorData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.catalina.connector.Request;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
public class RhoApplication {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JSONDeserializing.class);

        SpringApplication.run(RhoApplication.class, args);


        /*Serde<Request> requestSerde = Serdes.serdeFrom(new JSONSerializer(), new JSONDeserializing(Request.class));
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, requestSerde.getClass().getName());*/

        KafkaConsumer<String,JsonNode> consumer = new KafkaConsumer<String, JsonNode>(props);
        consumer.subscribe(Arrays.asList("teste"));
        ObjectMapper mapper = new ObjectMapper();

        //Start processing messages
        try {
            while (true) {
                ConsumerRecords<String, JsonNode> records = consumer.poll(100);
                for (ConsumerRecord<String, JsonNode> record : records) {
                    JsonNode jsonNode = record.value();
                    System.out.println(mapper.treeToValue(jsonNode,SensorData.class));
                }
            }
        }catch(WakeupException ex){
            System.out.println("Exception caught " + ex.getMessage());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally{
            consumer.close();
            System.out.println("After closing KafkaConsumer");
        }
    }
}
