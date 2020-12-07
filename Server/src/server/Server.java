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
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 
 * @author Ondirko, Karabinos
 *
 */

public class Server 
{   
    public static void main(String args[]) throws Exception, SQLException 
    {    
        Server myWrapper = new Server() ;

        int x=0;
        ServerSocket serverSocket = new ServerSocket(3333);
        Socket socket = serverSocket.accept();
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader stdin = new BufferedReader(inputStreamReader);
        String socket1="",socket2="",rola="",student="student",referent="referent", pomocna="",maria="741963",robert="robert",vypis="vypis";
        while(!socket1.equals("stop"))
        {
            socket1=dataInputStream.readUTF();
            if(x == 0)
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement preparedStatement = connect.prepareStatement("SELECT TYPE_OF_CLIENT FROM CLIENTS WHERE ID=?");)
                {
                    preparedStatement.setString(1, socket1);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        rola = result.getString("TYPE_OF_CLIENT");
                        pomocna = socket1;
                        x=1;
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }  
            
            
            
            
            
            System.out.println("Client: "+socket1);
            if((x == 1)&&(rola.equals(student)))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME FROM CLIENTS WHERE ID=?");)
                {
                    preparedStatement.setString(1, socket1);
                    try(ResultSet result = preparedStatement.executeQuery())
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
                PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME FROM CLIENTS WHERE PASSWORD=?");)
                {
                    preparedStatement.setString(1, socket1);
                    try(ResultSet result = preparedStatement.executeQuery())
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
            if(((x == 3)&&(socket1.equals(maria))))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME,SURNAME,ID FROM CLIENTS WHERE ID=?");)
                {
                    preparedStatement.setString(1, pomocna);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        String surname = result.getString("SURNAME");
                        String id = result.getString("ID");
                        Statement statement = connect.createStatement();
                        statement.executeUpdate("insert into TEST.MARIA(name,surname,id) values('"+name+"','"+surname+"','"+id+"')");
                        System.out.println("Data is inserted ");
                        x=0;
                        
                    }
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }
            if(((x == 3)&&(socket1.equals(robert))))
            {
                try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME,SURNAME,ID FROM CLIENTS WHERE ID=?");)
                {
                    preparedStatement.setString(1, pomocna);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        String surname = result.getString("SURNAME");
                        String id = result.getString("ID");
                        Statement statement = connect.createStatement();
                        statement.executeUpdate("insert into TEST.ROBERT(name,surname,id) values('"+name+"','"+surname+"','"+id+"')");
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
                    PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME FROM CLIENTS WHERE ID=?");)
                {
                    preparedStatement.setString(1, socket1);
                    try(ResultSet result = preparedStatement.executeQuery())
                    {
                        result.next();
                        String name = result.getString("NAME");
                        pomocna = socket1;
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
                    PreparedStatement preparedStatement = connect.prepareStatement("SELECT NAME FROM CLIENTS WHERE PASSWORD=?");)
                {
                    preparedStatement.setString(1, socket1);
                    try(ResultSet result = preparedStatement.executeQuery())
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
            if((x == 3)&&(rola.equals(referent))&&(socket1.equals(vypis))&&(pomocna.equals(maria)))
            {
                try
                {
                    Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/ServerDatabase", "test", "test");
                    Statement statement = connect.createStatement();            
                    ResultSet resoultset = statement.executeQuery("select * from TEST.MARIA");
                    while (resoultset.next()) 
                    {
                        String name = resoultset.getString("NAME");
                        System.out.println("NAME: " + name);
                    }  
                    x=0;
                }catch (SQLException ex) 
                {
                    System.out.println("Cannot communicate with database");
                }
            }

                

            if(x == 0)
            {
                socket2="Enter ID"; 
            }
            if(x == 2)
            {
                socket2="Enter password";
            }     
            if((x == 3)&&(rola.equals(student)))
            {
                socket2="maria or robert";
            }
            if((x == 3)&&(rola.equals(referent)))
            {
                socket2="vypis alebo poradie";
                
            }
            
  
            
            dataOutputStream.writeUTF(socket2);
            dataOutputStream.flush();
            
        }
        dataInputStream.close();
        socket.close();
        serverSocket.close();
    }
}