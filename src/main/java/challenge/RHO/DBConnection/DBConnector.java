package challenge.RHO.DBConnection;

import challenge.RHO.Model.Sensor;
import challenge.RHO.Model.SensorData;
import com.mysql.cj.jdbc.CallableStatementWrapper;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.util.ArrayList;

public class DBConnector {

    private String dbUrl;
    private String user;
    private String password;
    //private String dbDriver = "com.mysql.jdbc.Driver";

    private static Connection conn = null;
    private StringBuffer sqlQuery = null;
    private StringBuffer sqlQuery1 = null;
    private Statement st = null;
    private static ResultSet rs = null;

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

    public ArrayList<Sensor> selectallSensors() {
        if (isConnected()) {
            ArrayList<Sensor> arraySensors = new ArrayList<>();

            String query = "SELECT * FROM sensor;";

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


    public boolean updateMaxMinOnSesor(int id_sensor, double novo_valor_sensor, boolean is_max) {
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

                total_rows = statement.executeUpdate();
                if (total_rows > 0)
                    return true;
                return false;
            } catch (SQLException e) {
                System.out.println("!! SQL Exception !!\n" + e);
                return false;
            }
        } else {
            System.out.println("!! Database not initialised !!\nUnable to select new employer.");
            return false;
        }
    }

}
