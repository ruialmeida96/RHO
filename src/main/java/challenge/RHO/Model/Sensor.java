package challenge.RHO.Model;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Sensor {
    private long id;
    private String name;
    private Date install_date_time;
    private double latitude;
    private double longitude;
    private double min;
    private double max;
    private Vector<SensorData> dados;

    public Sensor(long id, String name, Date install_date_time, double latitude, double longitude, double min,double max, Vector<SensorData> dados) {
        this.id = id;
        this.name = name;
        this.install_date_time = install_date_time;
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
                ", install_date_time=" + install_date_time +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", max=" + max +
                ", min=" + min +
                ", dados=" + dados +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInstall_date_time() {
        return install_date_time;
    }

    public void setInstall_date_time(Date install_date_time) {
        this.install_date_time = install_date_time;
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

    public Vector<SensorData> getDados() {
        return dados;
    }

    public void setDados(Vector<SensorData> dados) {
        this.dados = dados;
    }
}
