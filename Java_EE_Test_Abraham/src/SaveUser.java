import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static us.monoid.web.Resty.*;
import us.monoid.web.Resty.*;

public class SaveUser extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
           Connection connection = null;  
           try{  
                 // Read the username and postal code from the forms
        	 String username = request.getParameter("username");
                 String postal_code= request.getParameter("postal_code");
        	 response.sendRedirect("http://www.geonames.org/search.html?q="+postal_code);
        
	     // Loading or registering MySQL JDBC driver class	   
             Class.forName("com.mysql.jdbc.Driver");
    
	     // Create and get connection using DriverManager class
             connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abraham", "root", "Abraham");
             
	     //Creating JDBC Statement
             PreparedStatement pst = connection.prepareStatement("insert into Detail (postal_code, city) values(?,?)");
             pst.setString(1,postal_code);  
             pst.setString(2,"Barcelona");        
             
             PreparedStatement pst1 = connection.prepareStatement("insert into Master (username) values(?)");
             pst1.setString(1,username);  
             
             //Excecute the query
             int i = pst.executeUpdate();
             int j = pst1.executeUpdate();
             
             if(i!=0 && j!=0){  
            	 System.out.print("One Record has been inserted");                  
          
             }  
             else{  
            	 System.out.print("Failed to insert data"); 
              }  
           }  
           catch (Exception e){  
           }  
         }  
        
   }
