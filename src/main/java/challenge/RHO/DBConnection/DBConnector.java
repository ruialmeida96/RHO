package challenge.RHO.DBConnection;

import challenge.RHO.Model.Sensor;
import challenge.RHO.Model.SensorData;

import java.sql.*;
import java.util.ArrayList;

public class DBConnector {

    private String dbUrl;
    private String user;
    private String password;
    //private String dbDriver = "com.mysql.jdbc.Driver";

    private static Connection conn = null;
    private Statement st = null;
    private static ResultSet rs = null;
    private static ResultSet rs2 = null;

    private static boolean connected;

    public DBConnector(String aHost, String aUser, String aPass) {
        dbUrl = aHost;
        user = aUser;
        password = aPass;
    }


    public String ConnectDataBase() {
        /*try {
            Class.forName(dbDriver).newInstance();
        } catch (ClassNotFoundException e) {
            return "!! Class Not Found. Unable to load Database Drive !!\n"+e;
        } catch (IllegalAccessException e) {
            return "!! Illegal Access !!\n"+e;
        } catch (InstantiationException e) {
            return "!! Class Not Instanciaded !!\n"+e;
        } finally {
            //closeDataBase();
        }*/

        // Get connection from DB
        try {
            //conn = DriverManager.getConnection(dbUrl);
            conn = DriverManager.getConnection(dbUrl, user, password);
            st = conn.createStatement();
        } catch (SQLException e) {
            closeDataBase();
            return "!! SQL Exception !!\n" + e;
        } finally {
            //closeDataBase();
        }

        connected = true;
        return "Database initialised.";
    }

