package challenge.RHO.RESTAPI;

import challenge.RHO.DBConnection.DBConnector;
import challenge.RHO.Utils.Utils;
import jdk.jshell.execution.Util;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import challenge.RHO.Model.Sensor;
import challenge.RHO.Model.SensorData;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;
import java.util.Vector;

import static challenge.RHO.RhoApplication.lista_sensores;

@RestController
public class SensorAPI {

    SensorAPI() {

    }

    /*@GetMapping("/random")
    public Sensor ola() {

        Vector<SensorData> vetorteste = new Vector<SensorData>();
        vetorteste.add(new SensorData(1,2.222222));
        vetorteste.add(new SensorData(2,333.332));
        vetorteste.add(new SensorData(3,4.222222));
        vetorteste.add(new SensorData(4,5.222222));

        return new Sensor(1, "nome", new Date(System.currentTimeMillis()), 1.111111, 2.323234234,1,2,vetorteste);
    }*/

    /*@GetMapping("/sensores")
    public ArrayList<Sensor> return_sensores(){
        return array_sensores;
    }*/

    /*@GetMapping("/sensor")
    public Sensor return_sensor_by_id(@RequestParam(name = "id") int id){
        if (id >0){
            for (Sensor _sensor : array_sensores){
                if(_sensor.getId() == id)
                    return _sensor;
            }
        }
        return null;
    }*/

    @GetMapping(value = "/sensores",produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Sensor> return_sensores() {
        return DBConnector.selectallSensors();
    }

    @GetMapping(value = "/sensor", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sensor return_sensor_by_id(
            @RequestParam(name = "id") int id,
            @RequestParam(name = "datainicio", required = false) Date datainicio,
            @RequestParam(name = "datafim", required = false) Date datafim,
            @RequestParam(name = "horainicio", required = false) Time horainicio,
            @RequestParam(name = "horafim", required = false) Time horafim
    ) {
        if (id > 0) {
            if (datainicio == null && horainicio == null && datafim == null && horafim == null) {
                for (Sensor _sensor : lista_sensores) {
                    if (_sensor.getId() == id) {
                        //obter dados de SensorData e colocar no dados
                        ArrayList<SensorData> dados_sensor = new ArrayList<SensorData>();
                        dados_sensor = DBConnector.selectallSensorData(_sensor.getId());
                        _sensor.setDados(dados_sensor);
                        return _sensor;
                        /*if (_sensor.getDados().size() > 0)
                            return "Sensor id:" + _sensor.getId() + " | Latest Temperature:" + _sensor.getDados().get(_sensor.getDados().size() - 1).getValor() + " | Min. Temp.:" + _sensor.getMin() + " | Max. Temp.:" + _sensor.getMax();
                        else if (_sensor.getDados().size() == 0)
                            return "Sensor id:" + _sensor.getId() + " | Latest Temperature: {} | Min. Temp.:" + _sensor.getMin() + " | Max. Temp.:" + _sensor.getMax();*/
                    }
                }
            } else {
                Sensor sensor_retorna = DBConnector.retorna_entre_datas(id, datainicio, datafim, horainicio, horafim);
                if (sensor_retorna != null)
                    return sensor_retorna;
            }

        }
        return null;
    }


}
