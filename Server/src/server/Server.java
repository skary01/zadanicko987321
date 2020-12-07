package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
/**
 * 
 * @author Ondirko, Karabinos
 *
 */

public class Server 
{   
    public static void main(String args[]) throws Exception, SQLException 
    {    Server myWrapper = new Server() ;

        int x=0;
        ServerSocket ss = new ServerSocket(3333);
        Socket s = ss.accept();
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader stdin = new BufferedReader(isr);
        String s1="",s2="",rola="",student="student",referent="referent", pomocna="",maria="Maria",robert="robert",vypis="vypis";
        while(!s1.equals("stop"))
        {
            s1=dis.readUTF();
            if(x == 0)
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement dotaz = connect.prepareStatement("SELECT TYPE_OF_CLIENT FROM CLIENTS WHERE ID=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet result = dotaz.executeQuery())
                    {
                        result.next();
                        rola = result.getString("TYPE_OF_CLIENT");
                        pomocna = s1;
                        x=1;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }  
            
            
            
            
            
            System.out.println("Client: "+s1);
            if((x == 1)&&(rola.equals(student)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement dotaz = connect.prepareStatement("SELECT NAME FROM CLIENTS WHERE ID=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet result = dotaz.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        x=2;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if((x == 2)&&(rola.equals(student)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                PreparedStatement dotaz = connect.prepareStatement("SELECT NAME FROM CLIENTS WHERE PASSWORD=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet result = dotaz.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        x=3;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if(((x == 3)&&(s1.equals(maria))))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                PreparedStatement dotaz = connect.prepareStatement("SELECT NAME,SURNAME,ID FROM CLIENTS WHERE ID=?");)
                {
                    dotaz.setString(1, pomocna);
                    try(ResultSet result = dotaz.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        String surname = result.getString("SURNAME");
                        String id = result.getString("ID");
                        Statement stmt = connect.createStatement();
                        stmt.executeUpdate("insert into TEST.MARIA(name,surname,id) values('"+name+"','"+surname+"','"+id+"')");
                        System.out.println("Data is inserted ");
                        x=0;
                        
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if(((x == 3)&&(s1.equals(robert))))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                PreparedStatement dotaz = connect.prepareStatement("SELECT NAME,SURNAME,ID FROM CLIENTS WHERE ID=?");)
                {
                    dotaz.setString(1, pomocna);
                    try(ResultSet result = dotaz.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        String surname = result.getString("SURNAME");
                        String id = result.getString("ID");
                        Statement stmt = connect.createStatement();
                        stmt.executeUpdate("insert into TEST.ROBERT(name,surname,id) values('"+name+"','"+surname+"','"+id+"')");
                        System.out.println("Data is inserted ");
                        x=0;                    
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            
            
            
            if((x == 1)&&(rola.equals(referent)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement dotaz = connect.prepareStatement("SELECT NAME FROM CLIENTS WHERE ID=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet result = dotaz.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        pomocna=name;
                        x=2;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if((x == 2)&&(rola.equals(referent)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement dotaz = connect.prepareStatement("SELECT NAME FROM CLIENTS WHERE PASSWORD=?");)
                {
                    dotaz.setString(1, s1);
                    try(ResultSet result = dotaz.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        x=3;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if(((x == 3)&&(pomocna.equals(maria)))&&(s1.equals(vypis)))
            {
                System.out.println("Meno: " + pomocna);
                Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                Statement stmt = connect.createStatement();            
                ResultSet resoultset = stmt.executeQuery("select * from TEST.MARIA");
                resoultset.beforeFirst();
                while (resoultset.next()) 
                {
                    String nazov = resoultset.getString("MENO");
                    System.out.println("Nazov: " + nazov);
                    x=0;
                }                   
            }
            
            if(x == 0)
            {
                s2="Enter ID"; 
            }
            if(x == 2)
            {
                s2="Enter password";
            }     
            if((x == 3)&&(rola.equals(student)))
            {
                s2="maria or robert";
            }
            if((x == 3)&&(rola.equals(referent)))
            {
                s2="vypis alebo poradie";
                
            }
  
            
            dos.writeUTF(s2);
            dos.flush();
            
        }
        dis.close();
        s.close();
        ss.close();
    }
}