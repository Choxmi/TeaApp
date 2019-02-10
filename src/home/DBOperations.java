package home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBOperations {


    public static boolean mysql_status = false;
    private Connection con;

    public static final int TYPE_SELECT = 0;
    public static final int TYPE_INSERT = 1;
    public static final int TYPE_UPDATE = 2;
    public static final int TYPE_DELETE = 3;
    public static final int TYPE_INSERT_UPDATE = 4;
    public static final String OP_SUCCESS = "SUCCESS";
    public static final String OP_FAILED = "FAILED";

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/tea_transport";

    private boolean getConnectionStatus(){
        if(con!=null){
            return true;
        }else{
            try {
                Class.forName(JDBC_DRIVER);
                con = DriverManager.getConnection(DB_URL,"root","");
                System.out.println("Connection Succeeded");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public List<List<String>> executeQuery(int type, String table, List<String> columns, List<String> values, String join, String where, int... primaries){
        List<List<String>> full = new ArrayList<>();
        List<String> inner = new ArrayList<>();
        ResultSet result = null;

        if(getConnectionStatus()) {

            try {
                Statement stmnt = con.createStatement();
                String query = "";

                switch (type) {
                    case TYPE_SELECT:
                        if (columns != null) {
                            query = "SELECT ";
                            for (int col = 0; col < columns.size(); col++) {
                                if (col != (columns.size() - 1)) {
                                    query += columns.get(col) + ", ";
                                } else {
                                    query += columns.get(col) + " ";
                                }
                            }
                            query += "FROM " + table;
                        } else {
                            query = "SELECT * FROM " + table;
                        }

                        if(join != null){
                            query += join;
                        }

                        if (where != null) {
                            query += " WHERE 1=1 AND " + where;
                        }
                        result = stmnt.executeQuery(query);

                        ResultSetMetaData meta = result.getMetaData();
                        int columnsNumber = meta.getColumnCount();
                        full.clear();
                        while (result.next()) {
                            inner = new ArrayList<>();
                            for (int i = 1; i <= columnsNumber; i++) {
                                inner.add(result.getString(i));
                                System.out.print(result.getString(i));
                            }
                            full.add(inner);
                        }
                        break;
                    case TYPE_INSERT:

                        if (columns != null) {
                            if (columns.size() != values.size()) {
                                System.out.printf("Size different");
                                return null;
                            } else {
                                System.out.println("QUERY");
                                query = "INSERT INTO " + table + "(";

                                for (int i = 0; i < columns.size(); i++) {
                                    if (i < (columns.size() - 1)) {
                                        query += " " + columns.get(i) + ",";
                                    } else {
                                        query += " " + columns.get(i);
                                    }
                                }

                                query += ") VALUES (";

                                for (int i = 0; i < values.size(); i++) {
                                    if (i < (values.size() - 1)) {
                                        query += " '" + values.get(i) + "',";
                                    } else {
                                        query += " '" + values.get(i) + "'";
                                    }
                                }
                                query += ")";
                                System.out.println("QUERY : "+query);

                                if (stmnt.execute(query)) {
                                    inner.add(OP_SUCCESS);
                                    full.add(inner);
                                } else {
                                    inner.add(OP_FAILED);
                                    full.add(inner);
                                }

                            }
                        } else {
                            query = "INSERT INTO " + table + " VALUES ";

                            for (int i = 0; i < values.size(); i++) {
                                if (i < (values.size() - 1)) {
                                    query += values.get(i);
                                }else{
                                    query += values.get(i) + ",";
                                }
                            }

                            System.out.println("QUERY : "+query);

                            if (stmnt.execute(query)) {
                                inner.add(OP_SUCCESS);
                                full.add(inner);
                            } else {
                                inner.add(OP_FAILED);
                                full.add(inner);
                            }
                        }

                        break;
                    case TYPE_DELETE:
                        break;
                    case TYPE_UPDATE:
                        if (columns != null) {
                            query = "UPDATE " + table + " SET ";
                            for (int i = 0; i < columns.size(); i++) {
                                if (i < (columns.size() - 1)) {
                                    query += " " + columns.get(i) + " = '"+ values.get(i) +"',";
                                } else {
                                    query += " " + columns.get(i) + " = '"+ values.get(i) + "'";
                                }
                            }
                            if (where != null) {
                                query += " WHERE 1=1 AND " + where;
                            }
                            System.out.println(query);
                            if (stmnt.execute(query)) {
                                inner.add(OP_SUCCESS);
                                full.add(inner);
                            } else {
                                inner.add(OP_FAILED);
                                full.add(inner);
                            }
                        }
                        break;
                    case TYPE_INSERT_UPDATE:
                        if (columns != null) {
                            query = "INSERT INTO " + table + "(";
                            for (int i = 0; i < columns.size(); i++) {
                                if (i < (columns.size() - 1)) {
                                    query += " " + columns.get(i) + ",";
                                } else {
                                    query += " " + columns.get(i);
                                }
                            }

                            query += ") VALUES (";

                            for (int i = 0; i < values.size(); i++) {
                                if (i < (values.size() - 1)) {
                                    query += " '" + values.get(i) + "',";
                                } else {
                                    query += " '" + values.get(i) + "'";
                                }
                            }

                            query += ") ON DUPLICATE KEY UPDATE ";
                            for (int i = primaries[0]; i < columns.size(); i++) {
                                if (i < (columns.size() - 1)) {
                                    query += " " + columns.get(i) + " = '"+ values.get(i) +"',";
                                } else {
                                    query += " " + columns.get(i) + " = '"+ values.get(i) + "'";
                                }
                            }

                            System.out.println(query);
                            if (stmnt.execute(query)) {
                                inner.add(OP_SUCCESS);
                                full.add(inner);
                            } else {
                                inner.add(OP_FAILED);
                                full.add(inner);
                            }
                        }
                        break;
                    default:
                        break;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("RET => "+full);
        return full;
    }

    private void runCommand(){
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "D:\\Software\\Installations\\Xampp\\mysql\\bin\\mysqld.exe\" ");
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void startMysql(){
        try {
            Runtime.getRuntime().exec("E:\\Softwares\\Installations\\Xampp\\mysql\\bin\\mysqld.exe");
            mysql_status = true;
        } catch (IOException e) {
            mysql_status = false;
            e.printStackTrace();
        }

    }

    public static void stopMysql(){
        try {
            Runtime.getRuntime().exec("D:\\Softwares\\Installations\\Xampp\\mysql\\bin\\mysqladmin.exe -u root shutdown");
            mysql_status = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
