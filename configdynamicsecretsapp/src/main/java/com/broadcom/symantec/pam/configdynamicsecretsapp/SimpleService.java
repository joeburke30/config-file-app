package com.broadcom.symantec.pam.configdynamicsecretsapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

@RestController
@CrossOrigin
public class SimpleService {

    @Value("${ALIAS}")
    String alias;
    
    private static final String U_START = "<U>";
    private static final String U_END = "</U>";

    private static final String P_START = "<P>";
    private static final String P_END = "</P>";

    @GetMapping("/")
    public String home () throws Exception {
        
        String uri = "http://localhost:28090/requestScript/retrieveCredentials?aliasName="+ alias + "&contentType=xml";

        HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(uri)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

		String user =responseBody.substring(responseBody.indexOf(U_START),responseBody.indexOf(U_END));
		String pw   =responseBody.substring(responseBody.indexOf(P_START),responseBody.indexOf(P_END));
     
        System.out.println("u="+user+"  p="+pw);

		//make A2A call

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

                sb.append("Dynamic Secrets   > " + name + " is a " + desc + " with employee id = "+ id + " hired on : " + date + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.append("Dynamic Secrets   > " + e.getMessage() + "\n");
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