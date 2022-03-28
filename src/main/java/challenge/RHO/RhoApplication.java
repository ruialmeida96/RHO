package challenge.RHO;

import challenge.RHO.DBConnection.DBConnector;
import challenge.RHO.Model.Sensor;
import challenge.RHO.Model.SensorData;
import challenge.RHO.Serializer.JSONDeserializerTest;
import challenge.RHO.Utils.Recetora;
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

    public static DBConnector dbConnector = null;

    public static void main(String[] args) {

        SpringApplication.run(RhoApplication.class, args);

        //dbConnector = new DBConnector("jdbc:mysql://172.0.0.1:3306/sys", "root", "root");
        dbConnector = new DBConnector("jdbc:mysql://localhost:3306/sys", "root", "root");

        //inicializar conexão com a DB
        dbConnector.ConnectDataBase();
        boolean con = dbConnector.isConnected();
        System.out.println("DB is connected ? -->" + con);

        //criar database se ela não existir
        dbConnector.create_database();


        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JSONDeserializerTest.class);
        props.put(JSONDeserializerTest.VALUE_CLASS_NAME_CONFIG, SensorData.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "SensorDataConsumerGroup");
        //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        //obter o total de sensores
        int total_sensors = dbConnector.selectCountSensors();
        //INSERIR DADOS DE SENSORES POR DEFEITO CASO NAO TENHA VALORES NOS SENSORES (criamos pelomenos 3 sensores)
        if (total_sensors == 0) {
            for (int i = 1; i <= 3; i++) {
                Sensor insere_sensor = new Sensor("Sensor " + i, Utils.return_current_date(), Utils.return_current_time(), 40.714 + i, -74.006 + i, 25, 0, null);
                dbConnector.insereSensor(insere_sensor);
                lista_sensores.add(insere_sensor);
            }
        } else {
            //obter os valores da BD
            lista_sensores = dbConnector.selectallSensors();
        }

        //Tentativa de criar uma Thread para cada sensor, atraves da distinção do partition
        /*Thread[] dispatchers = new Thread[3];
        for (int i = 1; i <= 3; i++) {
            dispatchers[i - 1] = new Thread(new Recetora(new KafkaConsumer<String, SensorData>(props), "testeSensorData", i));
            dispatchers[i - 1].start();
        }
        try {
            for (Thread t : dispatchers)
                t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //inicializar o subscritor do Kafka
        KafkaConsumer<String, SensorData> consumer = new KafkaConsumer<String, SensorData>(props);
        consumer.subscribe(Arrays.asList("testeSensorData"));
        //ObjectMapper mapper = new ObjectMapper();

        //Processar mensagens
        try {
            while (true) {
                ConsumerRecords<String, SensorData> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, SensorData> record : records) {
                    int record_key = Integer.parseInt(record.key());
                    SensorData dados_sensor = record.value();

                    //para o valor X, se estiver na posição correta no Array, verificar MAX e MIN
                    if (lista_sensores.get(record_key - 1).getId() == record_key) {
                        if (lista_sensores.get(record_key - 1).getMax() < dados_sensor.getValor()){
                            dbConnector.updateMaxMinOnSesor(record_key, dados_sensor.getValor(), true);
                            lista_sensores.get(record_key - 1).setMax(dados_sensor.getValor());
                        }
                        if (lista_sensores.get(record_key - 1).getMin() > dados_sensor.getValor()){
                            dbConnector.updateMaxMinOnSesor(record_key, dados_sensor.getValor(), false);
                            lista_sensores.get(record_key - 1).setMin(dados_sensor.getValor());
                        }
                    }

                    /*if (lista_sensores.get(dados_sensor.getId_sensor() - 1).getId() == dados_sensor.getId_sensor()) {
                        if (lista_sensores.get(dados_sensor.getId_sensor() - 1).getMax() < dados_sensor.getValor())
                            dbConnector.updateMaxMinOnSesor(dados_sensor.getId_sensor(), dados_sensor.getValor(), true);
                        else if (lista_sensores.get(dados_sensor.getId_sensor() - 1).getMin() > dados_sensor.getValor())
                            dbConnector.updateMaxMinOnSesor(dados_sensor.getId_sensor(), dados_sensor.getValor(), false);
                    }*/

                    //for (Sensor sensor : lista_sensores){
                    //    if (sensor.getId() == dados_sensor.getId_sensor() && sensor.getMax()< dados_sensor.getValor())
                    //        dbConnector.updateMaxMinOnSesor(sensor.getId(),dados_sensor.getValor(),true);
                    //    else if (sensor.getId() == dados_sensor.getId_sensor() && sensor.getMin()< dados_sensor.getValor())
                    //        dbConnector.updateMaxMinOnSesor(sensor.getId(),dados_sensor.getValor(),false);
                    //}

                    dbConnector.insereSensorData(dados_sensor);
                }
            }
        } catch (WakeupException ex) {
            System.out.println("Exception caught -->" + ex.getMessage());
        } finally {
            consumer.close();
            System.out.println("Kafka Consumer fechado!");
        }
    }
}
