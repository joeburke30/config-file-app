package com.broadcom.symantec.pam.configkubesecretapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

@RestController
@CrossOrigin
public class SimpleService {
    
    @Value("${SECRETS_DEMO_USER}")
    private String user;

    @Value("${SECRETS_DEMO_USER_PASSWD}")
    private String pw;

    @GetMapping("/")
    public String home () throws Exception {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        StringBuffer sb = new StringBuffer();
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://192.168.72.132:3306/demo",user,pw);
            //        .getConnection("jdbc:mysql://192.168.72.132:3306/demo","user1","MyPassword");
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from employees");
            while (resultSet.next()) {
                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);
                String name = resultSet.getString("name");
                String desc  = resultSet.getString("description");
                int id = resultSet.getInt("id");
                Date date = resultSet.getDate("created_at");

                sb.append("Kube Secret  >  " + name + " is a " + desc + " with employee id = "+ id + " hired on : " + date + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.append ("Kube Secret  >  " + e.getMessage());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        }
        return sb.toString();
    }
}
