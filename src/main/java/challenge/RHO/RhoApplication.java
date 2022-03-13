package challenge.RHO;

import challenge.RHO.DBConnection.DBConnector;
import challenge.RHO.Model.Sensor;
import challenge.RHO.Model.SensorData;
import challenge.RHO.Serializer.JSONDeserializerTest;
import challenge.RHO.Utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

@SpringBootApplication
public class RhoApplication {

    public static ArrayList<Sensor> lista_sensores = new ArrayList<>();

    private static DBConnector dbConnector = null;

    public static void main(String[] args) {

        SpringApplication.run(RhoApplication.class, args);

        dbConnector =  new DBConnector("jdbc:mysql://localhost:3306/sys","root","root");

        dbConnector.ConnectDataBase();
        boolean con = dbConnector.isConnected();
        System.out.println("DB is connected ? -->" + con);

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JSONDeserializerTest.class);
        props.put(JSONDeserializerTest.VALUE_CLASS_NAME_CONFIG,SensorData.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"SensorDataConsumerGroup");
        //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");


        int total_sensors = dbConnector.selectCountSensors();
        //INSERIR DADOS DE SENSORES POR DEFEITO CASO NAO TENHA VALORES NOS SENSORES (criamos pelomenos 3 sensores)
        if (total_sensors == 0){
            for (int i = 1; i<=3;i++){
                Sensor insere_sensor = new Sensor("Sensor "+i, Utils.return_current_date(),Utils.return_current_time(),40.714+i,-74.006+i,0,0,null);
                dbConnector.insereSensor(insere_sensor);
                lista_sensores.add(insere_sensor);
            }
        }else{
            //carregar os valores da BD
            lista_sensores = dbConnector.selectallSensors();
        }

        //aqui vai criar a lista de sensores (ir buscar à BD)
        //lista_sensores.add(new Sensor(1,"Sensor 1",new Date(System.currentTimeMillis()),2.222,1.1111,0,0,null));

        /*KafkaConsumer<String,SensorData> consumer = new KafkaConsumer<String, SensorData>(props);
        consumer.subscribe(Arrays.asList("testeSensorData"));
        ObjectMapper mapper = new ObjectMapper();

        //Start processing messages
        try {
            while (true) {
                ConsumerRecords<String, SensorData> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, SensorData> record : records) {

                }
            }
        }catch(WakeupException ex){
            System.out.println("Exception caught " + ex.getMessage());
        } finally{
            consumer.close();
            System.out.println("After closing KafkaConsumer");
        }*/
    }
}
