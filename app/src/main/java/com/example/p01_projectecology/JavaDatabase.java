package com.example.p01_projectecology;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Simple Java program to connect to MySQL database running on localhost and
 * running SELECT and INSERT query to retrieve and add data.
 *
 * @author Javin Paul
 */
public class JavaDatabase {
    // JDBC URL, username and password of MySQL server
    private static final String url = "db23.freehost.com.ua";
    private static final String user = "polissia_smash";
    private static final String password = "2AOKqv8Md";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static ArrayList<Integer> getBakInfo() {
        String query = "SELECT * FROM nodemcu_ldr_table ORDER BY No DESC LIMIT 1";
        ArrayList<Integer> statistic = null;
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);
            Log.i("database", String.valueOf(rs));
            while (rs.next()) {
                statistic = new ArrayList<Integer>();
                statistic.add(rs.getInt(1));
                statistic.add(rs.getInt(2));
                return statistic;
            }

        } catch (SQLException sqlEx) {
            System.out.println("There is an error with the SQL database: " + sqlEx.getMessage());
            sqlEx.printStackTrace();
        }
        return statistic;
    }

}
