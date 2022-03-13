package challenge.RHO.Model;


import java.sql.Date;
import java.sql.Time;

public class SensorData {
    private int id;
    private int id_sensor;
    private double valor;
    private Date regist_date;
    private Time regist_time;

    public SensorData() {
    }

    public SensorData(int id_sensor, double valor, Date regist_date,Time regist_time) {
        this.id_sensor = id_sensor;
        this.valor = valor;
        this.regist_date = regist_date;
        this.regist_time = regist_time;
    }

    public SensorData(int id, int id_sensor, double valor, Date regist_date,Time regist_time) {
        this.id = id;
        this.id_sensor = id_sensor;
        this.valor = valor;
        this.regist_date = regist_date;
        this.regist_time = regist_time;
    }

    public Date getRegist_date() {
        return regist_date;
    }

    public void setRegist_date(Date regist_date) {
        this.regist_date = regist_date;
    }

    public Time getRegist_time() {
        return regist_time;
    }

    public void setRegist_time(Time regist_time) {
        this.regist_time = regist_time;
    }

    public int getId_sensor() {
        return id_sensor;
    }

    public void setId_sensor(int id_sensor) {
        this.id_sensor = id_sensor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "id=" + id +
                ", id_sensor=" + id_sensor +
                ", valor=" + valor +
                ", regist_date=" + regist_date +
                ", regist_time=" + regist_time +
                '}';
    }
}
