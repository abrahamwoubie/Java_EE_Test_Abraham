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
             
        	 String username = request.getParameter("username");
             String postal_code= request.getParameter("postal_code");
        	 response.sendRedirect("http://www.geonames.org/search.html?q="+postal_code);
        	 
        	 Resty r = new Resty();        	 
        	 String name = r.json("http://ws.geonames.org/postalCodeLookupJSON?"+
        	     "postalcode=66780&country=DE").get("postalcodes[0].placeName");
        	 
             Class.forName("com.mysql.jdbc.Driver");
    
             connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/abraham", "root", "Abraham");
             
             PreparedStatement pst = connection.prepareStatement("insert into Detail (postal_code, city) values(?,?)");
             pst.setString(1,postal_code);  
             pst.setString(2,"Barcelona");        
             
             PreparedStatement pst1 = connection.prepareStatement("insert into Master (username) values(?)");
             pst1.setString(1,username);  
             
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
        	   
           /*try {

               URL url = new URL("http://api.geonames.org/postalCodeSearchJSON?postalcode=08903&maxRows=1&username=demo&country=ES");
               HttpURLConnection conn = (HttpURLConnection) url.openConnection();
               conn.setRequestMethod("GET");
               conn.setRequestProperty("Accept", "application/json");

               if (conn.getResponseCode() != 200) {
                   throw new RuntimeException("Failed : HTTP error code : "
                       + conn.getResponseCode());
               }

               BufferedReader br = new BufferedReader(new InputStreamReader(
                  (conn.getInputStream())));

               String output;
               System.out.println("Output from Server .... \n");
               while ((output = br.readLine()) != null) {
                  System.out.println(output);
               }

               conn.disconnect();

               } catch (MalformedURLException e) {

               e.printStackTrace();

               } catch (IOException e) {

               e.printStackTrace();

             }

           }

       }*/