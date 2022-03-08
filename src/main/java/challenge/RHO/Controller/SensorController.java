package challenge.RHO.Controller;

import challenge.RHO.Model.Sensor;
import challenge.RHO.Model.SensorData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

@RestController
public class SensorController {

    ArrayList<Sensor> array_sensores = new ArrayList<>();

    SensorController(){
        Vector<SensorData> vetorteste = new Vector<SensorData>();
        vetorteste.add(new SensorData(1,2.222222,new Date(System.currentTimeMillis())));
        vetorteste.add(new SensorData(2,333.332,new Date(System.currentTimeMillis())));
        vetorteste.add(new SensorData(3,4.222222,new Date(System.currentTimeMillis())));
        vetorteste.add(new SensorData(4,5.222222,new Date(System.currentTimeMillis())));

        Vector<SensorData> vetorteste1 = new Vector<SensorData>();
        vetorteste1.add(new SensorData(1,2.222222,new Date(System.currentTimeMillis())));
        vetorteste1.add(new SensorData(2,333.332,new Date(System.currentTimeMillis())));
        vetorteste1.add(new SensorData(3,4.222222,new Date(System.currentTimeMillis())));
        vetorteste1.add(new SensorData(4,5.222222,new Date(System.currentTimeMillis())));


        array_sensores.add(new Sensor(1, "nome", new Date(System.currentTimeMillis()), 1.111111, 2.323234234,1,2,vetorteste));
        array_sensores.add(new Sensor(2, "nome 2", new Date(System.currentTimeMillis()), 3.11111312311, 2.323234234,3,4,vetorteste1));
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

    @GetMapping("/sensores")
    public ArrayList<Sensor> return_sensores(){
        return array_sensores;
    }


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

    @GetMapping("/sensor")
    public String return_sensor_by_id(@RequestParam(name = "id") int id){
        if (id >0){
            for (Sensor _sensor : array_sensores){
                if(_sensor.getId() == id)
                    return "Sensor id:"+_sensor.getId()+" | Latest Temperature:"+_sensor.getDados().lastElement().getValor()+" | Min. Temp.:"+_sensor.getMin()+" | Max. Temp.:"+_sensor.getMax();
            }
        }
        return "No Available Data";
    }
    

}
