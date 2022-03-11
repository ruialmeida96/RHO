package challenge.RHO;


import challenge.RHO.Model.SensorData;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class Config {

    /*@Bean
    public ConsumerFactory<String, SensorData> studentConsumer()
    {

        // HashMap to store the configurations
        Map<String, Object> map= new HashMap<>();

        // put the host IP in the map
        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");

        // put the group ID of consumer in the map
        map.put(ConsumerConfig.GROUP_ID_CONFIG,"id");
        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);

        // return message in JSON formate
        return new DefaultKafkaConsumerFactory<>(map, new StringDeserializer(),new JsonDeserializer<>(SensorData.class));
    }*/
}
