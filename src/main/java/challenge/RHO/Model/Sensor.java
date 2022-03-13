package challenge.RHO.Model;

import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Vector;

public class Sensor {
    private int id;
    private String name;
    private Date install_date;
    private Time install_time;
    private double latitude;
    private double longitude;
    private double min;
    private double max;
    private ArrayList<SensorData> dados;

    public Sensor() {
    }

    public Sensor(int id, String name, Date install_date, Time install_time, double latitude, double longitude, double min, double max, ArrayList<SensorData> dados) {
        this.id = id;
        this.name = name;
        this.install_date = install_date;
        this.install_time = install_time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.max = max;
        this.min = min;
        this.dados = dados;
    }

    public Sensor(String name, Date install_date, Time install_time, double latitude, double longitude, double min, double max, ArrayList<SensorData> dados) {
        this.name = name;
        this.install_date = install_date;
        this.install_time = install_time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.max = max;
        this.min = min;
        this.dados = dados;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", install_date=" + install_date +
                ", install_time=" + install_time +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", min=" + min +
                ", max=" + max +
                ", dados=" + dados +
                '}';
    }

    public Date getInstall_date() {
        return install_date;
    }

    public void setInstall_date(Date install_date) {
        this.install_date = install_date;
    }

    public Time getInstall_time() {
        return install_time;
    }

    public void setInstall_time(Time install_time) {
        this.install_time = install_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public ArrayList<SensorData> getDados() {
        return dados;
    }

    public void setDados(ArrayList<SensorData> dados) {
        this.dados = dados;
    }
}
