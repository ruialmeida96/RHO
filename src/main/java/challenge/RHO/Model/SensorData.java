package challenge.RHO.Model;


import java.util.Date;

public class SensorData {
    private int id;
    private double valor;
    private Date regitst_time;

    public SensorData(){}

    public SensorData(double valor, Date regitst_time) {
        this.valor = valor;
        this.regitst_time = regitst_time;
    }

    public SensorData(int id, double valor, Date regitst_time) {
        this.id = id;
        this.valor = valor;
        this.regitst_time = regitst_time;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "id=" + id +
                ", valor=" + valor +
                ", regitst_time=" + regitst_time +
                '}';
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

    public Date getRegitst_time() {
        return regitst_time;
    }

    public void setRegitst_time(Date regitst_time) {
        this.regitst_time = regitst_time;
    }
}