    public void closeDataBase() {
        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                System.out.println("!! Exception returning statement !!\n" + e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println("!! Exception closing DB connection !!\n" + e);
            }
        }
    }

    public static boolean isConnected() {
        return connected;
    }

    public void create_database(){
        try{
            String query = "CREATE TABLE IF NOT EXISTS sensor (\n" +
                    "  id int NOT NULL AUTO_INCREMENT,\n" +
                    "  name varchar(255) NOT NULL,\n" +
                    "  install_date date NOT NULL,\n" +
                    "  latitude double NOT NULL,\n" +
                    "  longitude double NOT NULL,\n" +
                    "  max double NOT NULL DEFAULT '0',\n" +
                    "  min double NOT NULL DEFAULT '50',\n" +
                    "  install_time time NOT NULL,\n" +
                    "  PRIMARY KEY (id)\n" +
                    ");";

            String query2 = "CREATE TABLE IF NOT EXISTS sensordata (\n" +
                    "  id int NOT NULL AUTO_INCREMENT,\n" +
                    "  id_sensor int NOT NULL,\n" +
                    "  valor double NOT NULL,\n" +
                    "  regist_date date NOT NULL,\n" +
                    "  regist_time time NOT NULL,\n" +
                    "  PRIMARY KEY (id),\n" +
                    "  KEY id_sensor_idx (id_sensor),\n" +
                    "  FOREIGN KEY (id_sensor) REFERENCES sensor (id)\n" +
                    ");";

            PreparedStatement statement = conn.prepareStatement(query);
            PreparedStatement statement2 = conn.prepareStatement(query2);

            statement.execute();
            statement2.execute();
        } catch (SQLException e) {
            System.out.println("!! SQL Exception !!\n" + e);
        }
    }


    public boolean insereSensor(Sensor _data) {
        if (isConnected()) {
            try {
                String query = "INSERT INTO sensor (name, install_date,install_time, latitude, longitude, min, max) " +
                        "VALUES(?,?,?,?,?,?,?);";

                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, _data.getName());
                statement.setDate(2, _data.getInstall_date());
                statement.setTime(3, _data.getInstall_time());
                statement.setDouble(4, _data.getLatitude());
                statement.setDouble(5, _data.getLongitude());
                statement.setDouble(6, _data.getMin());
                statement.setDouble(7, _data.getMax());

                return statement.execute();
            } catch (SQLException e) {
                System.out.println("!! SQL Exception !!\n" + e);
                return false;
            }
        } else {
            System.out.println("!! Database not initialised !!\nUnable to insert new employer.");
            return false;
        }
    }

    public boolean insereSensorData(SensorData _data) {
        if (isConnected()) {
            try {
                String query = "INSERT INTO sensordata (id_sensor,valor,regist_date,regist_time) " +
                        "VALUES(?,?,?,?);";

                PreparedStatement statement = conn.prepareStatement(query);
                statement.setInt(1, _data.getId_sensor());
                statement.setDouble(2, _data.getValor());
                statement.setDate(3, _data.getRegist_date());
                statement.setTime(4, _data.getRegist_time());

                return statement.execute();
            } catch (SQLException e) {
                System.out.println("!! SQL Exception !!\n" + e);
                return false;
            }
        } else {
            System.out.println("!! Database not initialised !!\nUnable to insert new employer.");
            return false;
        }
    }


    public Sensor selectSensor(int id_sensor) {
        if (isConnected()) {

            String query = "SELECT * FROM sensor WHERE id = ?;";

            try {
                rs = null;

                PreparedStatement statement = conn.prepareStatement(query);
                statement.setInt(1, id_sensor);

                rs = statement.executeQuery();
                Sensor sensor_return = null;
                while (rs.next()) {
                    sensor_return = new Sensor();
                    sensor_return.setId(rs.getInt("id"));
                    sensor_return.setName(rs.getString("name"));
                    sensor_return.setInstall_date(rs.getDate("install_date"));
                    sensor_return.setInstall_time(rs.getTime("install_time"));
                    sensor_return.setLatitude(rs.getDouble("latitude"));
                    sensor_return.setLongitude(rs.getDouble("longitude"));
                    sensor_return.setMax(rs.getDouble("max"));
                    sensor_return.setMin(rs.getDouble("min"));
                    sensor_return.setDados(null);
                }
                return sensor_return;
            } catch (SQLException e) {
                System.out.println("!! SQL Exception !!\n" + e);
                return null;
            }
        } else {
            System.out.println("!! Database not initialised !!\nUnable to select new employer.");
            return null;
        }
    }

    public static ArrayList<Sensor> selectallSensors() {
        if (isConnected()) {
            ArrayList<Sensor> arraySensors = new ArrayList<>();

            String query = "SELECT * FROM sensor ORDER BY id ASC;";

            try {
                rs = null;

                PreparedStatement statement = conn.prepareStatement(query);

                rs = statement.executeQuery();

                while (rs.next()) {
                    Sensor sensor_return = new Sensor();
                    sensor_return.setId(rs.getInt("id"));
                    sensor_return.setName(rs.getString("name"));
                    sensor_return.setInstall_date(rs.getDate("install_date"));
                    sensor_return.setInstall_time(rs.getTime("install_time"));
                    sensor_return.setLatitude(rs.getDouble("latitude"));
                    sensor_return.setLongitude(rs.getDouble("longitude"));
                    sensor_return.setMax(rs.getDouble("max"));
                    sensor_return.setMin(rs.getDouble("min"));
                    sensor_return.setDados(null);

                    arraySensors.add(sensor_return);
                }
                return arraySensors;
            } catch (SQLException e) {
                System.out.println("!! SQL Exception !!\n" + e);
                return null;
            }
        } else {
            System.out.println("!! Database not initialised !!\nUnable to select new employer.");
            return null;
        }
    }

    public int selectCountSensors() {
        if (isConnected()) {

            String query = "SELECT COUNT(*) as total FROM sensor;";

            try {
                rs = null;
                PreparedStatement statement = conn.prepareStatement(query);
                rs = statement.executeQuery();
                int total = 0;
                while (rs.next()) {
                    total = rs.getInt("total");
                }
                return total;
            } catch (SQLException e) {
                System.out.println("!! SQL Exception !!\n" + e);
                return 0;
            }
        } else {
            System.out.println("!! Database not initialised !!\nUnable to select new employer.");
            return 0;
        }
    }

    public static ArrayList<SensorData> selectallSensorData(int id_sensor) {
        if (isConnected()) {
            ArrayList<SensorData> arrayListreturn = new ArrayList<>();
            String query = "SELECT * FROM sensordata WHERE id_sensor = ? ORDER BY id ASC;";

            try {
                rs = null;
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setInt(1, id_sensor);

                rs = statement.executeQuery();
                while (rs.next()) {
                    SensorData sensor_data = new SensorData();
                    sensor_data.setId(rs.getInt("id"));
                    sensor_data.setId_sensor(rs.getInt("id_sensor"));
                    sensor_data.setValor(rs.getDouble("valor"));
                    sensor_data.setRegist_date(rs.getDate("regist_date"));
                    sensor_data.setRegist_time(rs.getTime("regist_time"));

                    arrayListreturn.add(sensor_data);
                }
                return arrayListreturn;
            } catch (SQLException e) {
                System.out.println("!! SQL Exception !!\n" + e);
                return null;
            }
        } else {
            System.out.println("!! Database not initialised !!\nUnable to select new employer.");
            return null;
        }
    }


    public void updateMaxMinOnSesor(int id_sensor, double novo_valor_sensor, boolean is_max) {
        //aqui o is_max == true indica que é para atualizar o Max. caso contrário o MIN
        if (isConnected()) {
            String query = null;
            int total_rows = 0;

            if (is_max)
                query = "UPDATE sensor SET max = ? WHERE id = ?;";
            else if (!is_max)
                query = "UPDATE sensor SET min = ? WHERE id = ?;";
            try {
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setDouble(1, novo_valor_sensor);
                statement.setInt(2, id_sensor);

                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("!! SQL Exception !!\n" + e);
            }
        } else {
            System.out.println("!! Database not initialised !!\nUnable to select new employer.");
        }
    }

    public static Sensor retorna_entre_datas(int id_sensor,Date datainicio, Date datafim,Time horainicio,Time horafim) {
        if (isConnected()) {
            Sensor sensor_retorna = new Sensor();
            String query = "SELECT * FROM sensor WHERE id = ?;";
            String query2 = null;
            if (horainicio == null && horafim == null)
                query2 = "SELECT * FROM sensordata WHERE id_sensor = ? AND regist_date >= ? AND regist_date <= ? ORDER BY id ASC;";
            else if (horainicio != null && horafim != null)
                query2 =  "SELECT * FROM sensordata WHERE id_sensor = ? AND (regist_date >= ? AND regist_date <= ? ) AND (regist_time > ? AND regist_time < ?) ORDER BY id ASC";

            try {
                rs = null;
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setInt(1, id_sensor);

                rs = statement.executeQuery();
                while (rs.next()) {
                    sensor_retorna.setId(rs.getInt("id"));
                    sensor_retorna.setName(rs.getString("name"));
                    sensor_retorna.setInstall_date(rs.getDate("install_date"));
                    sensor_retorna.setInstall_time(rs.getTime("install_time"));
                    sensor_retorna.setLatitude(rs.getDouble("latitude"));
                    sensor_retorna.setLongitude(rs.getDouble("longitude"));
                    sensor_retorna.setMax(rs.getDouble("max"));
                    sensor_retorna.setMin(rs.getDouble("min"));

                }
                rs2 = null;
                PreparedStatement statement2 = conn.prepareStatement(query2);
                statement2.setInt(1, id_sensor);
                statement2.setDate(2, datainicio);
                statement2.setDate(3, datafim);
                if(horainicio != null && horafim != null) {
                    statement2.setTime(4, horainicio);
                    statement2.setTime(5, horafim);
                }
                ArrayList<SensorData> dados_sensor_data = new ArrayList<SensorData>();

                rs2 = statement2.executeQuery();
                while (rs2.next()) {
                    SensorData sensordata = new SensorData();
                    sensordata.setId(rs2.getInt("id"));
                    sensordata.setId_sensor(rs2.getInt("id_sensor"));
                    sensordata.setValor(rs2.getDouble("valor"));
                    sensordata.setRegist_date(rs2.getDate("regist_date"));
                    sensordata.setRegist_time(rs2.getTime("regist_time"));

                    dados_sensor_data.add(sensordata);
                }
                //atribuir os dados do SensorData
                sensor_retorna.setDados(dados_sensor_data);
                return sensor_retorna;
            } catch (SQLException e) {
                System.out.println("!! SQL Exception !!\n" + e);
                return null;
            }
        } else {
            System.out.println("!! Database not initialised !!\nUnable to select new employer.");
            return null;
        }
    }

}
